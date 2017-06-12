from cornice.resource import resource
from app.models import DBSession
from app.models.domain import user


@resource(collection_path='/users', path='/users/{id}')
class UserResource(object):

    def __init__(self, request):
        self.request = request

    def collection_get(self):
        return DBSession.query(user.User).all()

    def get(self):
        return DBSession.query(user.User).filter(user.User.user_id == int(self.request.matchdict['id'])) # noqa

    def collection_post(self):
        return True
