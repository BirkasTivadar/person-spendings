package com.tivadar.birkas.personspendings;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeSpendingCostCommand {

    private int cost;
}
