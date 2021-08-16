package com.tivadar.birkas.personspendings.person;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = "delete from expenditures")
@Sql(statements = "delete from persons")
public class PersonsControllerRestTemplateIT {

    @Autowired
    TestRestTemplate template;

    private static final String DEFAULT_URL = "/api/persons/";

    @Test
    @DisplayName("Create two persons then query all")
    void testGetPersons() {
        template.postForObject(DEFAULT_URL,
                new CreatePersonCommand("123456789", "John Doe"), PersonDTO.class);

        template.postForObject(DEFAULT_URL,
                new CreatePersonCommand("856456789", "Jane Doe"), PersonDTO.class);

        List<PersonDTO> persons = template.exchange(DEFAULT_URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<PersonDTO>>() {
                        })
                .getBody();

        assertThat(persons)
                .extracting(PersonDTO::getName)
                .containsExactly("John Doe", "Jane Doe");
    }

    @Test
    @DisplayName("Create a person then query by id")
    void testGetPersonById() {
        PersonDTO person = template.postForObject(DEFAULT_URL,
                new CreatePersonCommand("123456789", "John Doe"), PersonDTO.class);

        long id = person.getId();

        PersonDTO testPerson = template.exchange(DEFAULT_URL + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PersonDTO>() {
                }
        ).getBody();

        assertThat(testPerson)
                .extracting(PersonDTO::getName)
                .isEqualTo("John Doe");
    }

    @Test
    @DisplayName("Create a person and update then query")
    void testUpdatePerson() {
        PersonDTO person = template.postForObject(DEFAULT_URL,
                new CreatePersonCommand("123456789", "John Doe"), PersonDTO.class);

        long id = person.getId();

        template.put(DEFAULT_URL + id, new ChangePersonNameCommand("John Jack Doe"));

        PersonDTO testPerson = template.exchange(DEFAULT_URL + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PersonDTO>() {
                }
        ).getBody();

        assertThat(testPerson)
                .extracting(PersonDTO::getName)
                .isEqualTo("John Jack Doe");
    }

    @Test
    @DisplayName("Create a person with exists SSN")
    void testCreatePersonWithExistsSsn() {
        PersonDTO person = template.postForObject(DEFAULT_URL,
                new CreatePersonCommand("123456789", "John Doe"), PersonDTO.class);

        PersonDTO testPerson = template.postForObject(DEFAULT_URL,
                new CreatePersonCommand("123456789", "Jack Doe"), PersonDTO.class);

        assertEquals(testPerson.getName(), person.getName());
    }

    @Test
    @DisplayName("Create a person, add a spending to it, then query")
    void testAddSpendingToPerson() {
        PersonDTO person = template.postForObject(DEFAULT_URL,
                new CreatePersonCommand("123456789", "John Doe"), PersonDTO.class);

        long id = person.getId();

        template.postForObject(DEFAULT_URL + id,
                new AddSpendingCommand(LocalDate.of(2000, 1, 1), "Apple laptop", 200_000), PersonDTO.class);

        PersonDTO testPerson = template.exchange(DEFAULT_URL + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PersonDTO>() {
                }).getBody();

        int cost = testPerson.getSpendingList().get(0).getCost();

        assertEquals(200_000, cost);
    }

    @Test
    @DisplayName("Create a person and add it some expenditures then query sum of costs")
    void testSumCosts() {
        PersonDTO person = template.postForObject(DEFAULT_URL,
                new CreatePersonCommand("123456789", "John Doe"), PersonDTO.class);

        long id = person.getId();

        template.postForObject(DEFAULT_URL + id,
                new AddSpendingCommand(LocalDate.of(2000, 1, 1), "Apple laptop", 200_000), PersonDTO.class);

        template.postForObject(DEFAULT_URL + id,
                new AddSpendingCommand(LocalDate.of(2010, 10, 10), "haircut", 1850), PersonDTO.class);

        template.postForObject(DEFAULT_URL + id,
                new AddSpendingCommand(LocalDate.of(2020, 12, 20), "apple", 75), PersonDTO.class);

        PersonDTO testPerson = template.exchange(DEFAULT_URL + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PersonDTO>() {
                }).getBody();

        assertEquals(201_925, testPerson.getSumCosts());
    }

    @Test
    @DisplayName("Create a person with invalid SSN")
    void testCreatePersonWithInvalidSsn() {
        Problem result = template.postForObject(DEFAULT_URL,
                new CreatePersonCommand("901456789", "John Doe"),
                Problem.class);

        assertEquals(Status.BAD_REQUEST, result.getStatus());
    }

    @Test
    @DisplayName("Create a person with invalid name")
    void testCreatePersonWithInvalidName() {
        Problem result = template.postForObject(DEFAULT_URL,
                new CreatePersonCommand("123456789", "J"),
                Problem.class);

        assertEquals(Status.BAD_REQUEST, result.getStatus());
    }

    @Test
    @DisplayName("Not found person")
    void testNotFoundPersonTest() {
        Problem result = template.getForObject(DEFAULT_URL + "1", Problem.class);

        assertEquals(URI.create("person/not-found"), result.getType());
        assertEquals(Status.NOT_FOUND, result.getStatus());
    }
}
