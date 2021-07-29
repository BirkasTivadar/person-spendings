package com.tivadar.birkas.personspendings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "persons")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "social_security_number")
    private String socialSecurityNumber;

    @Column(name = "person_name")
    private String name;

    public Person(String socialSecurityNumber, String name) {
        this.socialSecurityNumber = socialSecurityNumber;
        this.name = name;
    }
}
