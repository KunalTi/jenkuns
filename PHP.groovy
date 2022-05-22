
pipeline{
    agent any
        stages{
        stage('PHP-Install'){
            steps{
                echo 'Installing...'
                sh'''
                sudo apt-get update -y
                sudo apt-get install apache2 -y
                sudo apt-get install php libapache2-mod-php -y
                sudo apt install php-dev libmcrypt-dev php-pear -y
                sudo pecl channel-update pecl.php.net
                sudo pecl install mcrypt-1.0.1 -y
                sudo service apache2 restart
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
