package com.myfreezer.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name="freezer_storage", schema = "public")
public class FreezerStorageItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "freezer_storage_item_id", unique = true, nullable = false)
    private Long freezerStorageItemId;

    @Column(name="name", nullable=false, unique=false)
    private String name;

    @Column(name="quantity", nullable=false, unique=false)
    private Integer quantity;

    @Column(name="type", nullable=false, unique=false)
    private String type;

    @UpdateTimestamp
    @Column(name="modified_date", unique=false)
    private LocalDateTime modifiedDate;

    @CreationTimestamp
    @Column(name="creation_date", nullable=false, unique=false)
    private LocalDateTime creationDate;

}
