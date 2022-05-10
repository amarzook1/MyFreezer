package com.myfreezer.services;

import com.myfreezer.entities.FreezerStorageItem;
import com.myfreezer.exceptions.NotFoundException;
import com.myfreezer.models.FoodRequest;
import com.myfreezer.models.QuerySearch;
import com.myfreezer.repositories.FoodItemRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service @NoArgsConstructor
public class FreezerServices {

    @Autowired
    private FoodItemRepository foodItemRepository;

    public Long saveFoodItem(String name, Integer quantity, String type){

        FreezerStorageItem freezerStorageItem = new FreezerStorageItem();
        freezerStorageItem.setName(name);
        freezerStorageItem.setQuantity(quantity);
        freezerStorageItem.setType(type);
       return foodItemRepository.save(freezerStorageItem).getFreezerStorageItemId();
    }

    public FoodRequest getFoodItemById(Long id) throws NotFoundException {
        Optional<FreezerStorageItem> foodItems = foodItemRepository.findById(id);
        if(foodItems.isPresent()) {
            return new FoodRequest(foodItems.get());
        } else {
            throw new NotFoundException("The Id provided " + id + " does not match any item in the system");
        }
    }

    public FreezerStorageItem searchStorage(QuerySearch querySearch){
        return null;
    }

}
