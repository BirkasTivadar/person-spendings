package com.tivadar.birkas.personspendings;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PersonsService {

    private ModelMapper modelMapper;

    private PersonsRepository repository;

    public List<PersonDto> getPersons() {
        return repository.findAll().stream()
                .map(p -> modelMapper.map(p, PersonDto.class))
                .toList();
    }

    public PersonDto getPersonById(long id) {
        Person person = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found person with id: " + id));
        return modelMapper.map(person, PersonDto.class);
    }

    public PersonDto createPerson(CreatePersonCommand command) {
        Person person = new Person(command.getSocialSecurityNumber(), command.getName());
        repository.save(person);
        return modelMapper.map(person, PersonDto.class);
    }

    @Transactional
    public PersonDto changePersonName(long id, ChangePersonNameCommand command) {
        Person person = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found person with id: " + id));
        person.setName(command.getName());
        return modelMapper.map(person, PersonDto.class);
    }

    public void deletePerson(long id) {
        repository.deleteById(id);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}