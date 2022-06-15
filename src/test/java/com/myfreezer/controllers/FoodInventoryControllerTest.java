package com.myfreezer.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.myfreezer.configure.BaseTest;
import com.myfreezer.models.FoodRequest;
import com.myfreezer.models.QuerySearch;
import com.myfreezer.repositories.FoodItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Random;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FoodInventoryControllerTest extends BaseTest {

    @Autowired
    private FoodItemRepository foodItemRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void saveFoodItem() throws Exception {
        FoodRequest foodRequest = new FoodRequest().builder().name("PineApple").type("Fruit").quantity(4).build();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(foodRequest);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/food")
                .header("API_TOKEN", "password")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print());

        resultActions.andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("PineApple"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("Fruit"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(4));
    }

    @Test
    void saveFoodItemMissingValues() throws Exception {
        FoodRequest foodRequest = new FoodRequest().builder().type("Fruit").quantity(4).build();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(foodRequest);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/food")
                .header("API_TOKEN", "password")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print());

        resultActions.andExpect(status().is4xxClientError());
    }

    @Test
    @Sql({"/createFreezerStorageItem.sql"})
    void getFoodItemById() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/food/{id}", 10)
                .header("API_TOKEN", "password")
                .accept(MediaType.APPLICATION_JSON)).andDo(print());

        resultActions.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Mango"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("Fruit"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(10));
    }

    @Test
    void getFoodItemByIdNotExisting() throws Exception {
        Long dbId = new Random().nextLong();

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/food/{id}", dbId)
                .header("API_TOKEN", "password")
                .accept(MediaType.APPLICATION_JSON)).andDo(print());

        resultActions.andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("NOT_FOUND"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("The Id provided " + dbId + " does not match any item in the system"));
    }

    @Test
    @Sql({"/createFreezerStorageItem.sql"})
    void updateFoodItemByIdIfExistOnlyQuantity() throws Exception {
        FoodRequest foodRequest = new FoodRequest().builder().quantity(12).build();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(foodRequest);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/food/{id}", 10)
                .header("API_TOKEN", "password")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print());

        resultActions.andExpect(status().isNoContent())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Mango"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("Fruit"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(12));
    }

    @Test
    @Sql({"/createFreezerStorageItem.sql"})
    void updateFoodItemByIdIfExistOnlyNameAndQuantity() throws Exception {
        FoodRequest foodRequest = new FoodRequest().builder().quantity(4).name("Alphonso (mango)").build();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(foodRequest);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/food/{id}", 10)
                .header("API_TOKEN", "password")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print());

        resultActions.andExpect(status().isNoContent())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Alphonso (mango)"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("Fruit"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(4));
    }

    @Test
    @Sql({"/createFreezerStorageItem.sql"})
    void updateFoodItemByIdIfExistOnlyType() throws Exception {
        FoodRequest foodRequest = new FoodRequest().builder().type("Produce").build();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(foodRequest);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/food/{id}", 10)
                .header("API_TOKEN", "password")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print());

        resultActions.andExpect(status().isNoContent())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Mango"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("Produce"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(10));
    }

    @Test
    @Sql({"/createFreezerStorageItem.sql"})
    void updateFoodItemByIdDoesNotExist() throws Exception {
        Long dbId = new Random().nextLong();

        FoodRequest foodRequest = new FoodRequest().builder().type("Produce").build();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(foodRequest);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/food/{id}", dbId)
                .header("API_TOKEN", "password")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print());

        resultActions.andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("NOT_FOUND"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("The Id provided " + dbId + " does not match any item in the system"));
    }

    @Test
    @Sql({"/createFreezerStorageItem.sql"})
    void searchForFoodItemIfExistByName() throws Exception {
        QuerySearch querySearch = new QuerySearch().builder().name("Ice Cream").build();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(querySearch);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/food/search")
                .header("API_TOKEN", "password")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print());

        resultActions.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].foodId").value(12))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("Ice Cream"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].type").value("Dessert"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].quantity").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].date").value("2022-06-14"));
    }

    @Test
    @Sql(scripts = {"/clearFreezerTable.sql", "/createFreezerStorageItem.sql"})
    void searchForFoodItemIfExistByTypeMultipleReturn() throws Exception {
        QuerySearch querySearch = new QuerySearch().builder().type("Fruit").build();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(querySearch);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/food/search")
                .header("API_TOKEN", "password")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print());

        resultActions.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].type").value("Fruit"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].type").value("Fruit"));
    }

    @Test
    @Sql({"/createFreezerStorageItem.sql"})
    void searchForFoodItemIfExistByDateMultipleReturn() throws Exception {
        QuerySearch querySearch = new QuerySearch().builder().creationDate(LocalDate.parse("2022-06-14")).build();
        /**
         * Due to ERROR: Java 8 date/time type `java.time.LocalDate` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling
         * I had to add a new module com.fasterxml.jackson.datatype:jackson-datatype-jsr310
         * This allowed me Serialize Java 8 Date With Jackson from: https://www.baeldung.com/jackson-serialize-dates#java-8
        **/
        ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(querySearch);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/food/search")
                .header("API_TOKEN", "password")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print());

        resultActions.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].date").value("2022-06-14"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].date").value("2022-06-14"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].date").value("2022-06-14"));
    }

    @Test
    void searchForFoodItemDoesNotExistByName() throws Exception {
        QuerySearch querySearch = new QuerySearch().builder().name("Lobster").build();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(querySearch);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/food/search")
                .header("API_TOKEN", "password")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print());

        resultActions.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(0)));
    }
}
