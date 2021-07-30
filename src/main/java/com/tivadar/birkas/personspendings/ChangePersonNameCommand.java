package com.tivadar.birkas.personspendings;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePersonNameCommand {

    @Size(message = "Name must be large than one characters, and less than fifty-one", min = Person.NAME_LENGTH_MIN, max = Person.NAME_LENGTH_MAX)
    private String name;
}
