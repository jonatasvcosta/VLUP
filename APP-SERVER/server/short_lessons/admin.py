from django.contrib import admin
from .models import ShortLessons


@admin.register(ShortLessons)
class ShortLessonsAdmin(admin.ModelAdmin):
    list_display = ('title', 'votes', 'original_language', 'final_language', 'video_url', 'tags', 'content')
