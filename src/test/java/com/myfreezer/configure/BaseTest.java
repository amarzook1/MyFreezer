package com.myfreezer.configure;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

//@Testcontainers
@SpringBootTest()
@AutoConfigureMockMvc
public class BaseTest {

    //Make Sure testcontainers.reuse.enable=true is set in your home directory in .testcontainer.properties
    // @Container
    private static PostgreSQLContainer postgreSQLContainer = (PostgreSQLContainer) new PostgreSQLContainer("postgres:14.2-alpine").withReuse(true);

    @DynamicPropertySource
    public static void overideProps(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url",postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username",postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password",postgreSQLContainer::getPassword);
    }

    @BeforeAll
    public static void setUp(){
        postgreSQLContainer.start();
    }
}
