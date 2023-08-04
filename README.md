


# Camunda Call External API to validate the User


![img.png](img/img.png)!

>Groovy Script to fetch the user from external API and throw exception incase of error

```groovy
import wslite.rest.RESTClient
import wslite.rest.RESTClientException

def firstName = execution.getVariable("firstName")
def lastName = execution.getVariable("lastName")
def mobile = execution.getVariable("mobile")
def id = execution.getVariable("id")

def isValidUser = false



RESTClient client = new RESTClient("http://localhost:8080")
def path = "/customer/"+id
def response



try {
    println(path)
    response = client.get(path: path)
    println(response.contentAsString)


    if(response.contentAsString.contains(firstName)){
        isValidUser = true;
       
    }

     if(isValidUser && response.contentAsString.contains(lastName)){
        isValidUser = true;
       
    }

     if(isValidUser && response.contentAsString.contains(mobile)){
        isValidUser = true;
       
    }
   

    println(isValidUser)
    
    //return variables
    execution.setVariable("isValidUser", isValidUser)
   

} catch (RESTClientException e) {
    println("Exception Occured :: " + e)
    throw new org.camunda.bpm.engine.delegate.BpmnError("404Error");
}
```