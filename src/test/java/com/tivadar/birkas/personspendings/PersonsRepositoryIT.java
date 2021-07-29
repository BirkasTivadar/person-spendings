package com.tivadar.birkas.personspendings;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PersonsRepositoryIT {

    @Autowired
    PersonsRepository repository;

    @BeforeEach
    void init() {
        repository.deleteAll();
    }

    @Test
    void testPersistAndFindAll() {
        Person john = new Person("123456789", "John Doe");
        Person jane = new Person("856456789", "Jane Doe");
        repository.save(john);
        repository.save(jane);

        List<Person> persons = repository.findAll();

        assertThat(persons)
                .extracting(Person::getName)
                .containsExactly("John Doe", "Jane Doe");
    }

    @Test
    void testPersistAndFindById() {
        Person john = new Person("123456789", "John Doe");
        Person jane = new Person("856456789", "Jane Doe");
        repository.save(john);
        repository.save(jane);

        long id = john.getId();
        Person person = repository.findById(id).orElseThrow();

        assertThat(person)
                .extracting(Person::getName)
                .isEqualTo("John Doe");
    }

    @Test
    void testPersistAndDeleteById() {
        Person john = new Person("123456789", "John Doe");
        Person jane = new Person("856456789", "Jane Doe");
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
    void testPersistAndDeleteAll() {
        Person john = new Person("123456789", "John Doe");
        Person jane = new Person("856456789", "Jane Doe");
        repository.save(john);
        repository.save(jane);

        repository.deleteAll();

        List<Person> persons = repository.findAll();

        assertThat(persons).isEmpty();
    }


}
