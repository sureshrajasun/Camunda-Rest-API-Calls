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

    println("IS Valid User : " + isValidUser)

    if(isValidUser){
        def slurper = new groovy.json.JsonSlurper()
        def result = slurper.parseText(response.contentAsString)
        println("First Name :" + result.firstName)
        println("User Id :" + result.id)
        execution.setVariable("userId", result.id)
    }

    //return variables
    execution.setVariable("isValidUser", isValidUser)

} catch (RESTClientException e) {
    println("Exception Occured :: " + e)
    throw new org.camunda.bpm.engine.delegate.BpmnError("404Error");
}