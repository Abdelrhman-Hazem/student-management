# Student Management System

This is a simple student management system that consist of students 
and courses, where courses can be created, students can create an account, 
login to account, enroll to a course and withdraw from a course and other features.

The project utilized many technologies of interest to developers showing 
a simple use-case for each, including security, caching, pdf report creation.

ChatGPT was heavily leveraged through this project, showcasing how a 
developer can benefit from AI tools to boost productivity, while also 
highlighting how a developer can utilize experience and knowledge to be 
selective using AI code, and quickly recognizing and applying all necessary 
modifications and adjustments. For reference find the ChatGPT chat below 
[here](https://chatgpt.com/share/66fc4bce-1924-800d-90cd-1eeb944bd692).

## Business Description
Building a student management system where users can log in, view courses and
manage courses registration.

The system provides the following features:
- Student account can be created, student can log in using account. 
- Courses can be created specifying a schedule for the course. 
- Course list can be retrieved, also a single course details including schedule and student list can be viewed.
- Student can enroll to a course, withdraw from a course, and view enrolled courses.

## Technologies Used
- Java 
- Spring (Spring Boot, Spring Security, Spring Data, ...)
- Maven
- Weblogic Server
- Oracle DB
- Redis
- RESTful APIs
- JWT Token

## API
For full API documentation, including examples on success calls and all 
expected exceptions, refer to the link [here](https://documenter.getpostman.com/view/26735712/2sAXxJiFVC#c98473ab-84bf-41f8-9b37-f1226c50cbeb).

## Project Configuration
Before running the project replace all required properties on the 
`application.properties` file found in the below path:
```
src/main/resources/
```

## Run Project
The Project is prepared to be run in one of two methods.

1. Run using Spring Web embedded tomcat server using maven.\
Navigate to project directory and run:
    ```
    mvn spring-boot:run
    ```
2. Deploy WAR file on Weblogic server.\
Navigate to project directory and run:
    ```
    mvn clean package
    ``` 
    Then Deploy WAR file on Weblogic server. For detailed 
   instructions on how to deploy on Weblogic refer to the
   article [here](https://o7planning.org/11901/deploy-spring-boot-application-on-oracle-weblogic-server).
