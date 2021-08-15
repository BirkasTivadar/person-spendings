package com.tivadar.birkas.personspendings.person;

import com.tivadar.birkas.personspendings.spending.SpendingDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {

    private Long id;

    private String socialSecurityNumber;

    private String name;

    private Long sumCosts;

    private List<SpendingDTO> spendingList = new ArrayList<>();
}