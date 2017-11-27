# -*- coding: utf-8 -*-
# Generated by Django 1.11.6 on 2017-11-27 11:31
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='ShortLessons',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('title', models.TextField()),
                ('votes', models.IntegerField(default=0)),
                ('original_language', models.CharField(max_length=5)),
                ('final_language', models.CharField(max_length=5)),
                ('video_url', models.TextField()),
                ('tags', models.TextField()),
                ('content', models.TextField()),
            ],
        ),
    ]