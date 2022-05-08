package com.myfreezer.repositories;


import com.myfreezer.entities.FreezerStorageItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodItemRepository extends JpaRepository<FreezerStorageItem, Long> {

}
