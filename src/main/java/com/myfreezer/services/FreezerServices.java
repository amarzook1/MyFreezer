package com.myfreezer.services;

import com.myfreezer.entities.FreezerStorageItem;
import com.myfreezer.models.QuerySearch;
import com.myfreezer.repositories.FoodItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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

    public FreezerStorageItem searchStorage(QuerySearch querySearch){
        return null;
    }

}
