#!/bin/sh

sleep 5;

python /app/manage.py collectstatic --noinput
python /app/manage.py makemigrations
python /app/manage.py migrate --noinput

echo "Creating Django superuser named 'root'..."
echo "import os; from django.contrib.auth.models import User; print('Root user already exists') if User.objects.filter(username='root') else User.objects.create_superuser('root', 'admin@example.com', os.environ['ROOT_PASSWORD'])" | python /app/manage.py shell

# Start app
if [ "$DJANGO_PRODUCTION" == "true" ]; then
    gunicorn vlup.wsgi
else
    gunicorn vlup.wsgi
    #python /app/manage.py runserver 0.0.0.0:8000
fi
