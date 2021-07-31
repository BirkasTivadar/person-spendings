package com.tivadar.birkas.personspendings.Spending;


import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/expenditures")
public class ExpendituresController {

    private ExpendituresService expendituresService;

    public ExpendituresController(ExpendituresService expendituresService) {
        this.expendituresService = expendituresService;
    }

    @GetMapping
    @Operation(summary = "Query all expenditures")
    public List<SpendingDto> getExpenditures() {
        return expendituresService.getExpenditures();
    }

    @GetMapping("{id}")
    @Operation(summary = "Query a spending by id")
    public SpendingDto getSpendingById(@PathVariable("id") long id) {
        return expendituresService.getSpendingById(id);
    }

    @PostMapping
    @Operation(summary = "Create a spending")
    @ResponseStatus(HttpStatus.CREATED)
    public SpendingDto createSpending(@Valid @RequestBody CreateSpendingCommand command) {
        return expendituresService.createSpending(command);
    }

    @PutMapping("{id}")
    @Operation(summary = "Change the cost of a spending by id")
    public SpendingDto changeSpendingCost(@PathVariable("id") long id, @Valid @RequestBody ChangeSpendingCostCommand command) {
        return expendituresService.changeSpendingCost(id, command);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete a spending by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSpending(@PathVariable("id") long id) {
        expendituresService.deleteSpending(id);
    }

    @DeleteMapping
    @Operation(summary = "Delete all expenditures")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteALL() {
        expendituresService.deleteAll();
    }


}
