package com.tivadar.birkas.personspendings.Person;


import com.tivadar.birkas.personspendings.Person.Person;
import com.tivadar.birkas.personspendings.Person.PersonsRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PersonsRepositoryIT {

    @Autowired
    PersonsRepository repository;

    private Person john = new Person("123456789", "John Doe");
    private Person jane = new Person("856456789", "Jane Doe");

    @BeforeEach
    void init() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Save two persons then query all")
    void testSaveAndFindAll() {
        repository.save(john);
        repository.save(jane);

        List<Person> persons = repository.findAll();

        assertThat(persons)
                .extracting(Person::getName)
                .containsExactly("John Doe", "Jane Doe");
    }

    @Test
    @DisplayName("Save two persons then find one by id")
    void testSaveAndFindById() {
        repository.save(john);
        repository.save(jane);

        long id = john.getId();
        Person person = repository.findById(id).orElseThrow();

        assertThat(person)
                .extracting(Person::getName)
                .isEqualTo("John Doe");
    }

    @Test
    @DisplayName("Save two persons then delete one by id")
    void testSaveAndDeleteById() {
        repository.save(john);
        repository.save(jane);

        long id = john.getId();
        repository.deleteById(id);

        List<Person> persons = repository.findAll();

        assertThat(persons)
                .extracting(Person::getName)
                .containsExactly("Jane Doe");
    }

    @Test
    @DisplayName("Save two persons then delete all")
    void testSaveAndDeleteAll() {
        repository.save(john);
        repository.save(jane);

        repository.deleteAll();

        List<Person> persons = repository.findAll();

        assertThat(persons).isEmpty();
    }


}
