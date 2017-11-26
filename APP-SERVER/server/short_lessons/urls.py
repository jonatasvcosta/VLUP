from .views import ShortLessonsViewSet
from rest_framework.routers import SimpleRouter


router = SimpleRouter()
router.register(r'short_lessons', ShortLessonsViewSet)
urlpatterns = router.urls