package com.tivadar.birkas.personspendings.Person;

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
@RequestMapping("/api/persons")
public class PersonsController {

    private PersonsService personsService;

    public PersonsController(PersonsService personsService) {
        this.personsService = personsService;
    }

    @GetMapping
    @Operation(summary = "Query all persons")
    public List<PersonDto> getPersons() {
        return personsService.getPersons();
    }

    @GetMapping("{id}")
    @Operation(summary = "Query a person by id")
    public PersonDto getPersonById(@PathVariable("id") long id) {
        return personsService.getPersonById(id);
    }

    @PostMapping
    @Operation(summary = "Create a person")
    @ResponseStatus(HttpStatus.CREATED)
    public PersonDto createPerson(@Valid @RequestBody CreatePersonCommand command) {
        return personsService.createPerson(command);
    }

    @PutMapping ("{id}")
    @Operation(summary = "Change the name of a person by id")
    public PersonDto changePersonName(@PathVariable("id") long id,@Valid @RequestBody ChangePersonNameCommand command) {
        return personsService.changePersonName(id, command);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete a person by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@PathVariable("id") long id) {
        personsService.deletePerson(id);
    }

    @DeleteMapping
    @Operation(summary = "Delete all persons")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteALL() {
        personsService.deleteAll();
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

//    @ExceptionHandler(IllegalStateException.class)
//    public ResponseEntity<Problem> handleNotFound(IllegalStateException exception) {
//        Problem problem = Problem.builder()
//                .withType(URI.create("person/invalid-name"))
//                .withTitle("Invalid Name")
//                .withStatus(Status.BAD_REQUEST)
//                .withDetail(exception.getMessage())
//                .build();
//
//        return ResponseEntity
//                .status(HttpStatus.BAD_REQUEST)
//                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
//                .body(problem);
//    }
}
