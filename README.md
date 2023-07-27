# springboot-example

# module dependency
## spring-boot version
- `<parent>`: `spring-boot-starter-parent` and `spring-boot version`
- `<dependency>`: `spring-boot-starter-web` without version
- `<dependency>`: `snakeyaml` for vulnerability
```
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.1.0</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>

<dependencies>
    <dependency>
        <!-- Spring Boot WebMvc -->
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <!-- Spring Boot 에서 사용하는 snakeyaml 1.x 의 취약점 때문에 2.0 으로 버전 업그레이드 -->
        <groupId>org.yaml</groupId>
        <artifactId>snakeyaml</artifactId>
        <version>2.0</version>
    </dependency>
</dependencies>
```

## dependencies
- `spring-boot-devtools`: automatic restarting and only using runtime scope
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```

- `spring-boot-configuration-processor`: auto complete for application.properties
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
</dependency>
```

- `lombok`: getter and setter
```
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
```

- `spring-boot-starter-data-jpa`: include <spring-data-commons> which supports pageable
- `hibernate-validator`: form validator in server side
- `mysql-connector-java`: connect to mysql
- `hsqldb`: connect to hsqldb
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-validator</artifactId>
    <version>8.0.1.Final</version>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
</dependency>
<dependency>
    <groupId>org.hsqldb</groupId>
    <artifactId>hsqldb</artifactId>
    <version>2.7.2</version>
</dependency>
```

- `jakarta.servlet-api`: servlet and servlet filter from spring boot 3.0 (no more javax)
- `spring-boot-starter-actuator`: an information of application (e.q. Spring Bean lists)
- `jackson-dataformat-xml`: find resolver in WebMvcAutoConfiguration
```
<dependency>
    <!-- Servlet / ServletFilter 을 사용하기 위해 -->
    <groupId>jakarta.servlet</groupId>
    <artifactId>jakarta.servlet-api</artifactId>
    <version>6.0.0</version>
</dependency>
<dependency>
    <!-- Application 정보 모니터링 -->
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
<dependency>
    <!-- WebMvcAutoConfiguration 에서 Resolver 연결에 사용 -->
    <groupId>com.fasterxml.jackson.dataformat</groupId>
    <artifactId>jackson-dataformat-xml</artifactId>
    <version>2.15.2</version>
</dependency>
```


## Reading properties
```shell
java -jar ./application.jar --spring.config.location=file:./application.properties
```
```shell
java -jar ./application.jar -Dspring.profiles.active=dev
```
```shell
export spring_profiles_active
java -jar ./application.jar
```

## Execute application
```shell
nohup java -Xms1024m -Xms1024m -XX:+UseG1GC -jar ./application.jar > server.out 2>&1 &
```
