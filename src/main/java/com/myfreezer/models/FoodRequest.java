package com.myfreezer.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.myfreezer.entities.FreezerStorageItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodRequest {

    private Long foodId;

    @NotBlank
    private String name;

    @NotBlank
    private String type;

    @Min(value = 1, message = "Quantity should be at least 1")
    private Integer quantity;

    @JsonProperty("date")
    private LocalDate creationDate;

    public FoodRequest(FreezerStorageItem freezerStorageItem) {
        super();
        this.foodId = freezerStorageItem.getFreezerStorageItemId();
        this.name = freezerStorageItem.getName();
        this.type = freezerStorageItem.getType();
        this.quantity = freezerStorageItem.getQuantity();
        this.creationDate=freezerStorageItem.getCreationDate();
    }
}
