#!groovy
library('lib@main') _
 
pipeline {
    agent any
 
    stages {
        stage('Hello') {
            steps {
                echo 'Hello World'
                sh "ls -la" 
                called(this)
            }
        }
    }
}
