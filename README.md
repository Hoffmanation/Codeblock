# Codeblock Application
This Java Spring-Boot application is a programming languages blog which the user can register and post he's own code block 
The end user have he's own account in which he can upload,delete,update and display (by section) he's code blogs 

## Git Branches
- master - For Deploying a Spring-Boot application as a Tomcat war/ Embedded Tomcat jar  files
- codeblock-wildfly - For deploying a Spring-Boot application as a Wildfly/Jboss (or any JEE application server) war file

# Module Major Dependencies
- Spring-Boot V1.5.10.RELEASE
- Spring-Security V1.5.10.RELEASE
- Spring-Date V2.0.0.RELEASE
- Java-javax.mail V1.5.0-b01
- Apache-commons-lang3 V3.1
- Apache-commons-io V2.5

# Server Specifications
- Java Maven project
- Spring-Boot
- Persistence - Spring-JPA-repository
- Security - Spring-Security


# Client Specifications
- AngularJs + angular-cookies - V1.0.8
- jQuery,Bootstrap, Bootstrap-ui
- HTML,CSS


# Environment
 - Ubuntu/Windows
 
# Requirements
- JVM
- DB connection (Configured as development env to local PostgreSql)



## Running up Environment
- run maven install and retrieve the Codeblock.jar file
- java - jar Codeblock.jar

## Accessing Codeblock application
-http://localhost:8090/

# .properties files explanation

#### .application.properties
    

	server.port= what will be the port of the application
	

	
#### .messages.properties
    
	#Messages for front end user
	not.empty=* All fields are required.
	size.username= *Username have to be between 6 and 32 characters.
	duplicate.username= *Someone already has that username.
	size.password= *Password should be at least 8 characters.
	dont.match.passwordConfirm= *These passwords don't match.
	email.not.valid= *This email is not valid
	
	#Can be changed as you wish	
	

# Contact
- For any questions you can send a mail to orenhoffman1777@gmail.com