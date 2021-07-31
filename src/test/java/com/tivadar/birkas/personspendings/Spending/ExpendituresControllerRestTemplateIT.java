package com.tivadar.birkas.personspendings.Spending;

import com.tivadar.birkas.personspendings.Person.Person;
import com.tivadar.birkas.personspendings.Person.PersonsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

@DataJpaTest
public class ExpendituresControllerRestTemplateIT {

    @Autowired
    private PersonsRepository personsRepository;

    @Autowired
    private ExpendituresRepository expendituresRepository;

    @Test
    void testExpenditures() {
        Spending bread = new Spending(
                LocalDate.of(2020, 10, 10),
                "bread",
                35);

        Spending haircut = new Spending(
                LocalDate.of(2021, 11, 11),
                "haircut",
                1900);

        Person person = new Person("123456789", "John Doe");

        person.addSpending(bread);
        person.addSpending(haircut);
        System.out.println(person.getSpendingList());

        personsRepository.save(person);

        System.out.println(person.getSpendingList());
    }
}
