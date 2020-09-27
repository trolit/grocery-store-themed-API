<h3>Grocery Store API</h3>

<p align="justify">RESTFul API made in Java 14 Maven project using 2.3.4 Spring Boot for educational purposes after <a href="https://github.com/trolit/car-themed-API" draggable="false">car themed API (.NET Core 3.1)</a>. This API can serve as well as groundwork to practice front-end implementation for things like shopping cart, making order, promotion etc or extending it's functionality.</p>

<br/>

<h3>Tools and technologies</h3>

- Spring Boot 2.3.4.RELEASE
- Open JDK 14
- Hibernate (spring-boot-starter-data-jpa)
- JPA
- Maven
- H2 Database
- Springfox Swagger 3.0.0
- ModelMapper 2.3.8
- Querydsl
- IDE - IntelliJ IDEA

<br/>

<h3>Available actions</h3>

| Sr. No. | Operation <img width=350/> | HTTP Method | Path <br/> <img width=200/> ```/api/v1``` <img width=200/> | Status Code | Description <img width=200/> |
| :---: | :---: | :---: | :---: | :---: | :---: |
| (1) | <em>Create Category</em> | POST | ```/categories``` | 201 | New Category is created. |
| (2) | <em>Update Category</em> | PUT | ```/categories/{id}``` | 200 | Category resource is updated. |
| (3) | <em>Delete Category</em> | DELETE | ```/categories/{id}``` | 204 | Category is deleted. |
| (4) | <em>Return Categories</em> | GET | ```/categories``` | 200 | Fetches all categories. |
| (5) | <em>Return Category</em> | GET | ```/categories/{id}``` | 200 | One category is fetched. |
| (6) | <em>Create Product</em> | POST | ```/products``` | 201 | New Product is created. |
| (7) | <em>Update Product</em> | PUT | ```/products/{id}``` | 200 | Product is updated. |
| (8) | <em>Delete Product</em> | DELETE | ```/products/{id}``` | 204 | Product is deleted. |
| (9) | <em>Update Products stocks</em> | PATCH | ```/products/order``` | 204 | Given products stocks are reduced by requested amount. |
| (10) | <em>Update Product's stock</em> | PATCH | ```/products/{id}/stock``` | 204 | Requested Product's stock is updated. |
| (11) | <em>Change Product's price by percentage</em> | PATCH | ```/products/{id}/price``` | 204 | Product price is updated according to given percentage. |
| (12) | <em>Return Products (can be filtered)</em> | GET | ```/products``` | 200 | Fetches all products. |
| (13) | <em>Return Product within given id</em> | GET | ```/products/{id}``` | 200 | Returns single product. |

<br/>

<h3>Notes</h3>

<h4>Swagger</h4>

To access it enter: ```http://localhost:8080/swagger-ui/``` in your browser <em>(pay attention to last slash)</em>

<h4>H2 Database</h4>

Access database console by entering: ```http://localhost:8080/h2-console``` in your browser <em>(credentials are stored in application.properties)</em>. Project is using persistent Db mode. Database sample is stored in the repository and will be read as soon as you launch project with some precreated objects. If you want to have empty Db on each start you can <br/>
- Remove line ```spring.jpa.hibernate.ddl-auto=update``` from ```application.properties```. In result on every app launch H2 will perform DROP TABLE operation. <br/><br/>
- Overwrite line ```spring.datasource.url=jdbc:h2:file:./data/sample``` with ```jdbc:h2:mem:testdb```. This way app will use nonpersistent, "in-memory" database. 

<h4>Products filtering</h4>

GET method responsible for fetching all products is extended with optional search parameter with which you can pass property name, operation and value to filter results. Every property from ProductQueryDto can be requested to be filtered. 

```java
public class ProductQueryDto {

    private Integer id;

    private String name;

    private BigDecimal price;

    private Integer stock;

    private String category;

    private Integer categoryId;

    private String measurement;
    
    // getters and setters skipped for brievity
```

To return products that category is "Alcoholic Drink" we would use categoryId(1) because of existing whitespace. URL would look something like this:

```http://localhost:8080/api/v1/products?search=categoryId:1```

If we wanted to additionaly shorten results with different property like price:

```http://localhost:8080/api/v1/products?search=categoryId:1,price>15```

<br/>

Template generated using <a href="https://github.com/trolit/EzGitDoc">EzGitDoc</a>
