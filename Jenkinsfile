```groovy
pipeline {
    agent any

    environment {
        WAR_FILE = 'target/MVS.war'
    }

    stages {

        stage('Checkout') {
            steps {
                echo 'Checking out source code...'

                git branch: 'main',
                    url: 'https://github.com/soundarya21112004/manual-testing.git'
            }
        }

        stage('Build') {
            steps {
                echo 'Building MVS application...'

                sh '''
                    echo "Java version:"
                    java -version

                    echo "Maven version:"
                    mvn -version

                    echo "Starting Maven build..."
                    mvn clean package
                '''
            }
        }

        stage('Verify WAR') {
            steps {
                sh '''
                    echo "Checking generated WAR..."

                    if [ ! -f "${WAR_FILE}" ]; then
                        echo "ERROR: ${WAR_FILE} was not created"
                        exit 1
                    fi

                    ls -lh "${WAR_FILE}"
                '''
            }
        }

        stage('Archive WAR') {
            steps {
                archiveArtifacts artifacts: 'target/MVS.war',
                                 fingerprint: true
            }
        }
    }

    post {
        success {
            echo '========================================='
            echo 'MVS BUILD SUCCESSFUL'
            echo 'WAR: target/MVS.war'
            echo '========================================='
        }

        failure {
            echo '========================================='
            echo 'MVS BUILD FAILED'
            echo 'Check the Jenkins console output'
            echo '========================================='
        }
    }
}
```
