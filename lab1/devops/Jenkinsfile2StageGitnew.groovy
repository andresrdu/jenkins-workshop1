
node{

    stage("init"){
        cleanWs()
    }
    stage("checkout"){
        sh "pwd"
        folder1 = "source"
        sh "mkdir ${folder1}"

        dir ("${folder1}"){
            git(
                credentialsId: "opando-github",
                branch: "master" ,
                url: "https://github.com/opando/aks-rbac-example.git"
            )
        }
        

        sh "ls -lta"

        sh "cd ${folder1} && ls -lta"

    }
}

// git add .                    
// git commit -m "cambios"    
// git push -u origin master
