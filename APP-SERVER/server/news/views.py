from rest_framework import viewsets
from .serializer import WebsiteSerializer
from .models import Website


class WebsiteViewSet(viewsets.ModelViewSet):
    queryset = Website.objects.all()
    serializer_class = WebsiteSerializer
    filter_fields = ("language", "name")
