package com.myfreezer.sevices;

import com.myfreezer.entities.FreezerStorageItem;
import com.myfreezer.exceptions.NotFoundException;
import com.myfreezer.models.FoodRequest;
import com.myfreezer.repositories.FoodItemRepository;
import com.myfreezer.services.FreezerServices;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FreezerServicesTest {

    @Mock
    private FoodItemRepository mockFoodItemRepo;

    @InjectMocks
    private FreezerServices freezerServices;

    @Captor
    ArgumentCaptor<FreezerStorageItem> freezerStorageItemArgumentCaptor;

    private final String name = "Apple";
    private final String type = "Fruit";
    private final Integer quantity = 100;
    private final Long id = 20L;

    private FreezerStorageItem createFreezerStorageItem(){
        FreezerStorageItem freezerStorageItem = new FreezerStorageItem();
        freezerStorageItem.setType(type);
        freezerStorageItem.setQuantity(quantity);
        freezerStorageItem.setName(name);
        freezerStorageItem.setFreezerStorageItemId(id);

        return freezerStorageItem;
    }

    @Test
    void testSaveFoodItem(){
        //Setup
        FreezerStorageItem freezerStorageItem = createFreezerStorageItem();

        when(mockFoodItemRepo.save(any())).thenReturn(freezerStorageItem);

        //Test
        Long actualItemId = freezerServices.saveFoodItem(name,quantity,type);

        //Verify
        assertEquals(this.id,actualItemId);
        verify(mockFoodItemRepo, times(1)).save(any());
    }

    @Test
    void testGetFoodItemExist() throws NotFoundException {
        //Setup
        FreezerStorageItem freezerStorageItem = createFreezerStorageItem();
        Optional<FreezerStorageItem> optionalFreezerStorageItem = Optional.of(freezerStorageItem);

        when(mockFoodItemRepo.findById(id)).thenReturn(optionalFreezerStorageItem);

        //Test
        FoodRequest foodRequest = freezerServices.getFoodItemById(id);

        //Verify
        assertAll("Food Request Object",
                () -> assertEquals(this.id, foodRequest.getFoodId()),
                () -> assertEquals(this.name, foodRequest.getName()),
                () -> assertEquals(this.type, foodRequest.getType()),
                () -> assertEquals(this.quantity, foodRequest.getQuantity())
        );
        verify(mockFoodItemRepo, times(1)).findById(this.id);
    }

    @Test
    void testGetFoodItemDoesNotExist() {
        //Setup
        String expectedMessage = "The Id provided " + this.id + " does not match any item in the system";
        Optional<FreezerStorageItem> emptyOptional = Optional.empty();
        when(mockFoodItemRepo.findById(this.id)).thenReturn(emptyOptional);

        //Test
        Exception exception = assertThrows(NotFoundException.class, () ->
                freezerServices.getFoodItemById(this.id));

        //Verify
        String message = exception.getMessage();

        assertEquals(message, expectedMessage);
        verify(mockFoodItemRepo, times(1)).findById(this.id);
    }

    @Test
    void testUpdateFoodItemAllEntries() throws NotFoundException {
        //Setup
        String updatedName = "bapple";
        String updatedType = "Despicable";
        Integer updatedQuantity = 23;

        FoodRequest foodRequest = new FoodRequest();
        foodRequest.setType(updatedType);
        foodRequest.setName(updatedName);
        foodRequest.setQuantity(updatedQuantity);

        FreezerStorageItem freezerStorageItem = createFreezerStorageItem();
        Optional<FreezerStorageItem> optionalFreezerStorageItem = Optional.of(freezerStorageItem);

        when(mockFoodItemRepo.findById(id)).thenReturn(optionalFreezerStorageItem);
        when(mockFoodItemRepo.save(freezerStorageItemArgumentCaptor.capture())).thenReturn(freezerStorageItem);

        //Test
        Long actualItemId = freezerServices.updateFoodItem(this.id, foodRequest);
        FreezerStorageItem capturedFreezerStorageItem = freezerStorageItemArgumentCaptor.getValue();
        //Verify
        assertAll("Updated Database Entity",
                () -> assertEquals(updatedName, capturedFreezerStorageItem.getName()),
                () -> assertEquals(updatedType, capturedFreezerStorageItem.getType()),
                () -> assertEquals(updatedQuantity, capturedFreezerStorageItem.getQuantity())
                );
        assertEquals(this.id, actualItemId);
    }

    @Test
    void testUpdateFoodItemNoItemFound() {
        //Setup
        String updatedName = "bapple";
        String updatedType = "Despicable";
        Integer updatedQuantity = 23;

        FoodRequest foodRequest = new FoodRequest();
        foodRequest.setType(updatedType);
        foodRequest.setName(updatedName);
        foodRequest.setQuantity(updatedQuantity);

        String expectedMessage = "The Id provided " + this.id + " does not match any item in the system";
        Optional<FreezerStorageItem> emptyOptional = Optional.empty();
        when(mockFoodItemRepo.findById(this.id)).thenReturn(emptyOptional);

        //Test
        Exception exception = assertThrows(NotFoundException.class, () ->
        freezerServices.updateFoodItem(this.id, foodRequest));

        //Verify
        String message = exception.getMessage();

        assertEquals(message, expectedMessage);
        verify(mockFoodItemRepo, times(1)).findById(this.id);
    }

    @Test
    @DisplayName("Update only Quantity")
    void testUpdateFoodItemPartialOne() throws NotFoundException {
        //Setup
        Integer updatedQuantity = 23;

        FoodRequest foodRequest = new FoodRequest();
        foodRequest.setQuantity(updatedQuantity);

        FreezerStorageItem freezerStorageItem = createFreezerStorageItem();
        Optional<FreezerStorageItem> optionalFreezerStorageItem = Optional.of(freezerStorageItem);

        when(mockFoodItemRepo.findById(id)).thenReturn(optionalFreezerStorageItem);
        when(mockFoodItemRepo.save(freezerStorageItemArgumentCaptor.capture())).thenReturn(freezerStorageItem);

        //Test
        Long actualItemId = freezerServices.updateFoodItem(this.id, foodRequest);
        FreezerStorageItem capturedFreezerStorageItem = freezerStorageItemArgumentCaptor.getValue();
        //Verify
        assertAll("Updated Database Entity",
                () -> assertEquals(this.name, capturedFreezerStorageItem.getName()),
                () -> assertEquals(this.type, capturedFreezerStorageItem.getType()),
                () -> assertEquals(updatedQuantity, capturedFreezerStorageItem.getQuantity())
        );
        assertEquals(this.id, actualItemId);
    }

    @Test
    @DisplayName("Update only Name")
    void testUpdateFoodItemPartialTwo() throws NotFoundException {
        //Setup
        String updatedName = "bapple";

        FoodRequest foodRequest = new FoodRequest();
        foodRequest.setName(updatedName);

        FreezerStorageItem freezerStorageItem = createFreezerStorageItem();
        Optional<FreezerStorageItem> optionalFreezerStorageItem = Optional.of(freezerStorageItem);

        when(mockFoodItemRepo.findById(id)).thenReturn(optionalFreezerStorageItem);
        when(mockFoodItemRepo.save(freezerStorageItemArgumentCaptor.capture())).thenReturn(freezerStorageItem);

        //Test
        Long actualItemId = freezerServices.updateFoodItem(this.id, foodRequest);
        FreezerStorageItem capturedFreezerStorageItem = freezerStorageItemArgumentCaptor.getValue();
        //Verify
        assertAll("Updated Database Entity",
                () -> assertEquals(updatedName, capturedFreezerStorageItem.getName()),
                () -> assertEquals(this.type, capturedFreezerStorageItem.getType()),
                () -> assertEquals(this.quantity, capturedFreezerStorageItem.getQuantity())
        );
        assertEquals(this.id, actualItemId);
    }

    @Test
    @DisplayName("Update only Types")
    void testUpdateFoodItemPartialThree() throws NotFoundException {
        //Setup
        String updatedType = "Despicable";

        FoodRequest foodRequest = new FoodRequest();
        foodRequest.setType(updatedType);

        FreezerStorageItem freezerStorageItem = createFreezerStorageItem();
        Optional<FreezerStorageItem> optionalFreezerStorageItem = Optional.of(freezerStorageItem);

        when(mockFoodItemRepo.findById(id)).thenReturn(optionalFreezerStorageItem);
        when(mockFoodItemRepo.save(freezerStorageItemArgumentCaptor.capture())).thenReturn(freezerStorageItem);

        //Test
        Long actualItemId = freezerServices.updateFoodItem(this.id, foodRequest);
        FreezerStorageItem capturedFreezerStorageItem = freezerStorageItemArgumentCaptor.getValue();
        //Verify
        assertAll("Updated Database Entity",
                () -> assertEquals(this.name, capturedFreezerStorageItem.getName()),
                () -> assertEquals(updatedType, capturedFreezerStorageItem.getType()),
                () -> assertEquals(this.quantity, capturedFreezerStorageItem.getQuantity())
        );
        assertEquals(this.id, actualItemId);
    }

}
