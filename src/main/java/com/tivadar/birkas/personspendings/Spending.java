package com.tivadar.birkas.personspendings;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "spendings")
public class Spending {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "spending_date")
    private LocalDate date;

    @Column(name = "product_or_service")
    private String productOrService;

    private int cost;

    @ManyToOne
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
    private Person person;

    public Spending(LocalDate date, String productOrService, int cost) {
        this.date = date;
        this.productOrService = productOrService;
        this.cost = cost;
    }

    public Spending(LocalDate date, String productOrService, int cost, Person person) {
        this.date = date;
        this.productOrService = productOrService;
        this.cost = cost;
//        person.addSpending(this);
    }
}
