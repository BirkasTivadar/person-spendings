package com.tivadar.birkas.personspendings.person;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonsRepository extends JpaRepository<Person, Long> {

    Boolean existsPersonBySocialSecurityNumber(String socialSecurityNumber);

    Person findPersonBySocialSecurityNumber(String socialSecurityNumber);

}
