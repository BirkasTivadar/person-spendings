package com.tivadar.birkas.personspendings.Person;

import com.tivadar.birkas.personspendings.Spending.ExpendituresRepository;
import com.tivadar.birkas.personspendings.Spending.Spending;
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

    private ExpendituresRepository expendituresRepository;

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
        if (!repository.existsPersonBySocialSecurityNumber(command.getSocialSecurityNumber())) {
            repository.save(person);
        } else {
            person = repository.findPersonBySocialSecurityNumber(command.getSocialSecurityNumber());
        }
        return modelMapper.map(person, PersonDto.class);
    }

    @Transactional
    public PersonDto changePersonName(long id, ChangePersonNameCommand command) {
        Person person = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found person with id: " + id));
        person.setName(command.getName());
        return modelMapper.map(person, PersonDto.class);
    }

    @Transactional
    public PersonDto addSpendingToPerson(long id, AddSpendingCommand command) {
        Person person = repository.findById(id).orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_PERSON_WITH_ID + id));
        Spending spending = new Spending(command.getDate(), command.getProductOrService(), command.getCost());
        person.addSpending(spending);
        return modelMapper.map(person, PersonDto.class);
    }

    public void deletePerson(long id) {
        repository.deleteById(id);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
