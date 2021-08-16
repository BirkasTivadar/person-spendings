package com.tivadar.birkas.personspendings.spending;

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

    private final ExpendituresService expendituresService;

    public ExpendituresController(ExpendituresService expendituresService) {
        this.expendituresService = expendituresService;
    }

    @GetMapping
    @Operation(summary = "Query all expenditures")
    public List<SpendingDTO> getExpenditures() {
        return expendituresService.getExpenditures();
    }

    @PostMapping
    @Operation(summary = "Create a spending")
    @ResponseStatus(HttpStatus.CREATED)
    public SpendingDTO createSpending(@Valid @RequestBody CreateSpendingCommand command) {
        return expendituresService.createSpending(command);
    }

    @DeleteMapping
    @Operation(summary = "Delete all expenditures")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteALL() {
        expendituresService.deleteAll();
    }

    @GetMapping("{id}")
    @Operation(summary = "Query a spending by id")
    public SpendingDTO getSpendingById(@PathVariable("id") long id) {
        return expendituresService.getSpendingById(id);
    }

    @PutMapping("{id}")
    @Operation(summary = "Change the cost of a spending by id")
    public SpendingDTO changeSpendingCost(@PathVariable("id") long id, @Valid @RequestBody ChangeSpendingCostCommand command) {
        return expendituresService.changeSpendingCost(id, command);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete a spending by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSpending(@PathVariable("id") long id) {
        expendituresService.deleteSpending(id);
    }

    @GetMapping("/persons/{id}")
    @Operation(summary = "Query all expenditures of a person by person's id")
    public List<SpendingDTO> getExpendituresByPersonId(@PathVariable("id") long id) {
        return expendituresService.getExpendituresByPersonId(id);
    }

    @GetMapping("/persons/{id}/betweencosts/")
    @Operation(summary = "Query all expenditures of a person by person's id between two cost")
    public List<SpendingDTO> getExpendituresByPersonIdAndCostBetween(@PathVariable("id") long id, @RequestParam(name = "min", required = false) Integer min, @RequestParam(name = "max", required = false) Integer max) {
        return expendituresService.getExpendituresByPersonIdAndCostBetween(id, min, max);
    }

    @GetMapping("/sumofcostinyearormonth/")
    @Operation(summary = "Query sum of costs of this month")
    public Integer getSumCostsOfMonth(@RequestParam(name = "year", required = true) Integer numberOfYear, @RequestParam(name = "month", required = false) Integer numberOfMonth) {
        return expendituresService.getSumCostsOfMonth(numberOfYear, numberOfMonth);
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
