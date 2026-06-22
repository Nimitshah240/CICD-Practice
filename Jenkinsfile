pipeline {

    agent any

    tools {
        jdk 'JDK21'
        maven 'Maven3'
    }
    parameters {
            choice (
                name: 'ENVIRONMENT',
                choices: ['DEV', 'QA', 'PROD'],
                description: 'Select Deployment Environment'
            )
    }
    environment {
        APP_NAME = 'demo-api'
        HOST_PORT = credentials('port')
        CONTAINER_PORT = credentials('port')
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
                sh """
                docker rm -f demo-api || true

                docker run -d \
                  --name demo-api \
                  -p ${HOST_PORT}:${CONTAINER_PORT} \
                  demo-api:latest
                """
            }
        }
    }

    post {

        success {

            echo 'Build Successful'

            sh """
                cp target/*.jar \
                /artifacts/demo-api-${BUILD_NUMBER}.jar
            """
        }

        failure {
            echo 'Build Failed'
        }

        always {
            echo 'Pipeline Finished'
        }
    }
}