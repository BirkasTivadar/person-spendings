package com.tivadar.birkas.personspendings.spending;

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
public class ExpendituresControllerRestTemplateIT {

    @Autowired
    TestRestTemplate template;

    private static final String DEFAULT_URL = "/api/expenditures/";

    @Test
    @DisplayName("Create two expenditures then query all")
    void testGetExpenditures() {
        template.postForObject(DEFAULT_URL,
                new CreateSpendingCommand(LocalDate.of(2000, 1, 1), "Apple laptop", 200_000), SpendingDto.class);

        template.postForObject(DEFAULT_URL,
                new CreateSpendingCommand(LocalDate.of(2020, 6, 30), "haircut", 1800), SpendingDto.class);

        List<SpendingDto> spendingDtoList = template.exchange(DEFAULT_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<SpendingDto>>() {
                })
                .getBody();

        assertThat(spendingDtoList)
                .extracting(SpendingDto::getProductOrService)
                .containsExactly("Apple laptop", "haircut");
    }

    @Test
    @DisplayName("Create a spending then query by id")
    void testGetSpendingById() {
        SpendingDto spending = template.postForObject(DEFAULT_URL,
                new CreateSpendingCommand(LocalDate.of(2000, 1, 1), "Apple laptop", 200_000), SpendingDto.class);

        long id = spending.getId();

        SpendingDto testSpending = template.exchange(DEFAULT_URL + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<SpendingDto>() {
                })
                .getBody();

        assertThat(testSpending)
                .extracting(SpendingDto::getSpendingDate)
                .isEqualTo(LocalDate.of(2000, 1, 1));
    }

    @Test
    @DisplayName("Create a spending and update then query")
    void testUpdateSpending() {
        SpendingDto spending = template.postForObject(DEFAULT_URL,
                new CreateSpendingCommand(LocalDate.of(2000, 1, 1), "Apple laptop", 200_000), SpendingDto.class);

        long id = spending.getId();

        template.put(DEFAULT_URL + id, new ChangeSpendingCostCommand(15_000));

        SpendingDto testSpending = template.exchange(DEFAULT_URL + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<SpendingDto>() {
                })
                .getBody();

        assertThat(testSpending)
                .extracting(SpendingDto::getCost)
                .isEqualTo(15_000);
    }

    @Test
    @DisplayName("Create a spending with minus cost")
    void testCreateSpendingWithMinusCost() {
        Problem result = template.postForObject(DEFAULT_URL,
                new CreateSpendingCommand(LocalDate.of(2020, 6, 30), "haircut", -200), Problem.class);

        assertEquals(Status.BAD_REQUEST, result.getStatus());
    }

    @Test
    @DisplayName("Create a spending with empty name")
    void testCreateSpendingWithEmptyName() {
        Problem result = template.postForObject(DEFAULT_URL,
                new CreateSpendingCommand(LocalDate.of(2020, 6, 30), "   ", 200), Problem.class);

        assertEquals(Status.BAD_REQUEST, result.getStatus());
    }

    @Test
    @DisplayName("Not found spending")
    void notFoundSepndingTest() {
        Problem result = template.getForObject(DEFAULT_URL + "1", Problem.class);

        assertEquals(URI.create("expenditures/not-found"), result.getType());
        assertEquals(Status.NOT_FOUND, result.getStatus());
    }
}
