package com.myfreezer.models;

import com.myfreezer.entities.FreezerStorageItem;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FoodRequest {

    private Long foodId;

    private String name;

    private String type;

    private Integer quantity;

    public FoodRequest(FreezerStorageItem freezerStorageItem) {
        super();
        this.foodId = freezerStorageItem.getFreezerStorageItemId();
        this.name = freezerStorageItem.getName();
        this.type = freezerStorageItem.getType();
        this.quantity = freezerStorageItem.getQuantity();
    }
}
