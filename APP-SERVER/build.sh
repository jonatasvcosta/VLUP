docker stop appserver_reverseproxy_1 appserver_vlup_1 appserver_database_1 ; docker rm  appserver_reverseproxy_1 appserver_vlup_1 appserver_database_1  ;
git fetch ;
git pull origin master
docker-compose build
docker-compose up &