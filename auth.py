from functools import wraps
from flask import request
import jwt
from db import User
import json
from datetime import datetime, timedelta , timezone

def token_required(f):
    @wraps(f)
    def decorated(*args, **kwargs):
        token = None
        
        if 'x-access-token' in request.headers:
            token = request.headers['x-access-token']
        
        if not token:
            return json.dumps({'message': 'Token is missing!'}), 401
            
        try:
            data = jwt.decode(token, 'your-secret-key', algorithms=["HS256"])
            current_user = User.query.get(data['user_id'])
        except Exception as e:
            return json.dumps({'message': 'Token is invalid!', 'error': str(e)}), 401
            
        return f(current_user, *args, **kwargs)
    return decorated

def generate_token(user_id):
    token = jwt.encode({
        'user_id': user_id,
        'exp': datetime.now(timezone.utc) + timedelta(hours=24) 
    }, 'your-secret-key', algorithm="HS256")
    
    return token