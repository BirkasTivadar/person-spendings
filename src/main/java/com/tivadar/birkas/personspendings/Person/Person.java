package com.tivadar.birkas.personspendings.Person;

import com.tivadar.birkas.personspendings.Spending.Spending;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "persons")
public class Person {

    public static final int SSN_LENGTH = 9;
    public static final int NAME_LENGTH_MIN = 2;
    public static final int NAME_LENGTH_MAX = 50;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "social_security_number")
    private String socialSecurityNumber;

    @Column(name = "person_name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    private List<Spending> spendingList;

    public Person(String socialSecurityNumber, String name) {
        this.socialSecurityNumber = socialSecurityNumber;
        this.name = name;
        spendingList = new ArrayList<>();
    }

    public void addSpending(Spending spending) {
        spendingList.add(spending);
        spending.setPerson(this);
    }

}
