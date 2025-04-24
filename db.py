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

    language = db.relationship('Language', back_populates = 'users')
    match_initiated = db.relationship('Match', foreign_keys = 'Match.user1_id', back_populates = 'user1')
    match_received = db.relationship('Match', foreign_keys = 'Match.user2_id', back_populates = 'user2')

class Language(db.Model):
    __tablename__ = 'language'
    id = db.Column(db.Integer, primary_key = True)
    name = db.Column(db.String(50), unique = True , nullable = False)
    flag_icon_url = db.Column(db.String(200))

    users = db.relationship('User', back_populates = 'language')

class Match(db.Model):
    __tablename__ = 'matches'
    id = db.Column(db.Integer, primary_key = True)
    user1_id = db.Column(db.Integer, db.ForeignKey('users.id'), nullable = False)
    user2_id = db.Column(db.Integer, db.ForeignKey('users.id'), nullable = False)
    timestamp = db.Column(db.DateTime, server_default = db.func.now())
    status = db.Column(db.String(20), default = 'pending')

    user1 = db.relationship('User', foreign_keys=[user1_id], back_populates='match_initiated')
    user2 = db.relationship('User', foreign_keys=[user2_id], back_populates='match_received')

