## Govtech Developer Test
Package: com.govtech.govtrial
Version: 0.1.0
Java Version: Java SE 11

#### Architecture & Stack
- Developed using Java Spring Boot with JPA (Hibernate)
- Utilising in-memory H2 database
- Application layer follows an MVC with a repository design pattern for managing the data and business logic
- Web service follows a REST architecture with HATEOAS implementation
- Database seeded using Java Faker (http://dius.github.io/java-faker/)

#### How To Build
1. Configure server.port in "src/main/resources/application.properties". Default port is 8080. We will use port 8080 in the following examples.
2. On start, a employee data CSV will be automatically generated before being parsed and stored into the database.
3. Either build using STS or with the following command from project root:
`. scripts/build.sh`
4. Run the jar with the following command from project root:
`. scripts/buildrun.sh`
5. Stop the jar with the following command from project root:
`. scripts/stop.sh`

#### Notable URLs
**1. https://localhost:8080/employees**
- Returns the set of all employees with pagination and size limit

**2. http://localhost:8080/employees?page=1&size=10**
- Returns the next page of ten employees

**3. http://localhost:8080/employees?minimumSalary=0&maximumSalary=4000**
- Returns the employees with salaries between $0 and $4000 inclusive

**4. http://localhost:8080/employees?minimumSalary=0&maximumSalary=4000&page=1&size=1**
- Returns the second employee with salaries between $0 and $4000 inclusive

#### To Dos
Due to a lack of time, the following critical components have not been completed
- [ ] Testing Implementation
- [ ] Linting/Refactoring of code
- [ ] Integration with CI/CD tools
- [ ] CORS implementation
- [ ] OWASP checklist/guidelines
- [ ] Authentication (OAuth and/or JWT)

#### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.0.RELEASE/gradle-plugin/reference/html/)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.2.0.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring HATEOAS](https://docs.spring.io/spring-boot/docs/2.2.0.RELEASE/reference/htmlsingle/#boot-features-spring-hateoas)