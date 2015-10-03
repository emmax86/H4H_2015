from __init__ import db
from werkzeug.security import generate_password_hash, check_password_hash


class User(db.Model):
    __tablename__ = "user"
    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    name = db.String(64)
    phone_number = db.String(15)
    password_hash = db.String(120)

    def __init__(self, name, phone, password):
        self.name = name
        self.phone_number = phone
        self.password_hash = generate_password_hash(password, "pbkdf2:sha256:10000")

    def verify_password(self, password):
        return check_password_hash(self.password_hash, password)
