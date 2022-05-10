package com.myfreezer.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @NoArgsConstructor
public class QuerySearch {

    public String name;

    public String type;

    public LocalDateTime creationDate;

}
