package com.tivadar.birkas.personspendings;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = "delete from persons")
public class PersonsControllerRestTemplateIT {

    @Autowired
    TestRestTemplate template;

    private static final String DEFAULT_URL = "/api/persons/";

    @BeforeEach
    void init() {
        template.postForObject(DEFAULT_URL,
                new CreatePersonCommand("123456789", "John Doe"), PersonDto.class);

        template.postForObject(DEFAULT_URL,
                new CreatePersonCommand("856456789", "Jane Doe"), PersonDto.class);
    }

    @RepeatedTest(2)
    @DisplayName("Create two persons then query all (run two times)")
    void testGetEmployees() {

        List<PersonDto> persons = template.exchange(DEFAULT_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PersonDto>>() {
                })
                .getBody();

        assertThat(persons)
                .extracting(PersonDto::getName)
                .containsExactly("John Doe", "Jane Doe");
    }

    @RepeatedTest(2)
    @DisplayName("Create two persons then query one by id (run two times)")
    void testGetEmployeeById() {
        List<PersonDto> persons = template.exchange(DEFAULT_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PersonDto>>() {
                })
                .getBody();

        String name = persons.get(0).getName();
        long id = persons.get(0).getId();

        PersonDto person = template.exchange(DEFAULT_URL + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PersonDto>() {
                }
        ).getBody();

        assertThat(person)
                .extracting(PersonDto::getName)
                .isEqualTo("John Doe");
    }
}
