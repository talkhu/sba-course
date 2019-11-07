pipeline {
    agent any
    environment {
        GIT_URL = "https://github.com/talkhu/sba-course.git"
		GIT_CRED = "b6514644-84fc-410a-9ee0-7b3002b7c931"
		dockerImage = ''
      
    }
    stages {
    
    	stage('CheckOut Code'){
         	steps{
            	checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: GIT_CRED, url: GIT_URL]]])
            	}
              }
        stage('Maven Build'){
        	steps{
        	    bat 'mvn clean install -DskipTests'
        	}

        }
        
        stage('Building image') {
	      steps{
		        script {
		        bat 'docker build -t sbacourse:build1 .'
		        bat 'docker run -d --name course -p 9996:9996 sbacourse:build1'
	        }
	      }
	    }
		
   }
  

}
