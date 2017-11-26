from rest_framework import serializers
from .models import ShortLessons


class ShortLessonsSerializer(serializers.ModelSerializer):
    class Meta:
        model = ShortLessons
        fields = '__all__'
