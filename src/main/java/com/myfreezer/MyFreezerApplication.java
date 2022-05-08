package com.myfreezer;

import com.myfreezer.repositories.FoodItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.myfreezer.repositories")
public class MyFreezerApplication implements CommandLineRunner {

	@Autowired
	private FoodItemRepository foodItemRepository;

	public static void main(String[] args) {
		SpringApplication.run(MyFreezerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
