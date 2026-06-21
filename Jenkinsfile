pipeline {

    agent any

    tools {

        jdk 'JDK21'
        maven 'Maven3'

    }

    stages {

        stage('Verify') {

            steps {

                sh 'java -version'
                sh 'mvn -version'

            }

        }

    }

}