from __init__ import app, db
from flask import request
from models import *


@app.route("/")
def hello():
    return "Guard Dog API v0.1.0"


@app.route("/register")
def register():
    json = request.json
    name = json["name"]
    phone = json["phone_number"]
    password = json["password"]
    user = User(name, phone, password)

    if User.query.filter_by(phone_number=phone):
        return "Error, phone number already registered", 401

    db.session.add(user)
    db.session.commit()

    return "", 200
