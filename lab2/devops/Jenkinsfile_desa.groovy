
try {
   node {      
      stage('init'){       
           
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
      }
      stage('Build Container') {      
         
          
     
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

