<h2>Grocery Store API</h2>

<p align="justify">RESTFul API made in Java 14 Maven project using 2.3.4 Spring Boot for educational purposes after <a href="https://github.com/trolit/car-themed-API" draggable="false">car themed API (.NET Core 3.1)</a>. This API can serve as well as groundwork to practice front-end implementation to implement something like shopping cart, order, promotion etc or extending it's functionality.</p>

<h4>Available actions</h4>

<details>
    <summary>Category Command Controller</summary> 

| Sr. No. | Operation <img width=350/> | HTTP Method | Path <br/> ```/api/v1``` <img width=200/> | Status Code | Description <img width=200/> |
| :---: | :---: | :---: | :---: | :---: | :---: |
| (1) | <em>Create Category</em> | POST | ```/categories``` | 201 | New Category is created. |
| (2) | <em>Update Category</em> | PUT | ```/categories/{id}``` | 200 | Category resource is updated. |
| (3) | <em>Delete Category</em> | DELETE | ```/categories/{id}``` | 204 | Category is deleted. |
</details>

<details>
    <summary>Category Query Controller</summary> 

| Sr. No. | Operation <img width=350/> | HTTP Method | Path <br/> ```/api/v1``` <img width=200/> | Status Code | Description <img width=200/> |
| :---: | :---: | :---: | :---: | :---: | :---: |
| (1) | <em>Return Categories</em> | GET | ```/categories``` | 200 | Fetches all categories. |
| (2) | <em>Return Category</em> | GET | ```/categories/{id}``` | 200 | One category is fetched. |
</details>

<details>
    <summary>Product Command Controller</summary> 

| Sr. No. | Operation <img width=350/> | HTTP Method | Path <br/> ```/api/v1``` <img width=200/> | Status Code | Description <img width=200/> |
| :---: | :---: | :---: | :---: | :---: | :---: |
| (1) | <em>Create Product</em> | POST | ```/products``` | 201 | New Product is created. |
| (2) | <em>Update Product</em> | PUT | ```/products/{id}``` | 200 | Product is updated. |
| (3) | <em>Delete Product</em> | DELETE | ```/products/{id}``` | 204 | Product is deleted. |
| (4) | <em>Change Product's price by percentage</em> | PATCH | ```/products/{id}/price``` | 204 | Product price is updated according to given percentage. |
| (5) | <em>Update Product's stock</em> | PATCH | ```/products/{id}/stock``` | 204 | Requested Product's stock is updated. |
| (6) | <em>Update Products stocks</em> | PATCH | ```/products/order``` | 204 | Given products stocks are reduced by requested amount. |
</details>

<details>
    <summary>Product Query Controller</summary> 

| Sr. No. | Operation <img width=350/> | HTTP Method | Path <br/> ```/api/v1``` <img width=200/> | Status Code | Description <img width=200/> |
| :---: | :---: | :---: | :---: | :---: | :---: |
| (1) | <em>Return Products (can be filtered)</em> | GET | ```/products``` | 200 | Fetches all products. |
| (2) | <em>Return Product within given id</em> | GET | ```/products/{id}``` | 200 | Returns single product. |
</details>

<h4>Notes</h4>

<h5>- Swagger</h5>

To access it enter: ```http://localhost:8080/swagger-ui/``` in your browser <em>(pay attention to last slash)</em>

<h5>- H2 Database</h5>

Access database console by entering: ```http://localhost:8080/h2-console``` in your browser <em>(credentials are stored in application.properties)</em>. Project is using persistent Db mode. Database sample with some precreated objects is stored in the repository and will be read as soon as you launch project. If you want to have empty Db on each start you can <br/>
- Remove line ```spring.jpa.hibernate.ddl-auto=update``` from ```application.properties```. In result on every app launch H2 will perform DROP TABLE operation. <br/><br/>
- Overwrite line ```spring.datasource.url=jdbc:h2:file:./data/sample``` with ```jdbc:h2:mem:testdb```. This way app will use nonpersistent, "in-memory" database. 

<h5>- Products filtering</h5>

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
}
```

To return products that category is "Alcoholic Drink" we would use categoryId(1) because of existing whitespace bug. URL would look something like this:

```http://localhost:8080/api/v1/products?search=categoryId:1```

If we wanted to additionaly shorten results with different property like price:

```http://localhost:8080/api/v1/products?search=categoryId:1,price>15```

<h4>Tools and technologies</h4>

- Spring Boot 2.3.4.RELEASE
- Open JDK 14
- Hibernate (spring-boot-starter-data-jpa)
- JPA
- Maven
- H2 Database
- Springfox Swagger 3.0.0
- ModelMapper 2.3.8
- Querydsl
- IDE - IntelliJ IDEA CE 2020.1.1

<h4>Useful references</h4>

- <a href="https://start.spring.io/"/>Spring Initializr</a>
- <a href="https://howtodoinjava.com/spring-rest/spring-rest-crud-jpa-example/"/>Spring REST CRUD JPA Example</a>
- <a href="https://www.javaguides.net/2019/08/spring-boot-crud-rest-api-spring-data-jpa-h2-database-example.html"/>Spring Boot CRUD REST API JPA H2 Example</a>
- <a href="https://stackabuse.com/integrating-h2-database-with-spring-boot/"/>Integrating H2 with Spring Boot</a>
- <a href="https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate/"/>Best way to map 1-n relation in JPA-Hibernate</a>
- <a href="https://www.baeldung.com/swagger-2-documentation-for-spring-rest-api"/>Setting up Swagger</a>
- <a href="http://modelmapper.org/"/>ModelMapper documentation</a>
- <a href="https://www.baeldung.com/rest-api-search-language-spring-data-querydsl"/>Query language Spring Querydsl</a>
- <a href="https://www.vojtechruzicka.com/documenting-spring-boot-rest-api-swagger-springfox/"/>Spring Boot documenting Swagger</a>
- <a href="https://stackoverflow.com/questions/58998687/swagger-read-documentation-from-properties-file"/>Swagger read docs from *.md files</a>

<br/>

Template generated using <a href="https://github.com/trolit/EzGitDoc">EzGitDoc</a>
