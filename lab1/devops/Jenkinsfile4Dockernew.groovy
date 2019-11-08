
node{
    stage("init"){

    }
    stage("checkout"){
        withCredentials([string(credentialsId:"opando-token",
                                variable:'token1')]){
            echo "${token1}"

            tokentmp = token1.split('\\|')
            tok1 = tokentmp[0]
            tok2 = tokentmp[1]

            echo " ${tok1}  ----  ${tok2}"
        }
    }

// terraform init
// terraform apply
// terraform -version
// docker run -it <name image>:tag terraform -version
// docker pull hashicorp/terraform:0.12.12

//sudo usermod -aG docker jenkins
    stage("deploy"){
        
        docker.image("hashicorp/terraform:0.12.12")
                .withRun("-version"){ c->
                sh "docker logs ${c.id}"

                
                
        }

        sh "docker run -i -t hashicorp/terraform:0.12.12 -version"
        sh "docker rm \$(docker ps -a -q)"
    }

}

def removeImg(image){
  try{
   sh 'docker rmi -f \$(docker images | grep \''+image+'\' | awk \'{print $3}\')'
  }catch(e){
    echo "-- No se pudo borrar la imagen ${image} --"
  }
}

/*
password2019|opando@gmail.com|textosecreto

*/