# springboot-blog

## Instruction

A simple blog application built with spring boot, spring jpa, h2, and security. 

## Initialize spring boot project

First of all, we can generate spring boot project in [here](https://start.spring.io/).

### specification
- Maven project
- Java 18
- Spring Boot 2.7.1
- Dependencies: Spring Web, Spring Boot DevTools, Spring Data JPA, H2 Database, Validation, Thymleaf, Lombok

## application properties

```
# setup server port to be 3000
server.port=3000

# setup local h2 database config
spring.datasource.url=jdbc:h2:file:./data/blogapp
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=admin
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

spring.jpa.hibernate.ddl-auto=create-drop
```

- server.port: change the localhost port number
- spring.datasource: define datasource of this spring project url, driver class name, username, password
- spring.jpa.database-platform: Name of the target database to operate on, auto-detected by default. Can be alternatively set using the "Database" enum.
- spring.h2.console: Whether to enable the console and path at which the console is available.
- spring.jpa.hibernate.ddl-auto: DDL mode. This is actually a shortcut for the "hibernate.hbm2ddl.auto" property. Defaults to "create-drop" when using an embedded database and no schema manager was detected. Otherwise, defaults to "none".
  - none: Disable DDL handling.
  - validate: Validate the schema, make no changes to the database.
  - update: Update the schema if necessary.
  - create: Create the schema and destroy previous data.
  - create-drop: Create and then destroy the schema at the end of the session.

Now, we can check changed server port with ```loacalhost:3000```, and h2 console in ```localhost:3000/h2-console```.

<img width="450" alt="image" src="https://user-images.githubusercontent.com/39740066/179660342-257d3ed8-f114-487e-9090-8ace1fe5f10b.png">
