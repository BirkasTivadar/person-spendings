package com.tivadar.birkas.personspendings.spending;

import com.tivadar.birkas.personspendings.person.PersonDto;
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

    private PersonDto person;

    public SpendingDto(LocalDate spendingDate, String productOrService, int cost) {
        this.spendingDate = spendingDate;
        this.productOrService = productOrService;
        this.cost = cost;
    }

    public SpendingDto(LocalDate spendingDate, String productOrService, int cost, PersonDto person) {
        this.spendingDate = spendingDate;
        this.productOrService = productOrService;
        this.cost = cost;
        this.person = person;
    }

}
