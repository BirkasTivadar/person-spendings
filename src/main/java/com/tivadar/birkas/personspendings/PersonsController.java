package com.tivadar.birkas.personspendings;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonsController {

    private PersonsService personsService;

    public PersonsController(PersonsService personsService) {
        this.personsService = personsService;
    }

    @GetMapping
    public List<PersonDto> getPersons() {
        return personsService.getPersons();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonDto createPerson(@RequestBody CreatePersonCommand command) {
        return personsService.createPerson(command);
    }

    @PutMapping("/{id}")
    public PersonDto changePersonName(@PathVariable("id") long id, @RequestBody ChangePersonNameCommand command) {
        return personsService.changePersonName(id, command);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@PathVariable("id") long id) {
        personsService.deletePerson(id);
    }


}
