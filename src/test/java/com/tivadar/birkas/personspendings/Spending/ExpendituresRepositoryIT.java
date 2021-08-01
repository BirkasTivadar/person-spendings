package com.tivadar.birkas.personspendings.Spending;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ExpendituresRepositoryIT {

    @Autowired
    ExpendituresRepository repository;

    private Spending bread = new Spending(
            LocalDate.of(2020, 10, 10),
            "bread",
            35);

    private Spending haircut = new Spending(
            LocalDate.of(2021, 11, 11),
            "haircut",
            1900);

    @BeforeEach
    void init() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Save two expenditures then query all")
    void testSaveAndFindAll() {
        repository.save(bread);
        repository.save(haircut);

        List<Spending> persons = repository.findAll();

        assertThat(persons)
                .extracting(Spending::getCost)
                .containsExactly(35, 1900);
    }

    @Test
    @DisplayName("Save two expenditures then find one by id")
    void testSaveAndFindById() {
        repository.save(bread);
        repository.save(haircut);

        long id = bread.getId();
        Spending spending = repository.findById(id).orElseThrow();

        assertThat(spending)
                .extracting(Spending::getProductOrService)
                .isEqualTo("bread");
    }

    @Test
    @DisplayName("Save two expenditures then delete one by id")
    void testSaveAndDeleteById() {
        repository.save(bread);
        repository.save(haircut);

        long id = bread.getId();
        repository.deleteById(id);

        List<Spending> spendingList = repository.findAll();

        assertThat(spendingList)
                .extracting(Spending::getProductOrService)
                .containsExactly("haircut");
    }

    @Test
    @DisplayName("Save two expenditures then delete all")
    void testSaveAndDeleteAll() {
        repository.save(bread);
        repository.save(haircut);

        repository.deleteAll();

        List<Spending> spendingList = repository.findAll();

        assertThat(spendingList).isEmpty();
    }
}