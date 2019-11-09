
try {
   node {      
      stage('init'){  
         sh "docker ps -a"
         //sh "docker rm az-demo:1.0"
         //sh "docker ps -a"
         sh "printenv"
         cleanWs()
      }
      stage('checkout'){

         checkout scm

         sh "mkdir source"
          dir("source"){
              git(
                  branch : "master",
                  credentialsId: "andresrdu-github",
                  url: "https://github.com/opando/aks-rbac-example.git"
              )
          }
          sh "pwd && ls -lart"
          
      }
      stage('Build Container') {      
         //dockerBuild = ""
         withCredentials([string(credentialsId:"andresrdu-sp",variable:'azcre')]){
            //token : CLIENT_ID|CLIENT_SECRET|TENANT_ID|SUSCRIPTION_ID
            spcredential = azcre.split('\\|')
            clientId = spcredential[0]
            clientSe = spcredential[1]
            tennatId = spcredential[2]
            subscrId = spcredential[3]
            sh "cp lab2/docker/Dockerfile ."
            sh "ls -lat"
            sh "find . -maxdepth 2 -mindepth 2"
            sh "docker build -t az-demo:1.0 --build-arg CLIENT_ID=${clientId} --build-arg CLIENT_SECRET=${clientSe} --build-arg TENANT_ID=${tennatId} --build-arg SUSCRIPTION_ID=${subscrId} ."
            sh "docker images"
            //sh "${dockerBuild}"
         }
      }
      stage("Terraform init"){
         
         //sh "docker run -v <folder host>:<folder del container> --name <nombre de imagen> <imagen de la copia> terraform init"
         sh "docker run --rm -v /var/run/docker.sock:/var/run/docker.sock -v ${WORKSPACE}/source:/iac --name az-runarq az-demo:1.0 ls -lat"
         sh "docker run --rm -v /var/run/docker.sock:/var/run/docker.sock -v ${WORKSPACE}/source:/iac --name az-runarq az-demo:1.0 terraform init -no-color"
         // sh "docker run --rm --name az-runarq az-demo:1.0 ls -lat"
         // sh "docker run --rm --name az-runarq az-demo:1.0 terraform init -no-color"
      }
      stage("Terraform plan"){
         
         //sh "docker run --rm -v ${WORKSPACE}/source:/iac --name az-runarq az-demo:1.0 terraform plan -no-color"
         
      }
      stage("Terraform apply"){
         
         //sh "docker run --rm -v ${WORKSPACE}/source:/iac --name az-runarq az-demo:1.0 terraform apply -auto-approve -no-color"
         
      }
   }
} 
catch(e) {
   node{   
      echo "-- exception --"
      throw e
   }
}
finally {
  node{ 
    echo "-- remove img --"

  }  
}

