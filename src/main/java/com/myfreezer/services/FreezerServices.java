package com.myfreezer.services;

import com.myfreezer.entities.FreezerStorageItem;
import com.myfreezer.exceptions.NotFoundException;
import com.myfreezer.models.FoodRequest;
import com.myfreezer.models.QuerySearch;
import com.myfreezer.repositories.FoodItemRepository;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@NoArgsConstructor
public class FreezerServices {

    @Autowired
    private FoodItemRepository foodItemRepository;

    /**
     * Save a food item with the parameters provided into the DB
     * @param name
     * @param quantity
     * @param type
     * @return Id of saved item
     */
    public Long saveFoodItem(String name, Integer quantity, String type) {

        FreezerStorageItem freezerStorageItem = new FreezerStorageItem();
        freezerStorageItem.setName(name);
        freezerStorageItem.setQuantity(quantity);
        freezerStorageItem.setType(type);
        return foodItemRepository.save(freezerStorageItem).getFreezerStorageItemId();
    }

    /**
     * Get a saved food item from the DB by ID
     * @param id
     * @return Food item from DB
     * @throws NotFoundException
     */
    public FoodRequest getFoodItemById(Long id) throws NotFoundException {
        FreezerStorageItem foodItem = getFreezerStorageItem(id);
        return new FoodRequest(foodItem);
    }

    /**
     * Update an existing food item in the DB with the updated values which the user provides
     * The user does not need to send all the parameters and an error will be thrown
     * if the item does not exist in the DB.
     * @param id
     * @param foodRequest
     * @return Id of updated item
     * @throws NotFoundException
     */
    public Long updateFoodItem(Long id, FoodRequest foodRequest) throws NotFoundException {
        FreezerStorageItem freezerStorageItem = getFreezerStorageItem(id);

        if (!StringUtils.isBlank(foodRequest.getType())) {
            freezerStorageItem.setType(foodRequest.getType());
        }
        if (!StringUtils.isBlank(foodRequest.getName())) {
            freezerStorageItem.setName(foodRequest.getName());
        }

        if (foodRequest.getQuantity() != null) {
            freezerStorageItem.setQuantity(foodRequest.getQuantity());
        }

        return foodItemRepository.save(freezerStorageItem).getFreezerStorageItemId();
    }

    /**
     * Method to get a food item by ID from the DB
     * @param id
     * @return DB entity
     * @throws NotFoundException
     */
    private FreezerStorageItem getFreezerStorageItem(Long id) throws NotFoundException {
        return foodItemRepository.findById(id).orElseThrow(() -> new NotFoundException("The Id provided " + id + " does not match any item in the system"));
    }
    /**
     * Search food item with the values given by the user not all the values are needed
     * It uses the JPA .findAll() Method in order to find items which contain values which
     * the user provides
     * @param querySearch
     * @return List of food items
     */
    public List<FoodRequest> searchStorage(QuerySearch querySearch) {
        FreezerStorageItem freezerStorageItem = new FreezerStorageItem().builder()
                .name(querySearch.getName())
                .type(querySearch.getType())
                .creationDate(querySearch.getCreationDate())
                .build();
        List<FoodRequest> searchResults = foodItemRepository.findAll(Example.of(freezerStorageItem))
                .stream().map(i -> new FoodRequest(i)).collect(Collectors.toList());
        return searchResults;
    }

}
