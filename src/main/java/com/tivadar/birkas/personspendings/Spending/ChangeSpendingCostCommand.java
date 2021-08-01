package com.tivadar.birkas.personspendings.Spending;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeSpendingCostCommand {

    @Positive(message = "Cost must be larger than zero")
    private int cost;
}
