package com.tivadar.birkas.personspendings;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = "delete from persons")
public class PersonsControllerRestTemplateIT {

    @Autowired
    TestRestTemplate template;

    private static final String DEFAULT_URL = "/api/persons/";

    @Test
    @DisplayName("Create two persons then query all")
    void testGetPersons() {
        template.postForObject(DEFAULT_URL,
                new CreatePersonCommand("123456789", "John Doe"), PersonDto.class);

        template.postForObject(DEFAULT_URL,
                new CreatePersonCommand("856456789", "Jane Doe"), PersonDto.class);

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

    @Test
    @DisplayName("Create a person then query by id")
    void testGetPersonById() {
        PersonDto person = template.postForObject(DEFAULT_URL,
                new CreatePersonCommand("123456789", "John Doe"), PersonDto.class);


        long id = person.getId();

        PersonDto testPerson = template.exchange(DEFAULT_URL + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PersonDto>() {
                }
        ).getBody();

        assertThat(testPerson)
                .extracting(PersonDto::getName)
                .isEqualTo("John Doe");
    }

    @Test
    @DisplayName("Create a person and update then query")
    void testUpdatePerson() {
        PersonDto person = template.postForObject(DEFAULT_URL,
                new CreatePersonCommand("123456789", "John Doe"), PersonDto.class);

        long id = person.getId();

        template.put(DEFAULT_URL + id, new ChangePersonNameCommand("John Jack Doe"));

        PersonDto testPerson = template.exchange(DEFAULT_URL + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PersonDto>() {
                }
        ).getBody();

        assertThat(testPerson)
                .extracting(PersonDto::getName)
                .isEqualTo("John Jack Doe");
    }

    @Test
    @DisplayName("Create a person with invalid SSN")
    void createPersonWithInvalidSsn() {
        Problem result = template.postForObject(DEFAULT_URL,
                new CreatePersonCommand("901456789", "John Doe"),
                Problem.class);

        assertEquals(Status.BAD_REQUEST, result.getStatus());
    }

    @Test
    @DisplayName("Create a person with invalid SSN")
    void createPersonWithInvalidName() {
        Problem result = template.postForObject(DEFAULT_URL,
                new CreatePersonCommand("123456789", "J"),
                Problem.class);

        assertEquals(Status.BAD_REQUEST, result.getStatus());
    }

    @Test
    @DisplayName("Not found person")
    void notFoundPersonTest() {
        Problem result = template.getForObject(DEFAULT_URL + "1", Problem.class);

        assertEquals(URI.create("person/not-found"), result.getType());
        assertEquals(Status.NOT_FOUND, result.getStatus());
    }

//    @Test
//    void changePersonNameWithInvalidName(){
//        PersonDto person = template.postForObject(DEFAULT_URL,
//                new CreatePersonCommand("123456789", "John Doe"), PersonDto.class);
//
//        Long id = person.getId();
//
//        Problem result = template.postForObject(
//                DEFAULT_URL + "1",
//                new ChangePersonNameCommand("J"),
//                Problem.class);
//
//        assertEquals(URI.create("person/invalid-name"), result.getType());
//        assertEquals(Status.BAD_REQUEST, result.getStatus());
//    }
}
