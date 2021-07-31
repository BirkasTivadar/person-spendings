package com.tivadar.birkas.personspendings.Person;

import com.tivadar.birkas.personspendings.Person.Person;
import com.tivadar.birkas.personspendings.Person.Ssn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePersonCommand {

    @Ssn
    private String socialSecurityNumber;

    @NotBlank(message = "Name can not be blank")
    @Size(message = "Name length must be large than one characters, and less than fifty-one", min = Person.NAME_LENGTH_MIN, max = Person.NAME_LENGTH_MAX)
    private String name;
}
