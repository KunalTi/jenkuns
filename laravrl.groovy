
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                echo 'Building..'
                sh '''
                sudo apt-get update -y
                sudo apt-add-repository ppa:ondrej/php
                sudo apt-get install php7.2-cli php7.2-fpm php7.2-fpm php7.2-xml php7.2-gd php7.2-mysql php7.2-mbstring php7.2-curl php7.1-mcrypt -y
                php -v
                sudo apt-ger install mysql-server -y
                sudo apt install nginx -y
                sudo apt install zip unzip -y
                '''
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}
