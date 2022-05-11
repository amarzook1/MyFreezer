package com.myfreezer.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.myfreezer.entities.FreezerStorageItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodRequest {

    private Long foodId;

    private String name;

    private String type;

    private Integer quantity;

    @JsonProperty("date")
    private LocalDateTime creationDate;

    public FoodRequest(FreezerStorageItem freezerStorageItem) {
        super();
        this.foodId = freezerStorageItem.getFreezerStorageItemId();
        this.name = freezerStorageItem.getName();
        this.type = freezerStorageItem.getType();
        this.quantity = freezerStorageItem.getQuantity();
        this.creationDate=freezerStorageItem.getCreationDate();
    }
}
