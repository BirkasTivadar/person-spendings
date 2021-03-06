package com.tivadar.birkas.personspendings.person;

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
@RequestMapping("/api/persons/")
public class PersonsController {

    private final PersonsService personsService;

    public PersonsController(PersonsService personsService) {
        this.personsService = personsService;
    }

    @GetMapping
    @Operation(summary = "Query all persons")
    public List<PersonDTO> getPersons() {
        return personsService.getPersons();
    }

    @PostMapping
    @Operation(summary = "Create a person")
    @ResponseStatus(HttpStatus.CREATED)
    public PersonDTO createPerson(@Valid @RequestBody CreatePersonCommand command) {
        return personsService.createPerson(command);
    }

    @DeleteMapping
    @Operation(summary = "Delete all persons")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteALL() {
        personsService.deleteAll();
    }

    @GetMapping("{id}")
    @Operation(summary = "Query a person by id")
    public PersonDTO getPersonById(@PathVariable("id") long id) {
        return personsService.getPersonById(id);
    }

    @PutMapping("{id}")
    @Operation(summary = "Change the name of a person by id")
    public PersonDTO changePersonName(@PathVariable("id") long id, @Valid @RequestBody ChangePersonNameCommand command) {
        return personsService.changePersonName(id, command);
    }

    @PostMapping("{id}")
    @Operation(summary = "Add a spending to person by id")
    public PersonDTO addSpendingToPerson(@PathVariable("id") long id, @Valid @RequestBody AddSpendingCommand command) {
        return personsService.addSpendingToPerson(id, command);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete a person by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@PathVariable("id") long id) {
        personsService.deletePerson(id);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Problem> handleNotFound(IllegalArgumentException exception) {
        Problem problem = Problem.builder()
                .withType(URI.create("person/not-found"))
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
