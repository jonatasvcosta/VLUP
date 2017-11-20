import werkzeug
from werkzeug.exceptions import default_exceptions
from flask import Flask, jsonify, request
from common.database import DBSession, initialize_sql

app = Flask(__name__)


@app.before_first_request
def initialize():
    initialize_sql()


@app.teardown_appcontext
def shutdown(exception=None):
    DBSession.remove()


def exception_handler(error):
    return jsonify({
        'status_code': error.code,
        'message': str(error),
        'description': error.description
    }), error.code


def register_routes(app):
    from .similarity import bp as sim_bp
    from .similar_words import bp as sw_bp
    from .translation import bp as tr_bp
    from .synonymous import bp as sy_bp
    from .keywords import bp as kw_bp
    app.register_blueprint(sim_bp, url_prefix='/similarity')
    app.register_blueprint(sw_bp, url_prefix='/similar_words')
    app.register_blueprint(sy_bp, url_prefix='/synonymous')
    app.register_blueprint(kw_bp, url_prefix='/keywords')

def create_app(app):
    register_routes(app)

    for code in default_exceptions:
        app.errorhandler(code)(exception_handler)

create_app(app)
