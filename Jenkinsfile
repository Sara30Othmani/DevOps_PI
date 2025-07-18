pipeline {
    agent any

    tools {
        maven 'Maven 3.8.8'
    }

    environment {
        SONARQUBE_TOKEN = credentials('sonar-token')
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/Sara30Othmani/DevOps_PI.git', branch: 'master'
            }
        }

        stage('Build, Test & Jacoco Report') {
            steps {
                sh 'mvn clean verify'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh '''
                        mvn sonar:sonar \
                        -Dsonar.projectKey=Foyer \
                        -Dsonar.login=$SONARQUBE_TOKEN \
                        -Dsonar.host.url=http://sonarqube:9000 \
                        -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml \
                        --settings /home/sara_devops/.m2/settings.xml
                    '''
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 15, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Deploy to Nexus') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'nexus-credentials', usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASS')]) {
                    sh '''
                        mvn deploy \
                        -Dnexus.username=$NEXUS_USER \
                        -Dnexus.password=$NEXUS_PASS \
                        --settings /home/sara_devops/.m2/settings.xml
                    '''
                }
            }
        }
    }
}
