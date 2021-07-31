package com.tivadar.birkas.personspendings.Spending;


import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/spendings")
public class SpendingsController {

    private SpendingsService spendingsService;

    public SpendingsController(SpendingsService spendingsService) {
        this.spendingsService = spendingsService;
    }

    @GetMapping
    @Operation(summary = "Query all spendings")
    public List<SpendingDto> getSpendings() {
        return spendingsService.getSpendings();
    }

    @GetMapping("{id}")
    @Operation(summary = "Query a spending by id")
    public SpendingDto getSpendingById(@PathVariable("id") long id) {
        return spendingsService.getSpendingById(id);
    }

    @PostMapping
    @Operation(summary = "Create a spending")
    @ResponseStatus(HttpStatus.CREATED)
    public SpendingDto createSpending(@Valid @RequestBody CreateSpendingCommand command) {
        return spendingsService.createSpending(command);
    }

    @PutMapping("{id}")
    @Operation(summary = "Change the cost of a spending by id")
    public SpendingDto changeSpendingCost(@PathVariable("id") long id, @Valid @RequestBody ChangeSpendingCostCommand command) {
        return spendingsService.changeSpendingCost(id, command);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete a spending by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSpending(@PathVariable("id") long id) {
        spendingsService.deleteSpending(id);
    }

    @DeleteMapping
    @Operation(summary = "Delete all spendings")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteALL() {
        spendingsService.deleteAll();
    }


}
