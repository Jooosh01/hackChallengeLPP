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
    description = db.Column(db.Text)
    language_id = db.Column(db.Integer, db.ForeignKey('language.id'))
    profile_picture_url = db.Column(db.String(256))
    points = db.Column(db.Integer, default = 0)

    language = db.relationship('Language', back_populates = 'users') 
    match_initiated = db.relationship('Match', foreign_keys = 'Match.user1_id', back_populates = 'user1')
    match_received = db.relationship('Match', foreign_keys = 'Match.user2_id', back_populates = 'user2')
    chatroom_user_1 = db.relationship('Chatroom', foreign_keys = 'Chatroom.user1_id', back_populates = 'user1')
    chatroom_user_2 = db.relationship('Chatroom', foreign_keys = 'Chatroom.user2_id', back_populates = 'user2')

    def serialize(self):
        return {
            'id': self.id,
            'netID': self.netID,
            'name': self.name,
            'level': self.level,
            'match_status': self.match_status,
            'description': self.description,
            'language': self.language.serialize()
        }

class Language(db.Model):
    __tablename__ = 'language'
    id = db.Column(db.Integer, primary_key = True)
    name = db.Column(db.String(50), unique = True , nullable = False)
    flag_url = db.Column(db.String(200))

    users = db.relationship('User', back_populates = 'language')

    def serialize(self):
        return {
            'id': self.id,
            'name': self.name,
            'flag_url': self.flag_url
        }

class Match(db.Model):
    __tablename__ = 'matches'
    id = db.Column(db.Integer, primary_key = True)
    user1_id = db.Column(db.Integer, db.ForeignKey('users.id'), nullable = False)
    user2_id = db.Column(db.Integer, db.ForeignKey('users.id'), nullable = False)
    timestamp = db.Column(db.DateTime, server_default = db.func.now())
    status = db.Column(db.String(20), default = 'pending')

    user1 = db.relationship('User', foreign_keys=[user1_id], back_populates='match_initiated')
    user2 = db.relationship('User', foreign_keys=[user2_id], back_populates='match_received')

    def serialize(self):
        return {
            'id': self.id,
            'user1': self.user.serialize(),
            'user2': self.user2.serialize(),
            'status': self.status,
            'timestamp': self.timestamp.isoformat() if self.timestamp else None
        }

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
    

def add_langauges():
    initial_languages = [
        { 'name': 'Arabic', 'flag_url': 'https://lpphack.s3.us-east-2.amazonaws.com/arabic.png' },
        { 'name': 'Bengali', 'flag_url': 'https://lpphack.s3.us-east-2.amazonaws.com/bengali.png' },
        { 'name': 'English', 'flag_url': 'https://lpphack.s3.us-east-2.amazonaws.com/english.png' },
        { 'name': 'French', 'flag_url': 'https://lpphack.s3.us-east-2.amazonaws.com/french.png' },
        { 'name': 'German', 'flag_url': 'https://lpphack.s3.us-east-2.amazonaws.com/german.png' },
        { 'name': 'Hindi', 'flag_url': 'https://lpphack.s3.us-east-2.amazonaws.com/hindi.png' },
        { 'name': 'Indonesian', 'flag_url': 'https://lpphack.s3.us-east-2.amazonaws.com/indonesian.png' },
        { 'name': 'Italian', 'flag_url': 'https://lpphack.s3.us-east-2.amazonaws.com/italian.png' },
        { 'name': 'Jamaican Patois', 'flag_url': 'https://lpphack.s3.us-east-2.amazonaws.com/jamaican-patois.png' },
        { 'name': 'Japanese', 'flag_url': 'https://lpphack.s3.us-east-2.amazonaws.com/japanese.png' },
        { 'name': 'Korean', 'flag_url': 'https://lpphack.s3.us-east-2.amazonaws.com/korean.png' },
        { 'name': 'Mandarin', 'flag_url': 'https://lpphack.s3.us-east-2.amazonaws.com/mandarin.png' },
        { 'name': 'Polish', 'flag_url': 'https://lpphack.s3.us-east-2.amazonaws.com/polish.png' },
        { 'name': 'Portuguese', 'flag_url': 'https://lpphack.s3.us-east-2.amazonaws.com/portuguese.png' },
        { 'name': 'Russian', 'flag_url': 'https://lpphack.s3.us-east-2.amazonaws.com/russian.png' },
        { 'name': 'Spanish', 'flag_url': 'https://lpphack.s3.us-east-2.amazonaws.com/spanish.png' },
        { 'name': 'Swahili', 'flag_url': 'https://lpphack.s3.us-east-2.amazonaws.com/swahili.png' },
        { 'name': 'Tagalog', 'flag_url': 'https://lpphack.s3.us-east-2.amazonaws.com/tagalog.png' },
        { 'name': 'Taiwanese', 'flag_url': 'https://lpphack.s3.us-east-2.amazonaws.com/taiwanese.png' },
        { 'name': 'Thai', 'flag_url': 'https://lpphack.s3.us-east-2.amazonaws.com/thai.png' },
        { 'name': 'Turkish', 'flag_url': 'https://lpphack.s3.us-east-2.amazonaws.com/turkish.png' },
        { 'name': 'Twi', 'flag_url': 'https://lpphack.s3.us-east-2.amazonaws.com/twi.png' },
        { 'name': 'Urdu', 'flag_url': 'https://lpphack.s3.us-east-2.amazonaws.com/urdu.png' },
        { 'name': 'Vietnamese', 'flag_url': 'https://lpphack.s3.us-east-2.amazonaws.com/vietnamese.png' },
        { 'name': 'Yoruba', 'flag_url': 'https://lpphack.s3.us-east-2.amazonaws.com/yoruba.png' }
    ]

    for lang in initial_languages:
        language = Language(
            name=lang['name'],
            flag_url=lang['flag_url'],
        )
        db.session.add(language)

    db.session.commit()

