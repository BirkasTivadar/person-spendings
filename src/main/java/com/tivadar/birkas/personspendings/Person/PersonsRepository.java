package com.tivadar.birkas.personspendings.Person;

import com.tivadar.birkas.personspendings.Person.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonsRepository extends JpaRepository<Person, Long> {
}
