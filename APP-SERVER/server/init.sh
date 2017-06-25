#!/bin/sh

./wait-for database:5432

python /app/manage.py collectstatic --noinput
python /app/manage.py syncdb --noinput
python /app/manage.py makemigrations
python /app/manage.py migrate --noinput

if [ "$DJANGO_PRODUCTION" != "true" ]; then
    # Create superuser in development
    echo "Creating Django superuser named 'root'..."
    echo "import os; from django.contrib.auth.models import User; print('Root user already exists') if User.objects.filter(username='root') else User.objects.create_superuser('root', 'admin@example.com', os.environ['ROOT_PASSWORD'])" | python /app/manage.py shell
fi

# Start app
gunicorn vlup.wsgi