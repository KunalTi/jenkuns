
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                echo 'Building..'
                sh '''
                sudo apt update -y
                sudo apt-add-repository ppa:ondrej/php
                sudo apt install php7.2-cli php7.2-fpm php7.2-fpm php7.2-xml php7.2-gd php7.2-mysql php7.2-mbstring php7.2-curl php7.1-mcrypt -y
                php -v
                sudo apt install nginx -y
                sudo apt install zip unzip -y
                rm -rf aws-laravel
                git clone https://github.com/KunalTi/aws-laravel.git
                cd aws-laravel
                curl -aS https://getcomposer.org/installer | sudo php -- --install-dir /usr/local/bin/ --filename=composer
                composer install --no-dev
                cp .env.example .env
                php artisan key:generate
                cd ~
                cd /etc/nginx/sites-available/
                sudo rm -rf default
                sudo git clone https://github.com/KunalTi/demo.git
                cd /var/www/
                sudo rm -rf html
                cd /home/ubuntu/
                mv aws-laravel html
                sudo mv html /var/www/
                cd /var/www/html/
                sudo chmod -R 777 storage
                sudo systemctl start nginx
                echo "done"
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