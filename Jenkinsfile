pipeline {
    agent any

    environment {
        IMAGE = "demo-app"
        TAG   = "${env.BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn -B test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t $IMAGE:$TAG .'
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                  docker rm -f demo-app || true
                  docker run -d --name demo-app -p 8081:8080 $IMAGE:$TAG
                '''
            }
        }

        stage('Health Check') {
            steps {
                sh '''
                  for i in $(seq 1 10); do
                    curl -sf http://localhost:8081/health && exit 0
                    sleep 5
                  done
                  echo "Health check failed"
                  exit 1
                '''
            }
        }
    }

    post {
        success { echo "Deployed build ${env.BUILD_NUMBER}" }
        failure { echo "Build ${env.BUILD_NUMBER} failed. Check the stage above." }
        always  { sh 'docker image prune -f' }
    }
}
