
node{
    withEnv(["ENV_CLIENTID=12345677-123456-123456"]){
        stage("init"){
            echo "${ENV_CLIENTID}"
            sh "printenv"
            cleanWs()
        }
    }

    stage("checkout"){
        println '===== TODO ======'
        
        sh "mkdir source"
        dir("source"){
            git(
                branch : "master",
                credentialsId: "opando-github",
                url: "https://github.com/opando/aks-rbac-example.git"
            )
        }
        
        sh "pwd"
        
        sh "ls -lta && cd source && ls -lta"


    }
}

// git add .                    
// git commit -m "cambios"    
// git push -u origin master
