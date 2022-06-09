package com.myfreezer.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.myfreezer.entities.FreezerStorageItem;
import com.myfreezer.models.FoodRequest;
import com.myfreezer.repositories.FoodItemRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@Testcontainers
@SpringBootTest()
@AutoConfigureMockMvc
public class FoodInventoryControllerTest {

    @Autowired
    private FoodItemRepository foodItemRepository;

    @Autowired
    private MockMvc mockMvc;

    //Make Sure testcontainers.reuse.enable=true is set in your home directory in .testcontainer.properties
//    @Container
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

    @Test
    void saveFoodItem() throws Exception {
        FoodRequest foodRequest = new FoodRequest().builder().name("PineApple").type("Fruit").quantity(4).build();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(foodRequest);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/food")
                .header("API_TOKEN" , "password")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk());
    }

    @Test
    void getFoodItemById() throws Exception {
        FreezerStorageItem freezerStorageItem = new FreezerStorageItem().builder().name("Mango").quantity(10).type("Fruit").build();
        foodItemRepository.save(freezerStorageItem);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/food/{id}", 1)
                .header("API_TOKEN" , "password")
                .accept(MediaType.APPLICATION_JSON)).andDo(print());

        resultActions.andExpect(status().isOk());

    }
}
