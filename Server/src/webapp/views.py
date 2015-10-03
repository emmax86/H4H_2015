from __init__ import app, db
from flask import request
from flask import abort
from flask import json
from models import *


@app.route("/")
def hello():
    return "Guard Dog API v0.1.0"


@app.route("/create")
def create():
    db.create_all()
    return "Great Success", 200


@app.route("/data", methods=["POST"])
def data():
    def verify_structure(json):
        if not request.json:
            return False
        elif ("real" not in json) or ("frames" not in json) or ("phone_number" not in json):
            return False
        return True

    if not verify_structure(request.json):
        abort(401)

    real = bool(request.json["real"])

    user = User.query.filter_by(phone_number=request.json["phone_number"]).first()

    if not user:
        abort(401)

    happening = Incident(real, user)
    db.session.add(happening)
    db.session.commit()

    frames = request.json["frames"]
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
    json = request.json
    name = json["name"]
    phone = json["phone_number"]
    password = json["password"]
    warranty = json["warranty"]

    if User.query.filter_by(phone_number=phone).first():
        return "Error, phone number already registered", 401

    user = User(name, phone, password, warranty)

    db.session.add(user)
    db.session.commit()

    return "", 200
