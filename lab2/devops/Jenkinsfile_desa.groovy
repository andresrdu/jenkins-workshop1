
try {
   node {      
      stage('init'){       
         sh "printenv"
      }
      stage('checkout'){              
         sh "mkdir source"
          dir("source"){
              git(
                  branch : "master",
                  credentialsId: "opando-github",
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
            sh "docker build -t az-demo:1.0 --build-arg CLIENT_ID=${clientId} --build-arg CLIENT_SECRET=${clientSe} --build-arg TENANT_ID=${tennatId} --build-arg SUSCRIPTION_ID=${subscrId} ."

            //sh "${dockerBuild}"
         }
      }
      stage("Terraform init"){
         
         //sh "docker run -v <folder host>:<folder del container> --name <nombre de imagen> <imagen de la copia> terraform init"
         sh "docker run -v ${WORKSPACE}/source/aks-rbac-example:/iac --name az-runarq az-demo:1.0 terraform init"
         
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

