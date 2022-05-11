package com.myfreezer.services;

import com.myfreezer.entities.FreezerStorageItem;
import com.myfreezer.exceptions.NotFoundException;
import com.myfreezer.models.FoodRequest;
import com.myfreezer.models.QuerySearch;
import com.myfreezer.repositories.FoodItemRepository;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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
        FreezerStorageItem foodItem = getFreezerStorageItem(id);
        return new FoodRequest(foodItem);
    }

    public Long updateFoodItem(Long id, FoodRequest foodRequest) throws NotFoundException {
        FreezerStorageItem freezerStorageItem = getFreezerStorageItem(id);

        if(!StringUtils.isBlank(foodRequest.getType())) {
            freezerStorageItem.setType(foodRequest.getType());
        }
        if(!StringUtils.isBlank(foodRequest.getName())) {
            freezerStorageItem.setName(foodRequest.getName());
        }

        if (foodRequest.getQuantity() != null) {
            freezerStorageItem.setQuantity(foodRequest.getQuantity());
        }

        return foodItemRepository.save(freezerStorageItem).getFreezerStorageItemId();
    }

    private FreezerStorageItem getFreezerStorageItem(Long id) throws NotFoundException {
        return foodItemRepository.findById(id).orElseThrow(() -> new NotFoundException("The Id provided " + id + " does not match any item in the system"));
    }

    public FreezerStorageItem searchStorage(QuerySearch querySearch){
        return null;
    }

}
