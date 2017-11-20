from django.conf.urls import url, include
from .views import WebsiteViewSet, ArticleViewSet, ArticleSimilarity
from rest_framework.routers import SimpleRouter

router = SimpleRouter()
router.register(r'websites', WebsiteViewSet)
router.register(r'article', ArticleViewSet)

urlpatterns = [
    url(r'', include(router.urls)),
    url(r'article/similarity', ArticleSimilarity.as_view())
]
