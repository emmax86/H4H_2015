from __init__ import app
from flask import request


@app.route("/")
def hello():
    return "Guard Dog API v0.1.0"
