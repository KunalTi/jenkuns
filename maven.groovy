pipeline{
    agent any 
        stages{
            stage('git-pull'){
                steps{
                    sh 'sudo apt-get update -y'
                    sh 'sudo apt-get install git -y'
                    echo "Starting to build the App"
                    git credentialsId: 'worker', url: 'git@github.com:KunalTi/student-ui.git'
                }
                }
            stage('Maven-Build'){
                steps{
                    sh 'sudo apt-get install maven curl unzip -y'
                    sh 'mvn clean'
                    sh "mvn package"
                    echo 'Do you want to proceed?'
                }
                }
            stage('Push-Artifact'){
                steps{
                    sh "cd /home/ubuntu/"
                    sh 'curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"'
                    sh 'unzip -o awscliv2.zip'
                    sh 'sudo ./aws/install --update'
                    sh 'sudo mv /var/lib/jenkins/workspace/tomcat/target/studentapp-2.2-SNAPSHOT.war /home/ubuntu/student-${BUILD_ID}.war'
                    sh 'aws s3 cp /home/ubuntu/student-${BUILD_ID}.war s3://monjeyb'
                    sh 'sudo chown jenkins:jenkins /var/cache/jenkins'
                     echo "Start the deploy .."
                } 
                }
            stage('Dev-Deployment'){
                steps{
                    echo "App is Prod Ready"
                    withCredentials([sshUserPrivateKey(credentialsId: 'root', keyFileVariable: 'id_rsa')]){
                    sh 'ssh -i $id_rsa -o -T StrictHostKeyChecking=no ubuntu@3.110.119.172'
                    sh '''
                    curl -O https://dlcdn.apache.org/tomcat/tomcat-8/v8.5.78/bin/apache-tomcat-8.5.78.tar.gz
                    sudo tar -xvf apache-tomcat-8.5.78.tar.gz -C /opt/
                    sudo curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
                    sudo unzip -o awscliv2.zip
                    sudo ./aws/install --update
                    sudo aws s3 cp s3://monjeyb/studentapp-2.2-SNAPSHOT.war /opt/apache-tomcat-8.5.78/webapps/studentapp.war
                    '''
                    sh 'sudo /opt/apache-tomcat-8.5.78/bin/catalina.sh start' 
                    }                
                }
            }
        }
}