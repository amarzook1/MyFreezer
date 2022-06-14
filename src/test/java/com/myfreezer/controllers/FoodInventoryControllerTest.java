package com.myfreezer.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.myfreezer.configure.BaseTest;
import com.myfreezer.entities.FreezerStorageItem;
import com.myfreezer.models.FoodRequest;
import com.myfreezer.repositories.FoodItemRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Random;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql({"/clearFreezerTable.sql"})
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
    void getFoodItemById() throws Exception {
        FreezerStorageItem freezerStorageItem = new FreezerStorageItem().builder().name("Mango").quantity(10).type("Fruit").build();
        Long dbId = foodItemRepository.save(freezerStorageItem).getFreezerStorageItemId();

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/food/{id}", dbId)
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("The Id provided "+ dbId + " does not match any item in the system"));
    }

}
