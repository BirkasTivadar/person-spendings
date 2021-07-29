package com.tivadar.birkas.personspendings;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
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


//    @Test
    @RepeatedTest(2)
    void testGetEmployees() {
        template.postForObject("/api/persons",
                new CreatePersonCommand("123456789", "John Doe"), PersonDto.class);

        template.postForObject("/api/persons",
                new CreatePersonCommand("856456789", "Jane Doe"), PersonDto.class);

       List<PersonDto> persons = template.exchange("/api/persons",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PersonDto>>() {})
                .getBody();

       assertThat(persons)
               .extracting(PersonDto::getName)
               .containsExactly("John Doe", "Jane Doe");
    }
}
