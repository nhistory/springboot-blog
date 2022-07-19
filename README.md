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

## Add objects

### Post

Inside of models, we create ```Post``` public class with id, title, body, and local date time information.

Annotations
- Entity: The @Entity annotation specifies that the class is an entity and is mapped to a database table. 
- Getter, Setter: You can annotate any field with @Getter and/or @Setter, to let lombok generate the default getter/setter automatically. You can also put a @Getter and/or @Setter annotation on a class. In that case, it's as if you annotate all the non-static fields in that class with the annotation.
- Id, GeneratedValue: The @Id annotation specifies the primary key of an entity and the @GeneratedValue provides for the specification of generation strategies for the values of primary keys.
- Column: @Column annotation is used for Adding the column the name in the table of a particular MySQL database.

### PostRepository

After that, you need to make ```Repository``` for handeling ```Post``` object with ```JpaRepository```. 

Annotations
- Repository: @Repository is a Spring annotation that indicates that the decorated class is a repository. A repository is a mechanism for encapsulating storage, retrieval, and search behavior which emulates a collection of objects.

### PostService

And we can controll ```Post``` data with ```PostService``` class. Inside of that, ```getById```, ```getAll```, and ```save``` method would be added.

Annotations
- Service: It is used to mark the class as a service provider. So overall @Service annotation is used with classes that provide some business functionalities. Spring context will autodetect these classes when annotation-based configuration and classpath scanning is used.
- Autowired: The @Autowired annotation provides more fine-grained control over where and how autowiring should be accomplished. The @Autowired annotation can be used to autowire bean on the setter method just like @Required annotation, constructor, a property or methods with arbitrary names and/or multiple arguments.

## Test this basic data structure

Let's make ```SeedData``` class and create private object ```postService```. So you can use ```postService.getAll()```, ```postService.save()```, and setter methods such as ```setTitle()``` and ```setBody```.

Annotations
- Component: @Component is an annotation that allows Spring to automatically detect our custom beans. In other words, without having to write any explicit code, Spring will: Scan our application for classes annotated with @Component. Instantiate them and inject any specified dependencies into them.
- Override: The @Override annotation indicates that the child class method is over-writing its base class method. The @Override annotation can be useful for two reasons. It extracts a warning from the compiler if the annotated method doesn't actually override anything. It can improve the readability of the source code.

### What is bean in spring boot?

In Spring, the objects that form the backbone of your application and that are managed by the Spring IoC container are called beans. A bean is an object that is instantiated, assembled, and otherwise managed by a Spring IoC container. Otherwise, a bean is simply one of many objects in your application.

<img width="450" alt="image" src="https://user-images.githubusercontent.com/39740066/179825434-2d03ab74-1133-4f28-8b58-d32298840069.png">

As shown in the figure above, @Service, @Controller, and @Repository all inherit @Component, and classes registered with the corresponding annotation are automatically generated by the spring container and reported as spring bins. Auto-wiring automatically maps a spring bin object to a specific reference variable and uses an annotation called @Autowired. Create a java file by implementing the CommandLineRunner interface. CommandLineRunner is used to implement code launched by the spring boot application and executed with the CommandLine factor. The run() method must be overridden.

## References
- https://www.youtube.com/watch?v=7iWlCl35p9o
- https://projectlombok.org/features/GetterSetter
