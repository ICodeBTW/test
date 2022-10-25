#!groovy
library('lib@main') _
 
pipeline {
    agent any
 
    stages {
        stage('Hello') {
            steps {
             script {
               def x = readMavenPom file:"pomx.xml" 
               echo "$x"
             }
            }
        }
    }
}
