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
                sh 'mvn clean verify --settings /var/jenkins_home/.m2/settings.xml'
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
                        --settings /var/jenkins_home/.m2/settings.xml
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
                        cat > settings-temp.xml <<EOF
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 https://maven.apache.org/xsd/settings-1.0.0.xsd">
  <servers>
    <server>
      <id>maven-snapshot</id>
      <username>${NEXUS_USER}</username>
      <password>${NEXUS_PASS}</password>
    </server>
  </servers>
</settings>
EOF
                        mvn deploy --settings settings-temp.xml
                    '''
                }
            }
        }
    }
}