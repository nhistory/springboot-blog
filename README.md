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

## Make HomeController and home.html

For make html file with variables on ```controller```, we need to use ```thymeleaf```. Thymeleaf is a Java-based library used to create a web application. It provides a good support for serving a XHTML/HTML5 in web applications. At first, you can make ```HomeController``` to control home.html with url mapping.

Annotations
- Controller: @Controller annotation indicates that the annotated class is a controller. It is a specialization of @Component and is autodetected through classpath scanning. It is typically used in combination with annotated handler methods based on the @RequestMapping annotation.
- GetMapping: The @GetMapping annotation is a specialized version of @RequestMapping annotation that acts as a shortcut for @RequestMapping(method = RequestMethod. GET) . The @GetMapping annotated methods in the @Controller annotated classes handle the HTTP GET requests matched with given URI expression.

In the ```home.html```, we can add thymeleaf attribute to use ```th``` method and variables.

```html
<html xmlns:th="http://www.thymeleaf.org">
```

If you run debug with ```SeedData```, it can be seen like below.

<img width="450" alt="image" src="https://user-images.githubusercontent.com/39740066/180105039-ff9e60c9-1cee-4566-8516-220be6711db9.png">

## Create PostController, and post.html

As ```HomeController```, you can add ```PostController``` with ```getPost``` public class. If requested post id exists, the post would be shown. If not, ```404.html``` will be occured.

Annotations
- PathVariable: @PathVariable is a Spring annotation which indicates that a method parameter should be bound to a URI template variable. It has the following optional elements: name - name of the path variable to bind to. required - tells whether the path variable is required. value - alias for name.

```java
    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable Long id, Model model) {
        // find the post by id
        Optional<Post> optionalPost = postService.getById(id);
        // if the post exists, then show it into the model
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            model.addAttribute("post", post);
            return "post";
        } else {
            return "404";
        }
    }
```

## Make Account feature

In this stage, we will make Account festure by using Jparepository. 

### 1. Account model

First of all, make an ```Account``` class on the ```models``` package. Such as ```Post``` class, @Entity, @Getter, @Setter, @Id, @GeneratedValue annotations would be needed. Relationship betweem account and posts is OneToMany, so @OneToMany annotation is added with ```mappedBy``` account.

### 2. Add ManyToOne relationship on the post class

Connection between posts and account would be ManyToOne relationship. To make this connectivity, we need to add code like below.

```java
// Connection with account
@NotNull
@ManyToOne
@JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
private Account account;
```

### 3. Account Repository

In order to save account repository, you can create ```AccountRepository``` interface inside of ```repositories``` package.

### 4. Account Service

```AccountService``` class can do a role as a service for saving account into the account repositories. After that, we can test this by changing ```SeedData``` class.

<img width="450" alt="image" src="https://user-images.githubusercontent.com/39740066/180368338-60a5cd23-19b5-43bd-bf92-45eebde24da8.png">

<img width="450" alt="image" src="https://user-images.githubusercontent.com/39740066/180368509-61799e64-0b4c-498e-b93b-94d1ff98a178.png">

You can see the ```Account``` column and each of posts connected with ```account_id```.

## Add new post feature

For now, we should make feature for adding new post. To doing this, ```PostController```,```AccountService```,```AccountRepository``` need to be modified.

### 1. Add new post mapping

First of all, you can add mapping on the ```PostController``` to connect new post url.

```java
@GetMapping("/posts/new")
    public String createNewPost(Model model) {
        Optional<Account> optionalAccount = accountService.findByEmail("user.user@domain.com");
        if (optionalAccount.isPresent()) {
            Post post = new Post();
            post.setAccount(optionalAccount.get());
            model.addAttribute("post", post);
            return "post_new";
        } else {
            return "404";
        }
    }
    
@PostMapping("/posts/new")
public String saveNewPost(@ModelAttribute Post post) {
    postService.save(post);
    return "redirect:/posts/" + post.getId();
}
```

New post should be matched with account information, so ```accountService``` obeject needed.

``` java
    @Autowired
    private AccountService accountService;
```

### 2. Create findByEmail method

In order to find account, ```findByEmail``` method has to be made.

```java
    public Optional<Account> findByEmail(String email) {
        return accountRepository.findOneByEmail(email);
    }
```

As you can see, ```findByEmail``` method need information from ```accountRepository```. So we need to add ```findOneByEmail``` method on the ```accountRepository```.

### 3. Make findOneByEmail method

```java
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findOneByEmail(String email);
}
```

Finally, we need ```post_new.html``` page.

### 4. Create post_new web page.

```html
<div class="container">
    <a th:href="@{'/'}">Home</a>
    <form action="#" th:action="@{'/posts/new'}" th:object="${post}" method="POST">
        <input type="hidden" th:field="*{account}" />
        <input type="hidden" th:field="*{createdAt}" />
        <h2>Write new Post</h2>
        <div>
            <label for="new-post-title">Title</label>
            <input id="new-post-title" type="text" th:field="*{title}" placeholder="Title" />
        </div>
        <div>
            <label for="new-post-body">Body</label>
            <textarea id="new-post-body" type="text" th:field="*{body}" placeholder="Enter the text"></textarea>
        </div>
        <button type="submit">Publish Post</button>
    </form>
</div>
```

Eventually we can check ```post_new``` page like below.

<img width="450" alt="image" src="https://user-images.githubusercontent.com/39740066/180657557-c257bf32-29fe-48f5-a39f-0a4b74e245f9.png">

## References
- https://www.youtube.com/watch?v=7iWlCl35p9o
- https://projectlombok.org/features/GetterSetter
