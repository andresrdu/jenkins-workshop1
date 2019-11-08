
node{
    stage("init"){

    }
    stage("checkout"){

        withCredentials([string(credentialsId: "token-demo" ,
         variable : "secreto")]){
            echo "${secreto}"
            arraytoken = secreto.split("\\|")
            part1 = arraytoken[0]
            part2 = arraytoken[1]
            echo "${part1} ${part2}"
            
        }

        
    }
}

/*
password2019|opando@gmail.com|textosecreto

*/