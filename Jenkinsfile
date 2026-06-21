pipeline {

    agent any

    tools {
        jdk 'JDK21'
        maven 'Maven3'
    }

    stages {

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Archive') {
            steps {
                archiveArtifacts(
                    artifacts: 'target/*.jar',
                    fingerprint: true
                )
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t demo-api:latest .'
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                docker rm -f demo-api || true

                docker run -d \
                  --name demo-api \
                  -p 8081:8082 \
                  demo-api:latest
                '''
            }
        }

    }
}