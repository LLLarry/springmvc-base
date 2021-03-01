### 项目介绍

> 这是一个 springmvc + mybatis + jdbc 的基础项目



**静态资源介绍**

* `applicationContext.xml`： 主配置文件 在`web.xml`中配置的`DispatchServlet`引入的xml文件使用了它，它是整合了`spring-service.xml` 、`spring-dao.xml` 和 `spring-mvc.xml`

* `mybatis-config.xml`: mybatis的配置文件，主要作用是：给包取别名、映射mapper文件等
* `spring-dao.xml`: 功能：1、创建DataSource数据源   2、创建SqlSessionFactory对象（mybatis使用）  3、配置dao接口扫描包,实现了dao接口可以注入spring容器中

```java
配置dao接口扫描包,实现了dao接 这样写就避免了
        public class BooksMapperImpl extends SqlSessionDaoSupport implements BooksMapper {
            public int insertBooks(Books books) {
                SqlSession sqlSession = getSqlSession();
                BooksMapper mapper = sqlSession.getMapper(BooksMapper.class);
                return mapper.insertBooks(books);
            }

            public int deleteById(int id) {
                SqlSession sqlSession = getSqlSession();
                BooksMapper mapper = sqlSession.getMapper(BooksMapper.class);
                return mapper.deleteById(id);
            }

            public int updateBooks(Books books) {
                SqlSession sqlSession = getSqlSession();
                BooksMapper mapper = sqlSession.getMapper(BooksMapper.class);
                return mapper.updateBooks(books);
            }

            public Books queryBooksById(int id) {
                SqlSession sqlSession = getSqlSession();
                BooksMapper mapper = sqlSession.getMapper(BooksMapper.class);
                return mapper.queryBooksById(id);
            }

            public List<Books> queryBooks() {
                SqlSession sqlSession = getSqlSession();
                BooksMapper mapper = sqlSession.getMapper(BooksMapper.class);
                return mapper.queryBooks();
            }
        }

        <bean id="booksMapper" class="nuc.ss.dao.BooksMapperImpl">
           <property name="sqlSessionFactory" ref="sqlSessionFactory" />
        </bean>
```



* `spring-service.xml`: 作用： 1、扫描service下的包   2、将我们的service的业务实现类 交托给spring管理，并通过set注入属性  3、声明式事务配置  4、aop事务支持
* `spring-mvc.xml` : 作用： 1、开启注解驱动 可以使得controller中的注解类能被扫描到   2、静态资源过滤  3、扫描包  4、视图解析器

#### 执行流程

1、用户访问`/book/allbooks` 路径，由于我们在web.xml中配置了`DispatchServlet`所有会执行`DispatchServlet`

2、`DispatchServlet`会通过 `HandlerMapper`找到对应的`Controller`类中的方法交给`DispatchServlet`，`DispatchServlet`通过`HandlerAdapter`执行方法

3、执行的过程中我们需要使用`service`中的业务接口实现类（这个类我们在`spring-service.xml`中已经被spring管理了，所以可以直接通过注解实行自动装配使用

```xml
<bean id="booksServerImpl" class="com.larry.service.BooksServerImpl">
    <property name="booksMapper" ref="booksMapper"></property>
</bean>
```

> 注意： booksMapper是BooksMapper接口的实现类 是在`spring-dao.xml`中扫描包自动给创建的对象

```xml
 <!-- 配置dao接口扫描包,实现了dao接口可以注入spring容器中 -->
<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <!-- 注入sqlSessionFactory
            sqlSessionFactoryBeanName: value 将bean的id传过去
            sqlSessionFactory： ref  将bean的对象传进去
        -->
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"></property>
<!--        <property name="sqlSessionFactory" ref="sqlSessionFactory"></property>-->
        <!-- 配置要扫描的包 -->
        <property name="basePackage" value="com.larry.dao"></property>
    </bean>
```



），

```java
@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    @Qualifier("booksServerImpl")
    private BooksServerImpl booksServer;
    @RequestMapping("/allbooks")
    public String allBooks(Model model) {
        List<Books> books = booksServer.queryBooks();
        model.addAttribute("list", books);
        return "allBook";
    }
}
```

* 4、将查询到的数据返回给`DispatchServlet` 然后`DispatchServlet`将返回页面路径交给视图解析器，视图解析器解析好之后再返回给`DispatchServlet`，`DispatchServlet`将解析好的路径和数据传给前台
* 5、完成渲染





**项目注意点**

在idea中需要手动将依赖包在WEB-INF文件夹下新建一个lib文件夹，将依赖包放进去

>步骤： idea顶部Project Structure --> Artifacts --> 选中对应的模块 右边 WEB-INF文件夹下新建一个lib文件夹，将依赖包放进去





#### 依赖

```xml
<dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>servlet-api</artifactId>
            <version>2.5</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet.jsp</groupId>
            <artifactId>jsp-api</artifactId>
            <version>2.2</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>jstl</artifactId>
            <version>1.2</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.3</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>2.0.3</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>5.2.4.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.46</version>
        </dependency>
        <dependency>
            <groupId>com.mchange</groupId>
            <artifactId>c3p0</artifactId>
            <version>0.9.5.4</version>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.16.20</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.1.9.RELEASE</version>
        </dependency>
    </dependencies>

    <build>
        <resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>false</filtering>
            </resource>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>false</filtering>
            </resource>
        </resources>
    </build>
```

