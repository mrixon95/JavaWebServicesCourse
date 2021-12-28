# Notes on course

Web Service

- software system designed for machine to machine interaction over a network

Key points:

- designed for machine to machine (or application to application) interaction
- should be interoperable - not platform dependent
- show allow communication over a network

Input to a webservice is called a request. The output from a webservice is called a response.

Webservices should be platform independent eg. our web service should be able to collect from a java application, .NET application or PHP application.

Two popular formats for request and response (XML or JSON).

XML - 

JSON - javascript object notation. It is how javascript represents its objects.

Every service offers a service definition.



## Service definition specifies

- request and response format eg. JSON or XML
- request structure
- response structure
- endpoint



How do we make web service platforms independent?

By creating platform independent requests and responses using XML and JSON

An application know the format of a request and response using the service definition.



### Key Terminology

**Request** is the input to our web service

**Response** is the output from our web service

Message exchange format is either XML or JSON

Service consumer sends requests to a service

Service providers sends back responses



The endpoint defines what URL is the service exposed at.

The transport is over the web. It is HTTP.

MQ is communication over a queue.

The service requester places message on a queue.

The service provider listens on the queue and takes requests.

When it takes a request, it does process of it and creates a response and puts it back on the queue and the service requester would get the response from the queue.



## Types of web services

SOAP and REST

Simple Object Access Protocol

SOAP uses XML is a medium for data exchange between applications of different languages

SOAP is based on XML and uses the HTTP protocol

SOAP envelope contains a header and a body

 A simple SOAP Message has the following elements

- The Envelope element
- The header element and
- The body element
- The Fault element (Optional)



The SOAP Envelope is used to encapsulate all of the necessary details of the SOAP messages, which are exchanged between the web service and the client application

This process of encapsulating the data into a SOAP message was known as **Marshalling.**

The practice of unwrapping a request sent by the client is known as **Demarshalling.**

The header could contain definitions of complex data types or authentication information

SOAP defines a format for your request and the response. It does not restrict the transport.



WSDL defines the endpoint, basically where your service is exposed at. It also defines the operations, request structure, response structure





API is a mediator between users/clients and the web service they want to interact with. The organisation can make their resources available whilst maintaining security, control and authentication.

REST - Representation State Transfer

REST are architectural constraints:

- Client Server constraint means that the client and server are separate from eachother. Changes on my mobile app should not impact the server's database or data structure and vice versa. Each application should grow independently.
- Stateless - every HTTP request happens in isolation. No server or session details are saved. Server does not need to remember prior requests. Server relies solely on the data that is provided in that call itself. Any state info is stored on the client, not on the server.
- Cache - should be designed to encourage the storage of cacheable data. When data is cachable for a temporary period of time, the client should be told about it through the API response

You expose resources to the outside world using URI. A resource can have different representations eg. XML, JSON, HTTP

Think in terms of resources and using HTTP verbs

Rest is an architectural style whereas SOAP is an XML format

REST doesn't have a strict data exchange format

Swagger is a popular RESTful web service







### **Bean Configuration**

Need to tell spring

```
// what are the different beans that Spring has to manage? A: @component
// what are the dependencies for the bean? A: @autowired
// where to search for beans? A: package com.example.SpringIn5StepsProd;
```



Java Springboot framework manages beans and wires in the dependencies. 

To make best use of spring, tell it the beans and dependencies.

Bean is an instance of an object with dependencies. We want to wire in dependencies

To tell spring boot what the bean is, let's decorate the BinarySearchImpl and class with the *@Component* annotation:

```
@Component
public class BinarySearchImpl {
    // this body is the same as before
}
```

Use *@Component* annotation on QuickSortAlgorithm and BubbleSortAlgorithm

and use @Autowired annotation on the dependencies.

This means that sortAlgorithm is a dependency for BinarySearchImpl

```
@Component
public class BinarySearchImpl {

    @Autowired
    private SortAlgorithm sortAlgorithm;
```



For searching for beans, tell springboot where to do the component scan.

Use the annotation @SpringBootApplication

This will cause Spring to scan the package where it is located and sub packages for finding beans.

Automatically spring will scan the package com.example.SpringIn5StepsProd and subpackages for beans.

```
package com.example.SpringIn5StepsProd;

@SpringBootApplication
public class SpringIn5StepsProdApplication {
```



SortAlgorithm is the dependency of BinarySearchImpl.



Don't need to do ```BinarySearchImpl binarySearchImp = new BinarySearchImpl(new BubbleSortAlgorithm());```

Spring will do that for us.

Now we can get beans from Application Context



```
// Spring manages the dependencies and injects the dependencies when mneeded
// Managed the lifecycle of beans
```

```java
ApplicationContext applicationContext = SpringApplication.run(SpringIn5StepsProdApplication.class, args);
		BinarySearchImpl binarySearch = applicationContext.getBean(BinarySearchImpl.class);
		int result =
				binarySearch.binarySearch(new int[] { 12, 4, 6 }, 3);
		System.out.println(result);
```



To use debugging mode, write in application.properties

```
logging.level.org.springframework = debug
```



### Identifying component classes

Spring will identify component classes

Identified candidate component class: file [C:\Users\mrixo\MasterJavaWebServicesCourse\SpringIn5StepsProd\SpringIn5StepsProd\target\classes\com\example\SpringIn5StepsProd\BinarySearchImpl.class]

Identified candidate component class: file [C:\Users\mrixo\MasterJavaWebServicesCourse\SpringIn5StepsProd\SpringIn5StepsProd\target\classes\com\example\SpringIn5StepsProd\BubbleSortAlgorithm.class]

Then it will start creating beans and trying to identify dependencies

```
Creating instance of bean 'binarySearchImpl'

Creating instance of bean 'bubbleSortAlgorithm'

Finished creating instance of bean 'bubbleSortAlgorithm'

Autowiring by type from bean name 'binarySearchImpl' via constructor to bean named 'bubbleSortAlgorithm'

Finished creating instance of bean 'binarySearchImpl'
```



If we put @Component on QuickSort algorithm as well as BubbleSort Algorithm, then Spring tells us that BinarySearchImp requires a single Bean but actually 2 were found. 

To get around this, we can specify the primary component using @Primary



If no other component is specified

```
Parameter 0 of constructor in com.example.SpringIn5StepsProd.BinarySearchImpl required a bean of type 'com.example.SpringIn5StepsProd.SortAlgorithm' that could not be found.
```



