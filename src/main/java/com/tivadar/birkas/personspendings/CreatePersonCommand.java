package com.tivadar.birkas.personspendings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePersonCommand {

    private String socialSecurityNumber;

    private String name;
}
