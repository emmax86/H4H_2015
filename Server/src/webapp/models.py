from __init__ import db
from werkzeug.security import generate_password_hash, check_password_hash


class User(db.Model):
    __tablename__ = "user"
    id = db.Column(db.Integer, primary_key=True, autoincrement=True, index=True)
    name = db.Column(db.String(64))
    phone_number = db.Column(db.String(15))
    password_hash = db.Column(db.String(120))

    def __init__(self, name, phone, password):
        self.name = name
        self.phone_number = phone
        self.password_hash = generate_password_hash(password, "pbkdf2:sha256:10000")

    def verify_password(self, password):
        return check_password_hash(self.password_hash, password)


class Incident(db.Model):
    __tablename__ = "incident"
    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    real = db.Column(db.Boolean, default=True)
    frames = db.relationship("AccelerationFrame", backref="incident")

    def __init__(self, real):
        self.real = real


class AccelerationFrame(db.Model):
    __tablename__ = "frame"
    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    accel_x = db.Column(db.Float)
    accel_y = db.Column(db.Float)
    accel_z = db.Column(db.Float)
    incident_id = db.Column(db.Integer, db.ForeignKey("incident.id"))
