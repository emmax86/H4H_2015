from __init__ import app, db
from flask import request
from flask import abort
from flask import json
from models import *


@app.route("/")
def hello():
    return "Guard Dog API v0.1.1"


@app.route("/create")
def create():
    db.create_all()
    return "Great Success", 200


@app.route("/login", methods=["POST"])
def login():
    obj = request.get_json()
    username = obj["username"]
    password = obj["password"]

    user = User.query.filter_by(username=username).first()

    if not user:
        return "Great Failure", 401

    if user.verify_password(password):
        return "Great Success", 200
    else:
        return "Great Failure", 401


@app.route("/data", methods=["POST"])
def data():
    def verify_structure(obj):
        if not obj:
            return False
        elif ("real" not in obj) or ("frames" not in obj) or ("username" not in obj):
            return False
        return True

    if not verify_structure(request.get_json()):
        abort(401)

    real = bool(request.get_json()["real"])

    user = User.query.filter_by(username=request.get_json()["username"]).first()

    if not user:
        abort(401)

    happening = Incident(real, user)
    db.session.add(happening)
    db.session.commit()

    frames = request.get_json()["frames"]
    for each in sorted(frames, key=lambda frame: frame["batch_order"]):
        frame = AccelerationFrame(each["batch_order"], each["accel_x"], each["accel_y"], each["accel_z"], happening)
        db.session.add(frame)

    db.session.commit()
    return "Great Success", 200


@app.route("/debug_db")
def debug_db():
    return json.dumps(
        {
            "Incidents": [each.serialize() for each in Incident.query.all()]
        }
    ), 200


@app.route("/register", methods=["POST"])
def register():
    obj = request.get_json()
    username = obj["username"]
    phone = obj["phone_number"]
    password = obj["password"]
    warranty = obj["warranty"]

    if User.query.filter_by(username=username).first():
        return "Error, phone number already registered", 401

    user = User(username, phone, password, warranty)

    db.session.add(user)
    db.session.commit()

    return "", 200
