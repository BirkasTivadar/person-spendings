package com.tivadar.birkas.personspendings.Spending;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
//import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpendingDto {

    private Long id;

    private LocalDate date;

//    private LocalDateTime dateTime;

    private String productOrService;

    private int cost;

//    private Person person;
}
