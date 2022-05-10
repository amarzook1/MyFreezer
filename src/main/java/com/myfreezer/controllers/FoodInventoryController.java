package com.myfreezer.controllers;

import com.myfreezer.entities.FreezerStorageItem;
import com.myfreezer.exceptions.NotFoundException;
import com.myfreezer.models.FoodRequest;
import com.myfreezer.models.QuerySearch;
import com.myfreezer.services.FreezerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/food")
public class FoodInventoryController {

    @Autowired
    private FreezerServices freezerServices;

    @PostMapping()
    public Long saveFoodItem(@RequestBody FoodRequest foodRequest){
        return freezerServices.saveFoodItem(foodRequest.getName(), foodRequest.getQuantity(), foodRequest.getType());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodRequest> getFoodRequestById(@PathVariable("id") Long id) throws Exception {
        return new ResponseEntity<>(freezerServices.getFoodItemById(id), HttpStatus.OK);
    }

    @PostMapping("/search")
    public FreezerStorageItem getFoodItem(@RequestBody QuerySearch querySearch){

        return null;
    }
}
