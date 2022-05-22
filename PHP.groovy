
pipeline{
    agent any
        stages{
        stage('PHP-Install'){
            steps{
                echo 'Installing...'
                git branch: 'main', credentialsId: 'kunal', url: 'https://github.com/KunalTi/jenkuns.git'
                sh'''
                sudo apt-get update -y
                sudo apt-get install apache2 -y
                sudo apt-get install php libapache2-mod-php -y
                sudo apt install php-dev libmcrypt-dev php-pear -y
                sudo pecl channel-update pecl.php.net
                sudo pecl install mcrypt-1.0.4 --skipInstalled
                sudo service apache2 restart
                sudo rm -rf /var/www/html/index.html
                sudo cp /var/lib/jenkins/workspace/'PHP Pipeline'/test.php /var/www/html/test.php
                cd /var/www/html/
                https://github.com/hrishavtandukar/PHPTrialApplication.git
                '''
            }
        }
        stage('Test'){
            steps{
                echo 'Testing..'
            }
        }
        stage('Deploy'){
            steps{
                echo 'Deploying....'
            }
        }
    }
}
