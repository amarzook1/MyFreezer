package com.myfreezer.controllers;

import com.myfreezer.exceptions.NotFoundException;
import com.myfreezer.models.FoodRequest;
import com.myfreezer.models.QuerySearch;
import com.myfreezer.services.FreezerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
    public FoodRequest getFoodRequestById(@PathVariable("id") @NotNull Long id) throws NotFoundException {
        return freezerServices.getFoodItemById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public FoodRequest updateFoodItem(@PathVariable(value = "id") Long id,
                                               @RequestBody FoodRequest foodRequest) throws NotFoundException {
        return freezerServices.updateFoodItem(id, foodRequest);
    }

    @PostMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<FoodRequest> getFoodItem(@RequestBody QuerySearch querySearch){
        return freezerServices.searchStorage(querySearch);
    }
}
