from flask import Flask, Blueprint, request, render_template, session, redirect
from flask_jwt_extended import JWTManager, jwt_required, create_access_token, get_jwt_identity
import json
from db import db, User, Language, Match, Chatroom, Message
from werkzeug.security import check_password_hash , generate_password_hash
from flask_socketio import join_room, emit, SocketIO
import os 
from datetime import timedelta


app = Flask(__name__)
db_filename = "lang.db"
app.config["SQLALCHEMY_DATABASE_URI"] = os.getenv("DATABASE_URL", "sqlite:///lang.db")
app.config["SQLALCHEMY_TRACK_MODIFICATIONS"] = False
app.config["SQLALCHEMY_ECHO"] = True
app.config["JWT_SECRET_KEY"] = os.getenv("JWT_SECRET_KEY", "default_jwt_secret")
app.config["JWT_ACCESS_TOKEN_EXPIRES"] = timedelta(hours=1)
app.config["JWT_REFRESH_TOKEN_EXPIRES"] = timedelta(days=30)

socketio = SocketIO(app, cors_allowed_origins="*")

db.init_app(app)
jwt = JWTManager(app)

with app.app_context():
    db.drop_all()
    db.create_all()
    db.add_languages()

def success_response(data, status=200):
    return json.dumps({'success': True, 'data': data}), status

def failure_response(message, status=400):
    return json.dumps({'success': False, 'error': message}), status

# ------------------------------------------------------ USER METHODS ------------------------------------------------------
@app.route('/login/', methods=['POST'])
def login():
    auth_data = json.loads(request.data)

    if not auth_data or not auth_data.get('netID') or not auth_data.get('password'):
        return failure_response("Missing login credentials", 401)

    user = User.query.filter_by(netID=auth_data['netID']).first()
    if not user or not check_password_hash(user.password_hash, auth_data['password']):
        return failure_response("Invalid credentials", 401)

    token = create_access_token(identity= str(user.id))
    return success_response({'token': token, 'user': user.serialize()}, 200)

@app.route('/api/languages/', methods=['GET'])
@jwt_required()
def get_languages():
    languages = Language.query.all()
    return success_response({
        'languages': [lang.serialize() for lang in languages]
    })

@app.route('/api/users/', methods=['POST'])
def create_user():
    try:
        data = json.loads(request.data)
        if not all(k in data for k in ['netID', 'name', 'password', 'level', 'language_id']):
            return failure_response("Missing required fields")
        
        user = User(
            netID=data['netID'],
            name=data['name'],
            password_hash=generate_password_hash(data['password'], method='pbkdf2:sha256'),
            level=data['level'],
            language_id=data['language_id'],
            description=data.get('description', '')
        )
        db.session.add(user)
        db.session.commit()
        return success_response(user.serialize(), 201)
    except Exception as e:
        return failure_response(str(e), 500)
    
@app.route('/api/users/<int:user_id>/', methods=['GET'])
@jwt_required()
def get_user(user_id):
    user = User.query.get(user_id)
    if not user:
        return failure_response("User not found", 404)
    return success_response(user.serialize())

@app.route('/api/users/', methods=['GET'])
@jwt_required()
def get_all_users():
    users = User.query.all()
    return success_response({
        'users': [user.serialize() for user in users]
    })
    
@app.route('/api/users/update/', methods=['PUT'])
def update_user():
    try:
        data = json.loads(request.data)
        netID = data['netID']
        if not netID:
            return failure_response("Missing required fields", 400)
        user = User.query.filter_by(netID=netID).first()
        if not user:
            return failure_response("User not found", 404)
        if 'name' in data:
            user.name = data['name']
        if 'level' in data:
            user.level = data['level']
        if 'language_id' in data:
            user.language_id = data['language_id']
        if 'description' in data:
            user.description = data['description']
    except Exception as e:
        return failure_response(str(e), 500)
    
@app.route('/api/users/<int:user_id>/', methods=['DELETE'])
@jwt_required()
def delete_user(user_id):
    user = User.query.get(user_id)
    if not user:
        return failure_response("User not found", 404)
    
    Match.query.filter(
        (Match.user1_id == user_id) | (Match.user2_id == user_id)
    ).delete()
    
    db.session.delete(user)
    db.session.commit()
    return success_response({"deleted": True})

# ----------------------------------------------------- MATCH METHODS -----------------------------------------------------

@app.route('/api/users/<int:user_id>/matches/', methods=['GET'])
@jwt_required()
def get_matches(user_id):
    try:
        user = User.query.get(user_id)
        if not user:
            return failure_response("User not found", 404)
        language_id = user.language_id
        users = User.query.filter_by(language_id).all()
        return success_response({ 'users': [user.serialize() for user in users] })
    except Exception as e:
        return failure_response(str(e), 500)
    
@app.route('/api/users/match_info/', methods=['GET'])
@jwt_required()
def get_users_match_info():
    users = User.query.all()
    match_info = []

    for user in users:
        matches = Match.query.filter(
            (Match.user1_id == user.id) | (Match.user2_id == user.id),
                Match.status == 'accepted'
        ).all()

        matched_users = []
        for match in matches:
            if match.user1_id == user.id:
                matched_users.append(match.user2.serialize())
            elif match.user2_id == user.id:
                matched_users.append(match.user1.serialize())

        match_info.append({
            'user': user.serialize(),
            'matched_users': matched_users
        })

    return success_response(match_info)

@app.route('/api/matches/', methods=['POST'])
@jwt_required()
def create_match():
    try:
        data = json.loads(request.data)
        if not all(k in data for k in ['user1_id', 'user2_id']):
            return failure_response("Missing user IDs")
        
        user1 = User.query.get(data['user1_id'])
        user2 = User.query.get(data['user2_id'])

        if not user1 or not user2:
            return failure_response("One or both users not found", 404)

        if user1.level == 1 and user2.level not in [2, 3]:
            return failure_response("Mentee with level 1 can only be matched with a mentor with level 2 or 3", 400)
        if user1.level == 2 and user2.level != 3:
            return failure_response("Mentee with level 2 can only be matched with a mentor with level 3", 400)
        if user1.level == 3 and user2.level not in [1, 2, 3]:
            return failure_response("Mentor with level 3 can only mentor all levels or mentee with level 3", 400)


        if user2.level == 1 and user1.level not in [2, 3]:
            return failure_response("Mentee with level 1 can only be matched with a mentor with level 2 or 3", 400)
        if user2.level == 2 and user1.level != 3:
            return failure_response("Mentee with level 2 can only be matched with a mentor with level 3", 400)
        if user2.level == 3 and user1.level not in [1, 2, 3]:
            return failure_response("Mentor with level 3 can only mentor all levels or mentee with level 3", 400)

        match = Match(
            user1_id=data['user1_id'],
            user2_id=data['user2_id'],
            status='pending',
        )
        db.session.add(match)
        db.session.commit()
        return success_response(match.serialize(), 201)
    except Exception as e:
        return failure_response(str(e), 500)
    
@app.route('/api/matches/<int:match_id>/accept/', methods=['POST'])
@jwt_required()
def accept_match(match_id):
    match = Match.query.get(match_id)
    if not match:
        return failure_response("Match not found", 404)
    
    match.status = 'accepted'
    match.user1.match_status = True
    match.user2.match_status = True
    db.session.commit()
    return success_response(match.serialize())

@app.route('/api/users/<int:user_id>/match_status/', methods=['PUT'])
@jwt_required()
def update_match_status(user_id):
    user = User.query.get(user_id)
    if not user:
        return failure_response("User not found", 404)
    
    data = json.loads(request.data)
    new_status = data.get('match_status')  
    user.match_status = new_status
    db.session.commit()
    return success_response(user.serialize())

@app.route('/api/matches/auto_match/', methods=['POST'])
def auto_match():
    try:
        data = json.loads(request.data)
        user_id = data.get('user_id')
        if not user_id:
            return failure_response("Missing user ID")

        user = User.query.get(user_id)
        if not user:
            return failure_response("User not found", 404)

        if user.match_status:
            return failure_response("User is already matched")

        potential_match = User.query.filter(
            User.id != user_id,
            User.language_id == user.language_id,
            User.match_status == False
        ).first()

        if not potential_match:
            return failure_response("No suitable match found")

            # Use the create_match function to create the match
        match_data = {
                'user1_id': user.id,
                'user2_id': potential_match.id
            }
        request_data_backup = request.data  # Backup the original request data
        request.data = json.dumps(match_data)  # Temporarily replace request data
        response = create_match()  # Call the create_match function
        request.data = request_data_backup  # Restore the original request data
        return response


# ---------------------------------------------------- CHATROOM METHODS ----------------------------------------------------

@app.route('/api/chatroom/', methods=["POST"])
@jwt_required()
def create_chatroom():
    try:
        data = json.loads(request.data)
        if not all(k in data for k in ['user1_id','user2_id']):
            return failure_response("Missing required fields")
        
        match = Match.query.filter(
            (Match.user1_id == data['user1.id']) | (Match.user2_id == data['user2.id']),
                Match.status == 'accepted'
        ).first()

        if not match:
            return failure_response("Users are not matched")

        chatroom = Chatroom(
            user1_id=data['user1_id'],
            user2_id=data['user2_id'],
            active=True,
        )

        db.session.add(chatroom)
        db.session.commit()
        return success_response(chatroom.serialize(), 201)
    except Exception as e:
        return failure_response(str(e), 500)
    
@app.route('/api/chatroom/<int:chatroom_id>/', methods=["PUT"])
@jwt_required()
def close_chatroom(chatroom_id):
    chatroom = Chatroom.query.get(chatroom_id)
    if not chatroom:
        return failure_response("Chatroom not found", 404)
    
    chatroom.active = False
    
    db.session.commit()
    return success_response(chatroom.serialize())

@app.route('/api/chatroom/<int:chatroom_id>/messages/', methods=["POST"])
@jwt_required()
def send_message(chatroom_id):
    try:
        data = json.loads(request.data)
        if not all(k in data for k in ['user_id','content']):
            return failure_response("Missing required fields")
        
        message = save_message(chatroom_id, data['user_id'], data['content'])

        return success_response(message, 201)
    except Exception as e:
        return failure_response(str(e), 500)
    
@app.route('/api/chatroom/<int:chatroom_id>/messages/', methods=["GET"])
@jwt_required()
def get_message_history(chatroom_id):
    chatroom = Chatroom.query.get(chatroom_id)
    if not chatroom:
        return failure_response('Chatroom not found', 404)
    
    message_history = []
    for message in chatroom.messages:
        message_history.append(message.serialize())

    return success_response(json.dumps(message_history))

@app.route('/api/chatroom/<int:chatroom_id>/rate/', methods=["POST"])
@jwt_required()
def give_points(chatroom_id):
    try:
        data = json.loads(request.data)
        if not all(k in data for k in ['sender_id', 'receiver_id', 'points']):
            return failure_response("Missing required fields")
        
        sender = User.query.get(data['sender_id'])
        receiver = User.query.get(data['receiver_id'])
        if not sender or not receiver:
            return failure_response("User not found", 404)
        
        if sender.level not in ['advanced', 'native']:
            return failure_response("Sender level not high enough to rate", 404)
        
        if data['sender_id'] not in [chatroom.user1_id, chatroom.user2_id] or data['receiver_id'] not in [chatroom.user1_id, chatroom.user2_id]:
            return failure_response("Users not part of this chatroom", 403)

        if data['points'] <= 0 or data['points'] > 5:
            return failure_response("Points must be between 1 and 5", 400)

        chatroom = Chatroom.query.get(chatroom_id)
        if not chatroom:
            return failure_response("Chatroom not found", 404)

        receiver.points += data['points']
        db.session.commit()

        return success_response(receiver.serialize(), 200)

    except Exception as e:
        return failure_response(str(e), 500)


@socketio.on('send message')
def socket_message(data):
    chatroom_id = data['chatroom_id']
    user_id = data['user_id']
    content = data['content']

    if not chatroom_id or not user_id or not content:
        emit("error", {"error": "Missing required fields"})
        return

    message = save_message(chatroom_id, user_id, content)

    emit('receive_message', message, room='chatroom_'+str(chatroom_id))

@socketio.on('join chatroom')
def on_join(data):
    chatroom_id = data['chatroom_id']
    user_id = data['user_id']

    if not chatroom_id or not user_id:
        emit("error", {"error": "Missing required fields"})
        return
    
    chatroom = Chatroom.query.get(chatroom_id)
    if not chatroom:
        emit('error', {'msg': 'Chatroom not found'})
        return

    if user_id != chatroom.user1_id and user_id != chatroom.user2_id:
        emit('error', {'msg': 'Not allowed to join this chatroom'})
        return
    
    join_room('chatroom_'+str(chatroom_id))
    emit('user_joined', {"msg": "User joined chatroom "+str(chatroom_id)}, room='chatroom_'+str(chatroom_id))

# Helper for storing messages
def save_message(chatroom_id, user_id, content):
        message = Message(
            chatroom_id=chatroom_id,
            user_id=user_id,
            content=content,
        )

        db.session.add(message)
        db.session.commit()

        return message.serialize()

if __name__ == '__main__':
    socketio.run(app, host="0.0.0.0", port=5000, debug=True, allow_unsafe_werkzeug=True)