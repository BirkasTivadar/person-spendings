package com.tivadar.birkas.personspendings.Spending;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import javax.validation.Valid;
import java.net.URI;
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

    @GetMapping("/persons/{id}")
    @Operation(summary = "Query all expenditures by person id")
    public List<SpendingDto> getExpendituresByPersonId(@PathVariable("id") long id){
        return expendituresService.getExpendituresByPersonId(id);
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

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Problem> handleNotFound(IllegalArgumentException exception) {
        Problem problem = Problem.builder()
                .withType(URI.create("expenditures/not-found"))
                .withTitle("Not Found")
                .withStatus(Status.NOT_FOUND)
                .withDetail(exception.getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }
}
