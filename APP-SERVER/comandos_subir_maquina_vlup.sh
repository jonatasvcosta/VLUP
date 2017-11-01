sudo yum update -y
sudo yum install docker -y
sudo gpasswd -a ec2-user docker
sudo yum install epel-release -y 
sudo yum install python-pip -y 
sudo yum install git -y
sudo pip install docker-compose
cd /home/ec2-user
eval `ssh-agent -s`
echo '-----BEGIN RSA PRIVATE KEY-----
MIIEogIBAAKCAQEA7YDiWITPBlVgdvVnupGcohX8qIPiVPCJiPT+k/bphB8ge5yA
S0PWnKOUSEAMb+L1kn/bSTW3Kqog12XLvQ81tL7r7dlFlfjOS7gi+orR9ib7jBGc
Q5sHEnrca10K7kin0QhWWIBYlBuH6K7C0Tfa17xBNMOdumDkMum3aSqh/MH/5RH/
nir/8KC7/nCtQfOGSTWfDW/x5GQ1AQo3ruZGmO1/0O/RkSfb5Y8h4OR2MVrzoJaw
LssfPskKhTYpz8dd14PDT3dKg1XsUiaS8CLqMX0Zzh+2tuZcs9vOFfS8EEQ6Be+r
JZaznrSSF5g5wc4UcDh7mwhCG+nd2P1TuZGasQIDAQABAoIBAAongoXqV7ebmt47
K84sP5gmfIDv8pNTHW4/m0rUObOuaQMf89RD+D8GN2bHZkC6YWl8EfIVW9l1IKZU
8dsJdXBe4VNwAKui0Z8nJkez+7ocfJDGh/nT/P9rRVVJyxfY/2POiXKLOj5K1wq5
vhaX3sFuiRMqcenwDpGmsHLModRemexHyixd4d09/FAPTTXjZhQqwZsVUeqAps1d
xjfLiPM3mmyhlzPXkPvLMn4q8gVYJWZktrq4baw8/RJr3XhzYaXzau1G7LczZWft
neQuEuiNPYCDOwBr/ATRwEfjSDZDxBQIKvcf4h2fRVOrmom1RprX9pRIj6IRyPg+
aeCoeKkCgYEA/b2My74PwJ0LpsioDamBNNE4YIuambuhfri8jIHxGWK+M3KRet2s
l6tHOrr50UeLsS7jt40RdXmxqfdk8hWj2g/xN9v78AXj3rFrRhP1mFpjxt2+PLbQ
kP2kSX82YvAwmGeHekWzaymetxkL3rYNscWoCamyHkksPnk2u+GcXOsCgYEA755R
odzP8WHZR9I7oAjzU4S0LJ1XK/Shs7j+OHZvt9UbOyvAemJ2n3d6Ty11rpJEr0zH
JrsEulZMbk/4VMB00a6fzbenBptnCMGHbB/4xxf0CvefdxvE/YXQuZzQPwlrrJnQ
/CGXhVm7gHVrFJOIztRbUwoVR5R77cLes7Ptz9MCgYBVs0q1mdkKLkC8MTOJgcGx
DCUlfqFV4SiXe1+7eTvsXgxtpfl67RoZE/F35aTwyWBmFLc2zH+kFNkr1fITTbnM
seu/+R3roKGpGnVn8t/VeFoQ1d4l9X0WK4TsifnditBu+TCp3JbPz/vaTaxTqVuQ
uniZ4uXVEtPNufUBmh7MxwKBgDafxMQ9bjn8sGerCWSmJbI4Ykp5Y9HlZqdt2KZO
eZFSx25pvusnXxtjRUKffmBZf8bmnc+6eo1v9uR+3oGTjg3u3QcsgqsC6i4Y0CJZ
94CLeVq2sGeasQGKa9akZOS55Q3n0rhnf/8qQZA7NDnwmBNuXZNEjS1QMCA3eEVH
E0N5AoGADd7S/IaQVk/dNjbEizUNMTjhMAffQIYZ2elqIvsPDq3XJxGONIqCBh47
efXf+pR5HF/NFUCAFF/Z2yCtoIJXH15U4snNJCC5oS0B5HKVGEvVOsC9uGJNVqeq
JSxygYzynUkuUhzM0p7uHX0vNeB3ENN8vZ12muMq8jZJrsIeOq4=
-----END RSA PRIVATE KEY-----' > ~/.ssh/vlup_access_tcc
ssh-add /home/ec2-user/.ssh/vlup_access_tcc
chmod 400 ~/.ssh/vlup_access_tcc
git clone ssh://git@github.com:jonatasvcosta/TCC.github
chmod 750 /home/ec2-user/ -R
sudo mv /usr/bin/java /usr/bin/java_old
sudo ln -s /usr/bin/java8 /usr/bin/java
sh ~/TCC/APP-SERVER/build.sh &


//jenkins
docker run -p 8080:8080 -p 50000:50000 jenkins/jenkins:lts &

// configurar a autenticação e a autorização
// referência
https://wiki.jenkins.io/display/JENKINS/GitHub+OAuth+Plugin

// 1) Criar uma nova aplicação no GitHub
https://github.com/settings/applications/new

// 2) instalar o GitHub Authentication Plugin no jenkins

// 3) Manage Jenkins -> Configure Global Security
// escolha Github Authentication Plugin
// insira o Client ID e a Client Secret geradas no GitHub no passo anterior

// 4) Escolha Logged-in users can do anything em Authorization