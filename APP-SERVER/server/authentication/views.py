from rest_framework import permissions, generics
from rest_framework import viewsets
from django.contrib.auth import get_user_model
from .serializers import UserSerializer, UserProfileSerializer
from .models import UserProfile

UserModel = get_user_model()


class UserViewSet(viewsets.ModelViewSet):
        queryset = UserModel.objects.all()
        serializer_class = UserSerializer


class UserProfileViewSet(viewsets.ModelViewSet):
        queryset = UserProfile.objects.all()
        serializer_class = UserProfileSerializer


class CreateUserView(generics.CreateAPIView):
    model = UserModel
    permission_classes = [
        permissions.AllowAny
    ]
    serializer_class = UserProfileSerializer
