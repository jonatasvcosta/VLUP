from rest_framework import serializers
import django.contrib.auth.password_validation as password_validation
from django.contrib.auth import get_user_model
import django.core.exceptions as exceptions
from .models import UserProfile
UserModel = get_user_model()


class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = UserModel


class UserProfileSerializer(serializers.ModelSerializer):

    id = serializers.IntegerField(source='pk', read_only=True)
    username = serializers.CharField(source='user.username', read_only=True)
    password = serializers.CharField(source='user.password', write_only=True)
    email = serializers.EmailField(source='user.email')

    class Meta:
        model = UserProfile
        fields = (
            'id', 'username', 'email', 'password',
            'name', 'native_language', 'learning_language',
            'latitude', 'longitude',
            'created_at', 'updated_at',
        )
        read_only_fields = ('created_at', 'updated_at',)

    def validate(self, data):
        user_data = data.get('user', None)
        user = UserModel(**user_data)

        # get the password from the data
        password = user_data.get('password')

        errors = dict()
        try:
            # validate the password and catch the exception
            password_validation.validate_password(password=password, user=user)
        except exceptions.ValidationError as e:
            errors['password'] = list(e.messages)

        if errors:
            raise serializers.ValidationError(errors)

        return super(UserProfileSerializer, self).validate(data)

    def update(self, instance, validated_data):
        # First, update the User
        user_data = validated_data.pop('user', None)
        for attr, value in user_data.items():
            setattr(instance.user, attr, value)
        instance.user.username = user_data['email']
        instance.user.set_password(user_data['password'])
        # Then, update UserProfile
        for attr, value in validated_data.items():
            setattr(instance, attr, value)
        instance.save()
        return instance

    def create(self, validated_data):

        user_data = validated_data.pop('user')
        user_data['username'] = user_data['email']
        user = UserModel.objects.create(**user_data)
        user.set_password(user_data['password'])

        profile = UserProfile.objects.create(user=user, **validated_data)
        return profile
