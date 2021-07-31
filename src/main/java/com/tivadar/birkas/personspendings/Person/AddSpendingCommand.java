package com.tivadar.birkas.personspendings.Person;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddSpendingCommand {

    private LocalDate date;

    @NotBlank(message = "Product or service can not be blank")
    @Size(message = "Product or service  must be less than one hundred characters", max = 99)
    private String productOrService;

    @Positive(message = "Cost must be larger than zero")
    private int cost;
}
