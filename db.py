from flask_sqlalchemy import SQLAlchemy

db = SQLAlchemy()

class User(db.Model):
    __tablename__ = 'users'
    id = db.Column(db.Integer, primary_key = True)
    netID = db.Column(db.String(50), unique = True , nullable = False)
    name = db.Column(db.String(100), nullable = False)
    password_hash = db.Column(db.String(128), nullable = False)
    level = db.Column(db.String(50), nullable = False)
    match_status = db.Column(db.Boolean, default = False)
    custom_description = db.Column(db.Text)
    language_id = db.Column(db.Integer, db.ForeignKey('language.id'))
    profile_picture_url = db.Column(db.String(256))

    language = db.relationship('Language', back_populates = 'users') #
    match_initiated = db.relationship('Match', foreign_keys = 'Match.user1_id', back_populates = 'user1')
    match_received = db.relationship('Match', foreign_keys = 'Match.user2_id', back_populates = 'user2')
    chatroom_user_1 = db.relationship('Chatroom', foreign_keys = 'Chatroom.user1_id', back_populates = 'user1')
    chatroom_user_2 = db.relationship('Chatroom', foreign_keys = 'Chatroom.user2_id', back_populates = 'user2')

class Language(db.Model):
    __tablename__ = 'language'
    id = db.Column(db.Integer, primary_key = True)
    name = db.Column(db.String(50), unique = True , nullable = False)
    flag_icon_url = db.Column(db.String(200))

    users = db.relationship('User', back_populates = 'language') #

class Match(db.Model):
    __tablename__ = 'matches'
    id = db.Column(db.Integer, primary_key = True)
    user1_id = db.Column(db.Integer, db.ForeignKey('users.id'), nullable = False)
    user2_id = db.Column(db.Integer, db.ForeignKey('users.id'), nullable = False)
    timestamp = db.Column(db.DateTime, server_default = db.func.now())
    status = db.Column(db.String(20), default = 'pending')

    user1 = db.relationship('User', foreign_keys=[user1_id], back_populates='match_initiated')
    user2 = db.relationship('User', foreign_keys=[user2_id], back_populates='match_received')

class Chatroom(db.Model):
    __tablename__ = 'chatroom'
    id = db.Column(db.Integer, primary_key=True)
    user1_id = db.Column(db.Integer, db.ForeignKey('users.id'), nullable = False)
    user2_id = db.Column(db.Integer, db.ForeignKey('users.id'), nullable = False)
    timestamp = db.Column(db.DateTime, server_default = db.func.now())
    active = db.Column(db.Boolean, default = True)

    user1 = db.relationship('User', foreign_keys=[user1_id], back_populates='chatroom_user_1')
    user2 = db.relationship('User', foreign_keys=[user2_id], back_populates='chatroom_user_2')
    messages = db.relationship('Message', back_populates="chatroom")

    def serialize(self):
        return {
            'id': self.id,
            'user1_id': self.user1_id,
            'user2_id': self.user2_id,
            'timestamp': self.timestamp.isoformat() if self.timestamp else None,
            'active': self.active,
        }

class Message(db.Model):
    __tablename__ = 'message'
    id = db.Column(db.Integer, primary_key=True)
    chatroom_id = db.Column(db.Integer, db.ForeignKey('chatroom.id'), nullable = False)
    user_id = db.Column(db.Integer, db.ForeignKey('users.id'), nullable = False)
    content = db.Column(db.String(750), nullable=False)
    timestamp = db.Column(db.DateTime, server_default = db.func.now())

    chatroom = db.relationship('Chatroom', back_populates="messages")

    def serialize(self):
        return {
            'id': self.id,
            'chatroom_id': self.chatroom_id,
            'user_id': self.user_id,
            'content': self.content,
            'timestamp': self.timestamp.isoformat() if self.timestamp else None,
        }