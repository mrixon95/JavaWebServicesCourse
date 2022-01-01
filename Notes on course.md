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



## Instead of using constructor injection, use setter injection

```
@Component
public class BinarySearchImpl {

    @Autowired
    private SortAlgorithm sortAlgorithm;

    public int binarySearch(int[] numbers, int numberToSearchFor) {

        int[] sortedNumbers = sortAlgorithm.sort(numbers);
        System.out.println(sortAlgorithm);
        return 3;
    }

    public BinarySearchImpl(SortAlgorithm sortAlgorithm) {
        super();
        this.sortAlgorithm = sortAlgorithm;
    }
}

@Component
public class BinarySearchImpl {

    @Autowired
    private SortAlgorithm sortAlgorithm;

    public int binarySearch(int[] numbers, int numberToSearchFor) {

        int[] sortedNumbers = sortAlgorithm.sort(numbers);
        System.out.println(sortAlgorithm);
        return 3;
    }

    public void setSortAlgorithm(SortAlgorithm sortAlgorithm) {
        this.sortAlgorithm = sortAlgorithm;
    }
}
```



## Or no setter at all

```
@Component
public class BinarySearchImpl {

    @Autowired
    private SortAlgorithm sortAlgorithm;

    public int binarySearch(int[] numbers, int numberToSearchFor) {

        int[] sortedNumbers = sortAlgorithm.sort(numbers);
        System.out.println(sortAlgorithm);
        return 3;
    }
}
```



Spring is very modular. It is not one big framework. its not like you have to use the entire framework or nothing at all.



One of the important data access models is Spring JDBC which makes JDBC much more easier.

Test is a cross cutting concern as it goes across multiple layers.



Within a few years, Spring Boot has become the most popular framework to develop microservices.

Makes it very easy to develop applications quickly using:

- start up projects
- actuator 
- autoconfiguration



Spring maintain its popularity because it maintains testable code

Very easy to do tests eg. mockito

No plumbing code. All exceptions are unchecked.

Flexible architecture - very modular projects and modules for specific purposes

Able to stay with the trend eg. Spring cloud and Spring boot



Springboot is most popular framework for microservices



## Introduction to Spring Boot in 10 steps

Lots of small services

Number 1 framework for microservices

Quickly build production ready application

Provides non-functional features

```
Goals
Enable building production ready applications quickly
Provide common non-functional features 
- embedded servers
- metrics
- health checks
- externalized configuration

What Spring Boot is NOT!
ZERO code generation
Neither an application server nor a web server

Features
Quick Starter Projects with Auto Configuration
 - Web
 - JPA
Embedded Servers - Tomcat, Jetty or Undertow
Production-ready features
 - metrics and health checks 
 - externalized configuration
 
 
http://localhost:8080/books => Few hardcoded books
```

[getting-started-in-5-steps/springboot-in-10-steps at master · in28minutes/getting-started-in-5-steps · GitHub](https://github.com/in28minutes/getting-started-in-5-steps/tree/master/springboot-in-10-steps)



```java
public class Book {
    long id;
    String name;
    String author;

    public Book(long id, String name, String author) {
        super();
        this.id = id;
        this.name = name;
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }
    @Override
    public String toString() {
        return String.format("Book [id=%s, name=%s, author=%s]", id, name, author);
    }
}

```



```java
@RestController
public class BooksController {
    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return Arrays.asList(
                new Book(1l, "Mastering Spring 5.2", "Ranga Karanam"));
    }
}
```





@SpringBootApplication indicates the Spring context file and is used for the location to start the component scan for beans.

The @RestController means that it will be registered as a bean and managed by the Spring Framework.

```
ApplicationContext applicationContext = SpringApplication.run(SpringIn5StepsProdApplication.class, args);
```

runs a spring context and returns an application context



```

```



## Spring Framework

Spring framework takes control of all the beans and their dependencies

Most important feature of Spring Framework is Dependency Injection. At the core of all Spring Modules is Dependency Injection or IOC Inversion of Control.

Define your beans, dependencies and how to find them

https://www.springboottutorial.com/spring-boot-vs-spring-mvc-vs-spring

- Using `@Component`, we tell Spring Framework - Hey there, this is a bean that you need to manage.
- Using `@Autowired`, we tell Spring Framework - Hey find the correct match for this specific type and autowire it in.



The SpringBoot starter web is the preferred starter for Springboot to develop web applications.

Actuators bring in monitoring of application



Expose all end points

```
#logging.level.org.springframework = DEBUG
management.endpoints.web.exposure.include=*
```

Use springboot developer tools to reload the project whenever there is a change.

Will only load application beans again.

```
<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
		</dependency>
```





## SOAP web services

Before developing your web service, you define the format of the request and the response.

We will define XSD - XML Service Definitions

Our SOAP webservice provider should accept a SOAP XML request and provide an output in terms of a SOAP XML response.

Need to define structure of request and response using XML schema definition.

 XML binding involves converting XML to object and vice-versa

JAXB is used for XML binding

JAXB takes the XSD and generates Java objects.

Endpoints process requests and have all the business logic related to the request.

Will use Chrome plugin called Wizdler 



## Defining XML Structure for request and response

Need to define the name space so that the GetCourseDetailsRequest tag is unique

```java
<Envelope xmlns="http://schemas.xmlsoap.org/soap/envelope/">
	<Header>
		<wsse:Security
			xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd"
			mustUnderstand="1">
			<wsse:UsernameToken>
				<wsse:Username>user</wsse:Username>
				<wsse:Password>password</wsse:Password>
			</wsse:UsernameToken>
		</wsse:Security>
	</Header>
	<Body>
		<GetCourseDetailsRequest xmlns="http://in28minutes.com/courses">
			<id>1</id>
		</GetCourseDetailsRequest>
	</Body>
</Envelope>
```

This helps tell our client how we want the request and how they can expect their response back.



https://github.com/in28minutes/spring-web-services/blob/master/soap-web-services/backup02-define-xsd-for-first-request-and-response.md



When an element can contain other elements, it is called a complex type



https://github.com/in28minutes/spring-web-services/blob/master/soap-web-services/backup02-define-xsd-for-first-request-and-response.md



Use JAXB to map Java objects to XML which is defined in the XSD. 

It will take the XSD and map it to Java objects



https://github.com/in28minutes/spring-web-services/blob/master/soap-web-services/backup04-define-jaxb-plugin-and-a-endpoint.md



JAXB takes care of converting java objects into a XML that is sent to the client.



## Endpoints

Endpoints receive a request and send back a response



Process a request with the namespace = "http://in28minutes.com/courses" and the name "GetCourseDetailsRequest".

The method must take the XML and map it to the Java class.

```
@Endpoint
public class CourseDetailsEndpoint {

	// method
	// input - GetCourseDetailsRequest
	// output - GetCourseDetailsResponse

	// http://in28minutes.com/courses
	// GetCourseDetailsRequest
	@PayloadRoot(namespace = "http://in28minutes.com/courses", localPart = "GetCourseDetailsRequest")
	@ResponsePayload
	public GetCourseDetailsResponse processCourseDetailsRequest(@RequestPayload GetCourseDetailsRequest request) {
		GetCourseDetailsResponse response = new GetCourseDetailsResponse();
		
		CourseDetails courseDetails = new CourseDetails();
		courseDetails.setId(request.getId());
		courseDetails.setName("Microservices Course");
		courseDetails.setDescription("That would be a wonderful course!");
		
		response.setCourseDetails(courseDetails);
		
		return response;
	}

}
```



@ResponsePayload is written because it needs to be converted back into XML

```
@Configuration
```

imports the Spring configuration

Any request that comes to the MVC will be first handled by the Dispatcher Servlet.

Message dispatcher servlet handles all the SOAP requests and identifies the end points

```
"/ws/*"
```

is the URL which we we will expose all of our endpoints at

```
@Bean
public ServletRegistrationBean messageDispatcherServlet(ApplicationContext context) {
    MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
    messageDispatcherServlet.setApplicationContext(context);
    messageDispatcherServlet.setTransformWsdlLocations(true);
    return new ServletRegistrationBean(messageDispatcherServlet, "/ws/*");
}

// /ws/courses.wsdl
// course-details.xsd
@Bean(name = "courses")
public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema coursesSchema) {
    DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
    definition.setPortTypeName("CoursePort");
    definition.setTargetNamespace("http://in28minutes.com/courses");
    definition.setLocationUri("/ws");
    definition.setSchema(coursesSchema);
    return definition;
}

@Bean
public XsdSchema coursesSchema() {
    return new SimpleXsdSchema(new ClassPathResource("course-details.xsd"));
}
```



```
ServletRegistrationBean helps to map message dispatcher serverlet to a URI
```

message dispatcher servelet will receive all the requests



Spring web services actually creates the WSDL for us.

We used an @Bean for course schema so it will be autowired in for us.



http://localhost:8080/ws/courses.wsdl



Access the course details through the coursedetails service



Creating a new service is easy. You just need to add a few things in the XSD and a new endpoint



Type http://localhost:8080/ws/courses.wsdl to get the wsdl definition of our services.

Client can use the wsdl to know what to sent to your web service



In the WSDL we specify the different XSD structures.

Next we define the messages, they are the request and the response



The only thing that can be used as a request or response have message in it

```
<wsdl:message name="GetAllCourseDetailsRequest">
<wsdl:part element="tns:GetAllCourseDetailsRequest" name="GetAllCourseDetailsRequest"> </wsdl:part>
</wsdl:message>
```

You map messages to operations using port type. Port type defines operations and what the input and output will be.

If you want to use a particular operation of the webservice, then this is the input and this is the output.

The service call happens over the transport of HTTP. That's how we expose our bindings

Document signifies its a complete XML request and response. Its most frequently used SOAP binding

Alternative is RPC



At a high level, types defines all the operations that are available

Bindings map operations to how you are exposing them

Service maps to end points



Springboot provides a way to build back end applications in java



spring boot start test is used for unit tests and integration tests



## Spring Boot Tutorial for Beginners (Java Framework)

https://www.youtube.com/watch?v=vtPkZShrvXQ



Dependency instance

Tell Springboot to Instantiate beans using @Repository or @Component. Tell Springboot about services using @Service and dependency injection using @Autowired

Use @Qualifier to differentiate between different dependency injections

GO TO CONFIGURATION TO CHECK JAVA RUNE TIME

You start up a jar

you run java -jar .\de.-SNAPSHOT.jar to run the jar.



```
//XwsSecurityInterceptor
```

intercepts all requests coming into our web service and checks their security.

Need to add this interceptor to the list of interceptors

callback says what the security interceptor do when it intercepts a request. It should validate username and password



Whenever the XwsSecurityIntercepter intercepts a request, it calls the SimplePasswordValidationCallbackHandler and checks that the user and password are correct.