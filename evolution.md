# 搭建微服务

## （1）配置依赖

### 1.1 选择合适的Spring Boot、Spring Cloud、Spring Cloud Alibaba版本

![image-20241211211207411](C:\Users\BlueJack\AppData\Roaming\Typora\typora-user-images\image-20241211211207411.png)

### 1.2 配置项目依赖

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>com.evolution</groupId>
  <artifactId>GloriousEvolution</artifactId>
  <packaging>pom</packaging>

  <!-- 指定项目版本号 -->
  <version>1.0-SNAPSHOT</version>
  <name>GloriousEvolution Maven Webapp</name>
  <url>http://maven.apache.org</url>

  <!-- 统一配置版本信息 -->
  <properties>
    <java.version>21</java.version>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    <spring-boot.version>3.0.2</spring-boot.version>
    <spring-cloud-dependencies.version>2022.0.0</spring-cloud-dependencies.version>
    <spring-cloud-alibaba.version>2022.0.0.0</spring-cloud-alibaba.version>
    <spring-cloud-starter-netflix-eureka-server.version>4.2.0</spring-cloud-starter-netflix-eureka-server.version>
  </properties>

  <dependencyManagement>
    <dependencies>
      <!-- Spring Cloud 依赖配置 -->
      <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-dependencies</artifactId>
        <version>${spring-cloud-dependencies.version}</version>
        <scope>import</scope>
        <type>pom</type>
      </dependency>

      <!-- Spring Boot 依赖配置 -->
      <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-dependencies</artifactId>
        <version>${spring-boot.version}</version>
        <scope>import</scope>
        <type>pom</type>
      </dependency>

      <!-- Spring Cloud Alibaba 依赖配置 -->
      <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-alibaba-dependencies</artifactId>
        <version>${spring-cloud-alibaba.version}</version>
        <scope>import</scope>
        <type>pom</type>
      </dependency>
        
      <!-- Eureka 配置依赖 -->
      <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        <version>${spring-cloud-starter-netflix-eureka-server.version}</version>
      </dependency>
    </dependencies>
  </dependencyManagement>

  <dependencies>
  </dependencies>

  <!-- 模块存储 -->
  <modules>
    <module>test-service</module>
  </modules>

  <build>
    <finalName>GloriousEvolution</finalName>
  </build>
</project>
```

### 1.3 配置子项目依赖

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>com.evolution</groupId>
        <artifactId>GloriousEvolution</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>

    <artifactId>test-service</artifactId>

    <!-- 模块功能简介 -->
    <description>
        Evolution测试中心
    </description>

    <packaging>war</packaging>
    <name>test-service Maven Webapp</name>
    <url>http://maven.apache.org</url>

    <dependencies>
        <!-- SpringBoot Web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- SpringBoot Actuator -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
    </dependencies>
    <build>
        <finalName>test-service</finalName>
    </build>
</project>
```

## （2）Java JDK发展历史

最重要及广泛影响的特性有：
Java8 -> Optional、Lambda
Java11 -> Var类型、HttpClient
Java17 -> 封闭类
Java21 -> 虚拟线程、模式匹配

### 1. Java8 新特性

#### 1. Optional 

##### **1.1 Optional 创建**

- `Optional.of(value)`：非空值
- `Optional.empty()`：空值
- `Optional.ofNullable(value)`：可空值

##### **1.2 Mybatis 配合 Optional**

- 配置映射：返回 `Optional<Type>` 类型

- 示例：

  ```
  Optional<User> user = userMapper.findById(id);
  ```

##### **1.3 方法返回 Optional**

- 返回类型：

  ```
  public Optional<User> findById(int id) {  
    return Optional.ofNullable(userMapper.findById(id));
  }
  ```

##### **1.4  `map()` 与 `flatMap()`**

- map()：转换值

```
Optional<User> user = Optional.ofNullable(userService.findUser());
Optional<String> name = user.map(u -> u.getName());
```

- flatMap()：扁平化值

```
Optional<Address> address = Optional.ofNullable(user)
                                    .flatMap(u -> Optional.ofNullable(u.getAddress()));
```

##### **1.5 ` ifPresent()` 和 `orElse()`**

- ifPresent()：

```
Optional<User> user = Optional.ofNullable(userService.findUser());
user.ifPresent(u -> System.out.println(u.getName()));
```

- orElse()：

```
String name = user.orElse(new User("Default")).getName();
```

##### **1.6 ` filter()`**

- 条件过滤：

```
Optional<User> user = Optional.ofNullable(userService.findUser());
user.filter(u -> u.getAge() > 18).ifPresent(u -> System.out.println(u.getName()));
```

##### **1.7  `orElseThrow()`**

- 抛出异常：

```
User user = Optional.ofNullable(userService.findUser())
                    .orElseThrow(() -> new RuntimeException("User not found"));
```

#### 2. Lambda 表达式

Lambda 表达式是 Java 8 最具标志性的特性之一，它允许你用更简洁的方式传递行为，即将代码作为参数传递。这使得函数式编程模式得以在 Java 中实现，代码更加简洁和易于理解。

示例：

```
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
names.forEach(name -> System.out.println(name));
```

Lambda 表达式可以用于处理集合、流、事件处理等场景，它极大地提高了代码的可读性和简洁性。

### 2. Java11 新特性

#### 1. **`var` 引用类型推断的改进**

可以使用 `var` 来简化代码，特别是在不需要显式指定类型时。

```
var list = new ArrayList<String>();
list.add("Hello");
```

#### 2. **`HttpClient` API (标准化)**

新的 `HttpClient` 提供了更现代的 HTTP/2 支持，支持异步和同步请求，以及更简洁的 API。

```
HttpClient client = HttpClient.newHttpClient();
HttpRequest request = HttpRequest.newBuilder(URI.create("https://api.example.com"))
        .build();
HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
System.out.println(response.body());
```

### 3. Java17 新特性

Java 17 是一个 **长期支持版本（LTS）**，因此它引入了许多更为成熟和重要的新特性，很多特性在后续版本中也得到了强化和完善。它是目前许多企业选择的稳定版本。

#### 1. **封闭类和封闭接口（Sealed Classes, JEP 409）**

Java 17 引入了 **封闭类（Sealed Classes）**，这允许开发者更精确地控制哪些类可以继承或实现该类/接口。通过封闭类，开发者可以指定一个类的子类的集合，增强了类型安全性，并且可以更好地管理继承层次。

```
public sealed class Shape
    permits Circle, Rectangle { }

public final class Circle extends Shape { ... }

public final class Rectangle extends Shape { ... }
```

这种方式提供了更强的控制，并且使得类型推断和模式匹配变得更加精确。

### 4. Java21 新特性

Java 21 是 Oracle 发布的最新版本，作为长期支持（LTS）版本，它包含了许多新特性和改进。以下是 Java 21 主要的新特性和增强功能：

### 1. **项目 Loom：虚拟线程**

Java 21 引入了 **虚拟线程（Virtual Threads）**，这是 Java 通过 **项目 Loom** 提供的一个重要特性。虚拟线程旨在显著改善高并发应用的性能，尤其是当有大量任务需要同时执行时（比如 I/O 密集型任务）。

- **虚拟线程** 是一种轻量级的线程实现，比传统的操作系统线程消耗的资源少很多。Java 通过 **`java.lang.Thread`** 类支持虚拟线程，开发者可以像使用普通线程一样使用虚拟线程。
- 虚拟线程可以轻松创建数百万个，适合构建高并发应用程序，比如微服务、Web 应用等。

```
java复制代码// 创建虚拟线程
Thread.startVirtualThread(() -> {
    System.out.println("Hello from virtual thread!");
});
```

### 2. **增强的 Pattern Matching（模式匹配）**

Java 21 增强了 **模式匹配**，尤其是在 `switch` 表达式和 `instanceof` 操作符中。模式匹配可以让开发者以更简洁、更安全的方式进行类型检查和解构。

- **模式匹配 for `switch`**：Java 21 进一步扩展了 `switch` 表达式，使其更加灵活，支持更复杂的条件匹配。

```
// 使用增强版模式匹配
switch (shape) {
    case Circle c -> System.out.println("It's a circle!");
    case Rectangle r -> System.out.println("It's a rectangle!");
    case null -> System.out.println("Shape is null");
    default -> System.out.println("Unknown shape");
}
```

- **`instanceof` 的简化**：通过使用 `instanceof` 时，可以直接将匹配的对象进行绑定，从而省去额外的强制类型转换。

```
if (obj instanceof String s) {
    System.out.println("String length: " + s.length());
}
```

### 3. **`Record` 类型的改进**

Java 21 对 **`record` 类型**（Java 14 引入的特性）进行了改进，允许开发者更加灵活地定义和使用记录类。

- 记录类（`record`）现在支持更多的功能，例如实现接口、继承抽象类等。
- 这使得 `record` 在许多场景下更加实用，比如替代传统的 DTO（数据传输对象）和简单的不可变数据结构。

```
public record Person(String name, int age) implements Cloneable {
    // 现在可以实现接口
}
```

## （3）Mybatis 3.0以上特性

是的，MyBatis 3.0 引入了一个非常重要的特性——通过 **注解** 代替传统的 XML 映射文件来管理 SQL 查询。这意味着开发者可以完全不依赖 XML 文件，在 Java 接口中直接使用注解来定义 SQL 语句和映射关系，从而使得 MyBatis 的配置更加简洁、直观，并且不需要额外的 XML 文件。

### 1. **通过注解代替 XML 配置**

MyBatis 3.0 提供了对注解的广泛支持，你可以在接口方法上使用注解来直接定义 SQL 语句，而不再需要在 XML 文件中编写 SQL 映射。常用的注解包括 `@Select`、`@Insert`、`@Update`、`@Delete` 等。

#### 1.1 **基本的 SQL 注解**

- `@Select`：用于执行查询操作，返回结果。
- `@Insert`：用于执行插入操作。
- `@Update`：用于执行更新操作。
- `@Delete`：用于执行删除操作。

#### 例子：使用注解定义查询

假设有一个 `User` 类，包含 `id` 和 `name` 字段，你可以使用以下注解来代替传统的 XML 配置。

```
java爪哇岛复制代码public class User {
    private int id;
    private String name;

    // getters and setters
}
```

1. **查询用户：**

```
java爪哇岛复制代码import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("SELECT * FROM users WHERE id = #{id}")
    User findUserById(int id);
}
```

在这个例子中，`@Select` 注解直接定义了查询 SQL。当调用 `findUserById(int id)` 方法时，MyBatis 会自动执行相应的 SQL 查询，获取 `User` 对象。

1. **插入数据：**

```
java复制代码import org.apache.ibatis.annotations.Insert;

public interface UserMapper {

    @Insert("INSERT INTO users (name) VALUES (#{name})")
    void insertUser(User user);
}
```

使用 `@Insert` 注解可以定义插入操作。注意，MyBatis 会自动将 `User` 对象的属性值绑定到 SQL 语句中的 `#{name}`。

1. **更新数据：**

```
java爪哇岛复制代码import org.apache.ibatis.annotations.Update;

public interface UserMapper {

    @Update("UPDATE users SET name = #{name} WHERE id = #{id}")
    void updateUser(User user);
}
```

使用 `@Update` 注解来定义更新操作。

1. **删除数据：**

```
java复制代码import org.apache.ibatis.annotations.Delete;

public interface UserMapper {

    @Delete("DELETE FROM users WHERE id = #{id}")
    void deleteUser(int id);
}
```

`@Delete` 注解用于执行删除操作。

### 2. **返回复杂结果的映射**

MyBatis 3.0 中的注解还支持复杂的结果映射。例如，可以使用 `@Results` 和 `@Result` 注解来定义查询结果如何映射到 Java 对象。

#### 例子：复杂查询和结果映射

```
java爪哇岛复制代码import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;

public interface UserMapper {

    @Select("SELECT id, name FROM users WHERE id = #{id}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "name", column = "name")
    })
    User findUserById(int id);
}
```

在这个例子中，`@Results` 注解定义了查询结果如何映射到 `User` 类的属性。每个 `@Result` 注解都对应一个列和属性之间的映射关系。

### 3. **动态 SQL 注解**

在 MyBatis 中，动态 SQL 通常通过 XML 文件中的 `<if>`、`<choose>`、`<where>` 等标签来实现。但 MyBatis 3.0 也允许使用注解来动态构建 SQL。通过 `@SelectProvider`、`@InsertProvider`、`@UpdateProvider` 和 `@DeleteProvider` 注解，开发者可以在 Java 代码中使用方法来生成 SQL，从而实现动态 SQL。

#### 3.1 **@SelectProvider 注解**

`@SelectProvider` 注解允许你指定一个方法，该方法会返回一个 SQL 字符串。这种方式适用于需要动态生成 SQL 语句的场景。

```
java复制代码import org.apache.ibatis.annotations.SelectProvider;

public interface UserMapper {

    @SelectProvider(type = SqlProvider.class, method = "buildFindUserSql")
    User findUserByIdAndName(int id, String name);

    class SqlProvider {
        public String buildFindUserSql(int id, String name) {
            StringBuilder sql = new StringBuilder("SELECT * FROM users WHERE 1=1");
            if (id != 0) {
                sql.append(" AND id = #{id}");
            }
            if (name != null && !name.isEmpty()) {
                sql.append(" AND name = #{name}");
            }
            return sql.toString();
        }
    }
}
```

在这个例子中，`@SelectProvider` 注解引用了一个方法 `buildFindUserSql`，该方法根据传入的参数动态生成 SQL 语句。

#### 3.2 **@InsertProvider、@UpdateProvider、@DeleteProvider 注解**

类似地，`@InsertProvider`、`@UpdateProvider` 和 `@DeleteProvider` 注解允许开发者动态生成插入、更新和删除 SQL 语句。

### 4. **使用 `@Mapper` 注解**

为了让 MyBatis 自动扫描和创建 Mapper 接口的实现，MyBatis 3.0 引入了 `@Mapper` 注解。通过在接口上添加 `@Mapper` 注解，MyBatis 可以自动生成该接口的实现，并将其注册到 Spring 或其他 IoC 容器中。

```
java复制代码import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM users WHERE id = #{id}")
    User findUserById(int id);
}
```

### 5. **结合 Spring 使用注解**

在 Spring 环境中，MyBatis 3.0 支持注解和 Spring 的集成。如果你使用 Spring，你可以通过 `@MapperScan` 注解来指定自动扫描的包，并将 MyBatis 的 Mapper 接口自动注册为 Spring Bean。

```
java复制代码import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

### 6. **优点和缺点**

#### 优点：

- **简洁性**：可以省略 XML 文件，SQL 映射直接写在 Java 接口中，减少了配置文件的复杂性。
- **高效**：开发者不需要切换到 XML 配置文件中进行修改，可以直接在 Java 代码中进行修改，适合快速开发。
- **类型安全**：通过使用 Java 编译器来检测 SQL 映射，避免了由于 XML 配置中的错误导致的潜在问题。

#### 缺点：

- **灵活性差**：对于复杂的 SQL 语句和映射，注解的方式可能会变得冗长，且难以维护。
- **难以调试**：动态生成的 SQL 语句可能较难调试，尤其是涉及复杂逻辑时。
- **不支持所有 XML 特性**：一些高级特性（如动态 SQL、映射文件中复杂的 SQL 片段）在注解方式中实现起来较为困难。

## （5）Eureka 配置

相关官方部署文档地址如下：

https://docs.spring.io/spring-cloud-netflix/docs/current/reference/html/#registering-a-secure-application

### 1. 搭建Eureka 服务端

#### 1.1 引入依赖

引入spring-cloud-starter-netflix-eureka-server的依赖，直接通过父类的spring-cloud-dependencies

![image-20241213223040336](C:\Users\BlueJack\AppData\Roaming\Typora\typora-user-images\image-20241213223040336.png)

帮我们提供好了对应的Eureka Server版本，这样就不会有兼容问题！！！

```xml
  <!-- Eureka Server -->
  <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
  </dependency>
```

#### 1.2 给启动项添加@EnableEurekaServe

#### 1.3 添加application.yml文件，编写下面的配置：

```yaml
server:
  port: 8761

eureka:
  instance:
    hostname: 127.0.0.1
  client:
    registerWithEureka: false
    fetchRegistry: false
    serviceUrl:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
```

### 2. 错误问题

### 2.1 JDK 11 Support

Eureka 服务器所依赖的 JAXB 模块已在 JDK 11 中删除。如果您打算在运行 Eureka 服务器时使用 JDK 11，则必须在 POM 或 Gradle 文件中包含这些依赖项。

```xml
<dependency>
    <groupId>org.glassfish.jaxb</groupId>
    <artifactId>jaxb-runtime</artifactId>
</dependency>
```

### 2.2 依赖引入注意

报错信息如下：
The bean ‘eurekaRegistration’, defined in class path resource [org/springframework/cloud/netflix/eureka/EurekaClientAutoConfiguration $ EurekaClientConfiguration.class], could not be registered. A bean with that name has already been defined in class path resource[org/springframework/cloud/netflix/eureka/EurekaClientAutoConfiguration$RefreshableEurekaClientConfiguration.class] and overriding is disabled.

![在这里插入图片描述](https://i-blog.csdnimg.cn/blog_migrate/077814ae9a963ed907aaf79cc8fff475.png)

解决方法：

检查 pom.xml：

![在这里插入图片描述](https://i-blog.csdnimg.cn/blog_migrate/a1afcd0a72a17eba3799f63bbb894a5a.png)

将其修改为：

![在这里插入图片描述](https://i-blog.csdnimg.cn/blog_migrate/02c7edf6fe1a28baea289820d46460ca.png)

### 3. Eureka集群

通过运行多个实例并要求它们相互注册，可以使 Eureka 更具弹性和可用性。事实上，这是默认行为，因此要使其正常工作，您需要做的就是将有效的 `serviceUrl` 添加到对等节点，如以下示例所示：

application.yml (Two Peer Aware Eureka Servers)

```
---
spring:
  profiles: peer1
eureka:
  instance:
    hostname: peer1
  client:
    serviceUrl:
      defaultZone: https://peer2/eureka/

---
spring:
  profiles: peer2
eureka:
  instance:
    hostname: peer2
  client:
    serviceUrl:
      defaultZone: https://peer1/eureka/
```

在前面的示例中，我们有一个 YAML 文件，该文件可用于在两个主机（`peer1` 和 `peer2`）上运行同一服务器，方法是在不同的 Spring 配置文件中运行它。您可以使用此配置通过操作 `/etc/hosts` 来解析主机名，从而在单个主机上测试对等体感知（在生产中这样做没有太大价值）。事实上，如果你在知道自己的主机名的计算机上运行，则不需要 `eureka.instance.hostname`（默认情况下，使用 `java.net.InetAddress` 查找它）。

application.yml (Three Peer Aware Eureka Servers)

```
eureka:
  client:
    serviceUrl:
      defaultZone: https://peer1/eureka/,http://peer2/eureka/,http://peer3/eureka/

---
spring:
  profiles: peer1
eureka:
  instance:
    hostname: peer1

---
spring:
  profiles: peer2
eureka:
  instance:
    hostname: peer2

---
spring:
  profiles: peer3
eureka:
  instance:
    hostname: peer3
```

### 2. 搭建Eureka 客户端

#### 2.1 引入依赖

```xml
<!-- Eureka Server -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

#### 2.2 配置application.xml

```yaml
eureka:
  client:
    serviceUrl:
      defaultZone: http://127.0.0.1:8761/eureka/
```

### 3. 调用Eureka 调用接口

#### 3.1 添加@LoadBalanced注释

在RestTemplate容器创建的时候使用注释

```java
/**
 * 创建RestTemplate容器，并且注入Spring容器之中
 */
@Bean
@LoadBalanced
public RestTemplate restTemplate(){
    return new RestTemplate();
}
```

### 3.2 修改url地址为对应服务的名称

![image-20241213232616440](C:\Users\BlueJack\AppData\Roaming\Typora\typora-user-images\image-20241213232616440.png)

```java
// 2.2 url地址,发送请求到test-service -> localhost:8081
String url = "http://test-service/test/user/" + userId;
```