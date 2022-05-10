package com.myfreezer.sevices;

import com.myfreezer.entities.FreezerStorageItem;
import com.myfreezer.exceptions.NotFoundException;
import com.myfreezer.models.FoodRequest;
import com.myfreezer.repositories.FoodItemRepository;
import com.myfreezer.services.FreezerServices;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FreezerServicesTest {

    @Mock
    private FoodItemRepository mockFoodItemRepo;

    @InjectMocks
    private FreezerServices freezerServices;

    @Test
    void testSave(){
        System.out.println("hello world");
    }

    @Test
    void testSaveFoodItem(){
        //Setup
        String name = "Apple";
        String type = "Fruit";
        Integer quantity = 100;

        FreezerStorageItem freezerStorageItem = new FreezerStorageItem();
        freezerStorageItem.setType(type);
        freezerStorageItem.setQuantity(quantity);
        freezerStorageItem.setName(name);
        freezerStorageItem.setFreezerStorageItemId(10L);

        when(mockFoodItemRepo.save(any())).thenReturn(freezerStorageItem);

        //Test
        Long id = freezerServices.saveFoodItem(name,quantity,type);

        //Verify
        assertEquals(id,10L);
        verify(mockFoodItemRepo, times(1)).save(any());
    }

    @Test
    void testGetFoodItemExist() throws NotFoundException {
        //Setup
        Long id = 12L;
        String name = "Apple";
        String type = "Fruit";
        Integer quantity = 100;

        FreezerStorageItem freezerStorageItem = new FreezerStorageItem();
        freezerStorageItem.setFreezerStorageItemId(id);
        freezerStorageItem.setName(name);
        freezerStorageItem.setQuantity(quantity);
        freezerStorageItem.setType(type);

        Optional<FreezerStorageItem> optionalFreezerStorageItem = Optional.of(freezerStorageItem);

        when(mockFoodItemRepo.findById(id)).thenReturn(optionalFreezerStorageItem);

        //Test
        FoodRequest foodRequest = freezerServices.getFoodItemById(id);

        //Verify
        assertEquals(foodRequest.getFoodId(), id);
        assertEquals(foodRequest.getName(), name);
        assertEquals(foodRequest.getType(), type);
        assertEquals(foodRequest.getQuantity(), quantity);
        verify(mockFoodItemRepo, times(1)).findById(id);
    }

    @Test
    void testGetFoodItemDoesNotExist() {
        //Setup
        Long id = 12L;

        Optional<FreezerStorageItem> emptyOptional = Optional.empty();
        when(mockFoodItemRepo.findById(id)).thenReturn(emptyOptional);

        //Test
        Exception exception = assertThrows(NotFoundException.class, () ->
        {freezerServices.getFoodItemById(id);});

        //Verify
        String message = exception.getMessage();
        String expectedMessage = "The Id provided " + id + " does not match any item in the system";

        assertEquals(message, expectedMessage);
        verify(mockFoodItemRepo, times(1)).findById(id);
    }

}
