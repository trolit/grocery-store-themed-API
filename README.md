<p align="center">
<img height="100" src="https://img.shields.io/badge/--white?logo=Spring&style=for-the-badge"/>
<img height="100" src="https://img.shields.io/badge/--white?logo=java&logoColor=orange&style=for-the-badge"/>
<img height="100" src="https://img.shields.io/badge/--white?logo=apache%20maven&logoColor=orange&style=for-the-badge"/>
</p>
<h2 align="center"> Grocery Store API (v1.1.1)</h2>
<p align="justify">RESTFul API Maven project made in Java 14 and 2.3.4.RELEASE Spring Boot after <a href="https://github.com/trolit/car-themed-API" draggable="false">car themed API (.NET Core 3.1)</a>. This API can serve for e.g. as groundwork to practise front-end implementation and develop functionalities like products filtering, shopping cart(rendering items in it, adding buttons to change amount), making purchase, decorating products that are on promotion etc. There is no payment system included. API by one of the PATCH requests serves products that user bought and reduces stock size on each of them(provided that purchase was successful meaning if ordered products stocks were enough to make requested order).</p>

<hr/>

<h3>Notes</h3>

<details>
    <summary>- Running project</summary> 
    
Best way to run a project is getting CE(Community Edition) IDE like JetBrains IntelliJ IDEA or any other you like and obtaining Java JDK to compile & run the project. 
</details>

<details>
    <summary>- Accessing Swagger</summary> 
    
To access Swagger enter: ```http://localhost:8080/swagger-ui/``` in your browser <em>(pay attention to the last slash)</em>
</details>

<details>
    <summary>- H2 Database usage</summary> 
    
<p align="justify">If API is running, access database console by entering: <code>http://localhost:8080/h2-console</code> in your browser <em>(credentials are stored in application.properties file)</em>. Project is using persistent mode. Database sample with some precreated objects is stored in the repository and will be read as soon as you launch project. If you want to have empty database on each run, you can do <strong>one</strong> of the steps described below: <br/><br/>
&nbsp; 1) Remove line <code>spring.jpa.hibernate.ddl-auto=update</code> from <code>application.properties</code>. <br/> &nbsp; <em>In result on every app launch H2 will perform DROP TABLE operation.</em> <br/><br/>
&nbsp; 2) Overwrite line <code>spring.datasource.url=jdbc:h2:file:./data/sample</code> with <code>jdbc:h2:mem:testdb</code> <br/> &nbsp; <em>This way app will use nonpersistent, "in-memory" database.</em>
</p>
</details>

<details>
    <summary>- Filtering products</summary> 
    
Except percentagePriceDiff, every property from ProductQueryDto showed below can be requested to be filtered in <code>key{operation}value</code> scheme. Available operations are: :(equal), >(greater or equal), <(less or equal).

```java
public class ProductQueryDto {

    private Integer id;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private String category;
    private Integer categoryId;
    private String measurement;
    private BigDecimal previousPrice;   
    private Integer percentagePriceDiff;   // returns % difference between price and previousPrice
    private String priceStatus;
    
    // getters and setters skipped for brievity
}
```

To return products that category is "Alcoholic Drinks" you could use <code>categoryId</code> or human readable <code>category</code>. When using name with whitespace, whitespace must be replaced with %20 encoding. See example below.

```http://localhost:8080/api/v1/products?search=categoryId:1```

```http://localhost:8080/api/v1/products?search=category:Alcoholic%20Drinks```

Filters can be chained to shorten results like below:

```http://localhost:8080/api/v1/products?search=categoryId:1,price>15```

If you would like to get products that are on discount you would send request:

```http://localhost:8080/api/v1/products?search=priceStatus:discount```
</details>
   
<details>
    <summary>- Explained Stock update PATCH requests</summary> 

<p align="justify">
    Request <code>/products/{id}/stock</code> updates stock for <strong>single product overwriting currently stored</strong> data while the other one -> <code>/api/v1/products/order</code> - allows to update database information when someone orders chosen product(s). To use the second method, you need to send in body an array with specific order: <code>{ProductId}, {Amount}</code>. Note that before API applies changes to sent products stocks, it will verify if there is enough of each product to make order. See JSON example below for <code>/api/v1/products/order</code> action on how order should look like:
</p>

```
{
  "order": [
    "97", "68", "102", "44"
  ]
}
// 97 -> product Id
// 68 -> amount of product identified with 97 that user ordered
```
</details>

<hr/>

<h3>Available actions</h3>

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
| (5) | <em>Update <strong>Product's</strong> stock</em> | PATCH | ```/products/{id}/stock``` | 204 | Requested Product's stock is updated. |
| (6) | <em>Update <strong>Products</strong> stocks</em> | PATCH | ```/products/order``` | 204 | Given products stocks are reduced by requested amount. |
</details>
<details>
    <summary>Product Query Controller</summary> 

| Sr. No. | Operation <img width=350/> | HTTP Method | Path <br/> ```/api/v1``` <img width=200/> | Status Code | Description <img width=200/> |
| :---: | :---: | :---: | :---: | :---: | :---: |
| (1) | <em>Return Products (can be filtered)</em> | GET | ```/products``` | 200 | Fetches all products. |
| (2) | <em>Return Product within given id</em> | GET | ```/products/{id}``` | 200 | Returns single product. |
</details>

<hr/>

<h3>Changelog</h3>

<strong>[ 04.10.2020, 1.1.1 ]</strong>

- Extended product model with previousPrice property
- Extended product query dto with previousPrice, priceStatus(discount/rise/unchanged) and percentagePriceDiff
- Updated Product HTTP PUT, GET methods to supply 3 variables mentioned above
- Implemented price difference counting(in percents) in <code>ProductCommonMethods.java</code>
- Implemented priceStatus filtering
- Optimized getAllProducts()
    - Moved categoryId, category, priceStatus predicates into ProductPredicate(categoryId, category are called within Product model)
    - Extraced setting percentagePriceDiff and adding params to ProductPredicateBuilder into separate methods
- Added 400(Bad request) response for getAllProducts() in case of trying to filter via percentagePriceDiff

<hr/>

<details>
    <summary>- Used tools and technologies</summary> 
   
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
</details>

<details>
    <summary>- References</summary> 
   
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
- <a href="https://regex101.com/">Online regular expressions tester</a>
</details>

<br/>

Template generated using <a href="https://github.com/trolit/EzGitDoc">EzGitDoc</a>
