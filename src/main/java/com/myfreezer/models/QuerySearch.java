package com.myfreezer.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data @NoArgsConstructor
public class QuerySearch {

    public String name;

    public String type;

    @JsonProperty("date")
    public LocalDate creationDate;

}
