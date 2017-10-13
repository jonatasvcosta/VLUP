#!/usr/bin/env python
import sys
import os

if __name__ == "__main__":
    try:
        from django.core.management import execute_from_command_line

        """
        DESCOMENTE A LINHA ABAIXO PARA USAR SUBIR O SERVER LOCALMENTE: python manage.py runserver

        Depois de subir o server, em outro terminal, faça:

        python manage.py shell
        from django.contrib.auth.models import User ; User.objects.create_superuser('root', 'admin@example.com', 'password')

        Com isso seu usuário: root com senha: password está criado. Uma vez criado em seu banco de dados local,
        ele só é excluído se você deletar o arquivo 'mydatabase'
        """
        #os.environ.setdefault("DJANGO_SETTINGS_MODULE", "vlup.settings_development")
        
    except ImportError:
        # The above import may fail for some other reason. Ensure that the
        # issue is really that Django is missing to avoid masking other
        # exceptions on Python 2.
        try:
            import django
        except ImportError:
            raise ImportError(
                "Couldn't import Django. Are you sure it's installed and "
                "available on your PYTHONPATH environment variable? Did you "
                "forget to activate a virtual environment?"
            )
        raise
    execute_from_command_line(sys.argv)

# flake8: noqa
