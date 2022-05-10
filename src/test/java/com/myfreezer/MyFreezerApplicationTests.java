package com.myfreezer;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = { "spring.liquibase.enabled=false" })
class MyFreezerApplicationTests {

	@Test
	void contextLoads() {
	}

}
