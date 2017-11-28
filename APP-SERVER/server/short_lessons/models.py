from django.db import models

class ShortLessons(models.Model):
    title = models.TextField(null=False)
    votes = models.IntegerField(default=0)
    original_language = models.CharField(max_length=5, null=False)
    final_language = models.CharField(max_length=5, null=False)
    video_url = models.TextField()
    tags = models.TextField()
    content =  models.TextField(null=False)
