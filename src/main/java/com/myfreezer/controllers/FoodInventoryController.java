package com.myfreezer.controllers;

import com.myfreezer.entities.FreezerStorageItem;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class FoodInventoryController {

    @GetMapping("/food")
    public int saveFoodItem(){

        return 1;
    }

    @PostMapping("/food/search")
    public FreezerStorageItem getFoodItem(){

        return null;
    }
}
