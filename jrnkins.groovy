pipeline {
         agent {
             label ("ec2-worker")
         }
         stages {
                 stage('Build') {
                 steps {
                     echo ' Starting to build the App.'
                     sh "ls"
                 sh 'sleep 10'
                 }
                 }
                 stage('Test') {
                 steps {
                    echo 'Do you want to proceed?'
                 }
                 }
                 stage('Deploy') {
                           steps {
                                echo "Start the deploy .."
                           } 
                           }
                 stage('Prod') {
                     steps {
                                echo "App is Prod Ready"
                              }
                 
              }
}
}
