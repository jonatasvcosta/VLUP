import requests
import coreapi
import coreschema
from rest_framework import viewsets, authentication
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework.permissions import IsAuthenticated
from rest_framework.decorators import detail_route
from .serializer import ShortLessonsSerializer
from .models import ShortLessons


class ShortLessonsViewSet(viewsets.ModelViewSet):
    queryset = ShortLessons.objects.all()
    serializer_class = ShortLessonsSerializer
    filter_fields = ('title', 'votes', 'original_language', 'final_language', 'video_url', 'tags', 'content')