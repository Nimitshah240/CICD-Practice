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

    }

}