package com.tivadar.birkas.personspendings.Spending;

import com.tivadar.birkas.personspendings.Person.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpendingDto {

    private Long id;

    private LocalDate spendingDate;

    private String productOrService;

    private int cost;

    private Person person;
}
