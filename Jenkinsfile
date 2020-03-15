pipeline {

    agent any

    stages {
        stage ('Checkout') {
            steps {
                checkout scm
                sh 'ls -lat'
            }
        }
        stage("Compile") {
            steps {
                sh "./gradlew compileJava"
            }
        }
        stage ('Analysis') {
            steps {
                sh './gradlew check'
                junit "**/build/test-results/test/*.xml"
                jacoco(
                    execPattern: 'build/jacoco/jacoco.exec'
                )
            }
        }
        stage ('Build') {
            steps {
                sh './gradlew build'
            }
        }
        stage ('Docker') {
            environment {
                DOCKERHUB_CREDENTIALS = credentials('dockerhub')
            }
            steps {
                sh "./gradlew jib"
            }
        }
    }

}
