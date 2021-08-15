package com.tivadar.birkas.personspendings.spending;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tivadar.birkas.personspendings.person.PersonDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpendingDTO {

    private Long id;

    private LocalDate spendingDate;

    private String productOrService;

    private int cost;

    @JsonBackReference
    private PersonDTO person;

}
