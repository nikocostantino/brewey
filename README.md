# SFG Beer Works - RESTful Brewery Service

This project is to support Fincons Academy learning about Java, Spring and Restful APIs. 

You can access the API documentation [here](http://localhost:8081/swagger-ui/index.html)

Before running the application remember to:
* Be sure you have installed JAVA 17
* You create a database called fincons-kbe-rest-brewery-jwt

Before using the application you need to create users
* Signup all needed users with roles "user" or "admin"
* Signin with a registered user in order to get a token or 
* Make calls from swagger api pages clicking on the lock icon to authenticate and inject a token in following calls

FEATURES
* Spring security with JWT token
* Spring and JPA for database
* Jasypt for password encryption
* Java 17 Record integration as DTO's
* Jackson mapping
* Swagger rest OpenAPI docmentation
* HAL browser for applying HATEOAS at http://localhost:8081/webjars/hal-explorer/1.1.0/index.html

RESOURCES
* https://dev.to/brunooliveira/practical-java-16-using-jackson-to-serialize-records-4og4
