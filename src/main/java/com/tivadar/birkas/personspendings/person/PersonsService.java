package com.tivadar.birkas.personspendings.person;

import com.tivadar.birkas.personspendings.spending.Spending;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PersonsService {

    private static final String NOT_FOUND_PERSON_WITH_ID = "Not found person with id: ";

    private ModelMapper modelMapper;

    private PersonsRepository repository;

    public List<PersonDTO> getPersons() {
        return repository.findAll().stream()
                .map(p -> modelMapper.map(p, PersonDTO.class))
                .toList();
    }

    public PersonDTO getPersonById(long id) {
        Person person = repository.findById(id).orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_PERSON_WITH_ID + id));
        return modelMapper.map(person, PersonDTO.class);
    }

    public PersonDTO createPerson(CreatePersonCommand command) {
        Person person = new Person(command.getSocialSecurityNumber(), command.getName());
        if (!repository.existsPersonBySocialSecurityNumber(command.getSocialSecurityNumber())) {
            repository.save(person);
        } else {
            person = repository.findPersonBySocialSecurityNumber(command.getSocialSecurityNumber());
        }
        return modelMapper.map(person, PersonDTO.class);
    }

    @Transactional
    public PersonDTO changePersonName(long id, ChangePersonNameCommand command) {
        Person person = repository.findById(id).orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_PERSON_WITH_ID + id));
        person.setName(command.getName());
        return modelMapper.map(person, PersonDTO.class);
    }

    @Transactional
    public PersonDTO addSpendingToPerson(long id, AddSpendingCommand command) {
        Person person = repository.findById(id).orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_PERSON_WITH_ID + id));
        Spending spending = new Spending(command.getDate(), command.getProductOrService(), command.getCost());
        person.addSpending(spending);
        return modelMapper.map(person, PersonDTO.class);
    }

    public void deletePerson(long id) {
        repository.deleteById(id);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
