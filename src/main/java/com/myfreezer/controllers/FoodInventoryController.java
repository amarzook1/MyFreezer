package com.myfreezer.controllers;

import com.myfreezer.exceptions.NoDataFoundException;
import com.myfreezer.models.FoodRequest;
import com.myfreezer.models.QuerySearch;
import com.myfreezer.services.FreezerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping("/food")
public class FoodInventoryController {

    @Autowired
    private FreezerServices freezerServices;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public FoodRequest saveFoodItem(@RequestBody @Valid FoodRequest foodRequest){
        return freezerServices.saveFoodItem(foodRequest.getName(), foodRequest.getQuantity(), foodRequest.getType());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FoodRequest getFoodRequestById(@PathVariable("id") Long id) throws NoDataFoundException {
        return freezerServices.getFoodItemById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public FoodRequest updateFoodItem(@PathVariable(value = "id") Long id,
                                               @RequestBody FoodRequest foodRequest) throws NoDataFoundException {
        return freezerServices.updateFoodItem(id, foodRequest);
    }

    @PostMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<FoodRequest> getFoodItem(@RequestBody QuerySearch querySearch){
        return freezerServices.searchStorage(querySearch);
    }
}
