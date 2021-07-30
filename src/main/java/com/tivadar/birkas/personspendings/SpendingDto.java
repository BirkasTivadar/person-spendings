package com.tivadar.birkas.personspendings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpendingDto {

    private Long id;

    private LocalDate date;

    private String productOrService;

    private int cost;

//    private Person person;
}
