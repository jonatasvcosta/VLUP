docker-compose down;
eval `ssh-agent -s`
ssh-add /home/ec2-user/.ssh/vlup_access_tcc
git fetch;
git pull origin master;
docker-compose build;
docker-compose up -d;
