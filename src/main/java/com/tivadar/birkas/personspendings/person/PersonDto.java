package com.tivadar.birkas.personspendings.person;

import com.tivadar.birkas.personspendings.spending.Spending;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {

    private Long id;

    private String socialSecurityNumber;

    private String name;

    private Long sumCosts;

    private List<Spending> spendingList;
}