pipeline {
    agent none

    options {
        timeout(time: 15, unit: 'MINUTES')
        disableConcurrentBuilds()
        failFast true
    }

    environment {
        MOCK_API_URL = 'https://api.fake-pharma-service.mock/v1/data'
        DB_AUTH_KEY  = credentials('mock-db-secret-string')
    }

    stages {
        stage('1. Fetch Fake API Data') {
            agent {
                docker { image 'alpine:latest' }
            }
            steps {
                echo "Connecting to remote API endpoint: ${env.MOCK_API_URL}..."

                // Simulating an API response payload creation using a basic shell block
                sh '''
                    echo '{"batch_id": "B2026-X9", "status": "ACTIVE", "medicines_count": 142}' > api_payload.json
                    echo "API data successfully retrieved and saved locally."
                '''

                // CONCEPT: Stash the file so the separate DB agent node can access it
                stash name: 'api-payload-data', includes: 'api_payload.json'
            }
        }

        stage('2. Seed Fake Database') {
            // Switching sequentially to a completely separate host machine node environment
            agent { label 'mock-database-worker-node' }
            steps {
                echo "Initializing connection to database instance..."

                // CONCEPT: Unstash the data created by the previous container agent
                unstash 'api-payload-data'

                // Using the masked credentials safely in our shell logic
                sh '''
                    echo "Authenticating using masked system token: ${DB_AUTH_KEY_PSW}"

                    # Creating a fake database flat-file and appending data
                    echo "--- DATABASE STORAGE ---" > database.db
                    cat api_payload.json >> database.db
                    echo "DB_TRANSACTION=SUCCESS" >> database.db

                    echo "Mock database schema update complete."
                '''

                // Stash the updated database state file for our parallel verification checks
                stash name: 'current-db-state', includes: 'database.db'
            }
        }

        stage('3. Parallel Quality Engines') {
            // CONCEPT: Running independent validation checks at the exact same time
            parallel {

                stage('Track A: Data Integrity Validation') {
                    agent { label 'linux-compute-worker' }
                    steps {
                        echo "Starting integrity analysis on the seed data..."
                        unstash 'current-db-state'

                        // Run a fake validation test using grep to ensure data exists
                        sh '''
                            grep -q "DB_TRANSACTION=SUCCESS" database.db
                            echo "INTEGRITY CHECK PASS: Expected database transaction markers found."
                        '''
                    }
                }

                stage('Track B: Security Compliance Scan') {
                    agent { docker { image 'alpine:latest' } }
                    steps {
                        echo "Running compliance checking protocols..."
                        unstash 'current-db-state'

                        // Imperative Groovy script escape hatch block for custom logic
                        script {
                            echo "Executing script engine parsing routines..."
                            def dbContent = readFile('database.db')
                            if (dbContent.contains("B2026-X9")) {
                                echo "SECURITY CHECK PASS: Payload batch signature matches security compliance rules."
                            } else {
                                currentBuild.result = 'UNSTABLE'
                                echo "WARNING: Target batch signature missing."
                            }
                        }
                    }
                }

            } // End of parallel execution block
        } // End of parent stage
    }

    // 4. Lifecycle Clean-up and Alerts
    post {
        always {
            echo "Pipeline workflow ending. Executing universal workspace cleanup..."
            cleanWs() // Clear out all files so the worker node storage stays healthy
        }
        success {
            echo "PROD ALERT: Pipeline completed beautifully. Fake database seeded and validated successfully."
        }
        failure {
            echo "ERR ALERT: Pipeline crashed. Check logs to see which phase threw a non-zero exit status code."
        }
    }
}