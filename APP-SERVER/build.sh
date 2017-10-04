docker-compose down;
eval `ssh-agent -s`
ssh-add .ssh/vlup_access_tcc
git fetch;
git pull origin master;
docker-compose build;
docker-compose up -d;