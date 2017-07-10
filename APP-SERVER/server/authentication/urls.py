from django.conf.urls import url, include
from rest_framework_jwt.views import obtain_jwt_token
from .views import UserProfileViewSet, CreateUserView
from rest_framework.routers import SimpleRouter

router = SimpleRouter()
router.register(r'^users', UserProfileViewSet)

urlpatterns = [
    url(r'^', include(router.urls)),
    url(r'^users2', CreateUserView.as_view()),
    url(r'^api-auth/', include('rest_framework.urls',
                               namespace='rest_framework')),
    url(r'^api-token-auth/', obtain_jwt_token),
]
