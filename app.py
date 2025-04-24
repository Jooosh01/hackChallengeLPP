from flask import Blueprint, request , Flask
import json
from db import User , Language, db, Match
from auth import generate_token
from werkzeug.security import check_password_hash , generate_password_hash

app = Flask(__name__)
db_filename = "lang.db"
app.config["SQLALCHEMY_DATABASE_URI"] = "sqlite:///%s" % db_filename
app.config["SQLALCHEMY_TRACK_MODIFICATIONS"] = False
app.config["SQLALCHEMY_ECHO"] = True

db.init_app(app)
with app.app_context():
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
        return failure_response({'message': 'Could not verify'},401)
    user = User.query.filter_by(netID=auth_data['netID']).first()
    if not user:
        return failure_response({'message': 'User not found'}, 404)
    if check_password_hash(user.password_hash, auth_data['password']):
        token = generate_token(user.id)
        return success_response({'token': token},200)
    return failure_response({'message': 'Wrong password'},401)

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
            password_hash=generate_password_hash(data['password']),#(I have to fix this)
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
def update_match_status(user_id):
    user = User.query.get(user_id)
    if not user:
        return failure_response("User not found", 404)
    
    data = json.loads(request.data)
    new_status = data.get('match_status')  
    user.match_status = new_status
    db.session.commit()
    return success_response(serialize_user(user))

@app.route('/api/users/<int:user_id>/', methods=['GET'])
def get_user(user_id):
    user = User.query.get(user_id)
    if not user:
        return failure_response("User not found", 404)
    return success_response(serialize_user(user))

@app.route('/api/users/', methods=['GET'])
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


if __name__ == '__main__':
    app.run(host="0.0.0.0", port=5000, debug=True)