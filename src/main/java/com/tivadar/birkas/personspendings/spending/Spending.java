package com.tivadar.birkas.personspendings.spending;

import com.tivadar.birkas.personspendings.person.Person;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "expenditures")
public class Spending {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "spending_date")
    private LocalDate spendingDate;

    @Column(name = "product_or_service")
    private String productOrService;

    private int cost;

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Person person;

    public Spending(LocalDate spendingDate, String productOrService, int cost) {
        this.spendingDate = spendingDate;
        this.productOrService = productOrService;
        this.cost = cost;
    }

    public Spending(LocalDate spendingDate, String productOrService, int cost, Person person) {
        this.spendingDate = spendingDate;
        this.productOrService = productOrService;
        this.cost = cost;
        this.person = person;
    }
}







