pipeline {
  agent {
    docker {
      reuseNode true
      image 'kyberorg/jobbari:1.1'
    }
    
  }
  stages {
    stage('Init') {
      steps {
        sh '''##### Gathering info ####



set +x
echo "Starting building ${PROJECT}"
echo ""
MV=`mvn --version`
DV=`docker --version`

# Internet available ?
wget -q --tries=10 --timeout=20 --spider http://google.com
if [ "$?" -eq "0" ]; then
        NET_STATUS="Host Online"
else
        NET_STATUS="Host Offline"
fi

echo "[Build info]"
echo "Git branch: ${GIT_BRANCH}"
echo "Git commit: ${GIT_COMMIT}"
echo "Jenkins Job #${BUILD_NUMBER}" 
echo "Jenkins Job URL: ${BUILD_URL}"
echo "Jenkins Tag: ${BUILD_TAG}"
echo ""
echo "[Worker info]"
echo "Hostname: ${HOSTNAME}"
echo "Net status: ${NET_STATUS}"
echo ""
echo "Docker version: ${DV}"
echo ""
echo "Maven version: ${MV}"'''
      }
    }
    stage('Test') {
      steps {
        sh 'mvn test -B'
      }
    }
    stage('Results') {
      steps {
        junit(testResults: 'target/surefire-reports/**/*.xml', allowEmptyResults: true)
      }
    }
    stage('Build') {
      steps {
        sh 'mvn clean package -DskipTests=true -Dmaven.javadoc.skip=true -B -V'
        archive 'target/*.jar'
      }
    }
    stage('Create Docker Tag') {
      steps {
        sh 'echo $HOSTNAME'
      }
    }
    stage('Create Docker image') {
      steps {
        sh 'echo $HOSTNAME'
        sh 'docker --version'
      }
    }
    stage('Push Docker image') {
      steps {
        sh 'echo $HOSTNAME'
      }
    }
  }
  environment {
    PROJECT = 'Yals'
  }
}