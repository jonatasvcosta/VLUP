from django.conf.urls import url, include
from .views import WebsiteViewSet
from rest_framework.routers import SimpleRouter

router = SimpleRouter()
router.register(r'websites', WebsiteViewSet)

urlpatterns = [
    url(r'', include(router.urls)),
]
