from flask import Flask, Blueprint, request, render_template, session, redirect
from flask_jwt_extended import JWTManager, jwt_required, create_access_token, get_jwt_identity
import json
from db import db, User, Language, Match, Chatroom, Message
from auth import generate_token
from werkzeug.security import check_password_hash , generate_password_hash
from flask_socketio import join_room, leave_room, emit, SocketIO
from string import ascii_uppercase
import os 


app = Flask(__name__)
db_filename = "lang.db"
app.config["SQLALCHEMY_DATABASE_URI"] = os.getenv("DATABASE_URL", "sqlite:///lang.db")
app.config["SQLALCHEMY_TRACK_MODIFICATIONS"] = False
app.config["SQLALCHEMY_ECHO"] = True
app.config["JWT_SECRET_KEY"] = os.getenv("JWT_SECRET_KEY", "default_jwt_secret")
socketio = SocketIO(app, cors_allowed_origins="*")

db.init_app(app)
jwt = JWTManager(app)

with app.app_context():
    db.drop_all()
    db.create_all()

def success_response(data, status=200):
    return json.dumps({'success': True, 'data': data}), status

def failure_response(message, status=400):
    return json.dumps({'success': False, 'error': message}), status

def serialize_user(user):
    return {
        'id': user.id,
        'netID': user.netID,
        'name': user.name,
        'level': user.level,
        'match_status': user.match_status,
        'description': user.custom_description,
        'language': serialize_language(user.language)
    }

def serialize_language(language):
    if not language:
        return None
    return {
        'id': language.id,
        'name': language.name,
        'flag_icon_url': language.flag_icon_url
    }

def serialize_match(match):
    return {
        'id': match.id,
        'user1': serialize_user(match.user1),
        'user2': serialize_user(match.user2),
        'status': match.status,
        'timestamp': match.timestamp.isoformat() if match.timestamp else None
    }

@app.route('/login/', methods=['POST'])
def login():
    auth_data = json.loads(request.data)

    if not auth_data or not auth_data.get('netID') or not auth_data.get('password'):
        return failure_response("Missing login credentials", 401)

    user = User.query.filter_by(netID=auth_data['netID']).first()
    if not user or not check_password_hash(user.password_hash, auth_data['password']):
        return failure_response("Invalid credentials", 401)

    token = create_access_token(identity=user.id)
    return success_response({'token': token, 'user': serialize_user(user)}, 200)

@app.route('/api/languages/', methods=['GET'])
def get_languages():
    languages = Language.query.all()
    return success_response({
        'languages': [serialize_language(lang) for lang in languages]
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
            custom_description=data.get('description', '')
        )
        db.session.add(user)
        db.session.commit()
        return success_response(serialize_user(user), 201)
    except Exception as e:
        return failure_response(str(e), 500)

@app.route('/api/matches/', methods=['POST'])
@jwt_required()
def create_match():
    try:
        data = json.loads(request.data)
        if not all(k in data for k in ['user1_id', 'user2_id']):
            return failure_response("Missing user IDs")
        
        match = Match(
            user1_id=data['user1_id'],
            user2_id=data['user2_id'],
            status='pending'
        )
        db.session.add(match)
        db.session.commit()
        return success_response(serialize_match(match), 201)
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
    return success_response(serialize_match(match))

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
    return success_response(serialize_user(user))

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

        match = Match(
            user1_id=user.id,
            user2_id=potential_match.id,
            status='pending'
        )
        db.session.add(match)
        db.session.commit()
        update_match_status(user.id)
        update_match_status(potential_match.id)

        return success_response(serialize_match(match), 201)
    except Exception as e:
        return failure_response(str(e), 500)

@app.route('/api/users/<int:user_id>/', methods=['GET'])
@jwt_required()
def get_user(user_id):
    user = User.query.get(user_id)
    if not user:
        return failure_response("User not found", 404)
    return success_response(serialize_user(user))

@app.route('/api/users/', methods=['GET'])
@jwt_required()
def get_all_users():
    users = User.query.all()
    return success_response({
        'users': [serialize_user(user) for user in users]
    })

## just to test get_languages
@app.route('/init/languages/', methods= ['GET'])
def init_languages():
    lang1 = Language(name="Spanish", flag_icon_url="https://expatnetwork.com/passport-visa-requirements-spain/spain-flag/")
    lang2 = Language(name="French", flag_icon_url="https://en.wikipedia.org/wiki/Flag_of_France#/media/File:Flag_of_France.svg")
    db.session.add_all([lang1, lang2])
    db.session.commit()
    return success_response("Languages initialized")

## Chatroom and Message APIs
@app.route('/api/chatroom/', methods=["POST"])
def create_chatroom():
    try:
        data = json.loads(request.data)
        if not all(k in data for k in ['user1_id','user2_id']):
            return failure_response("Missing required fields")
        
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
def close_chatroom(chatroom_id):
    chatroom = Chatroom.query.get(chatroom_id)
    if not chatroom:
        return failure_response("Chatroom not found", 404)
    
    chatroom.active = False
    
    db.session.commit()
    return success_response(chatroom.serialize())

@app.route('/api/chatroom/<int:chatroom_id>/messages/', methods=["POST"])
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
def get_message_history(chatroom_id):
    chatroom = Chatroom.query.get(chatroom_id)
    if not chatroom:
        return failure_response('Chatroom not found', 404)
    
    message_history = []
    for message in chatroom.messages:
        message_history.append(message.serialize())

    return success_response(json.dumps(message_history))

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
    socketio.run(app, host="0.0.0.0", port=5000, debug=True)