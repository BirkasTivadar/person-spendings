package com.tivadar.birkas.personspendings.Person;

import com.tivadar.birkas.personspendings.Spending.Spending;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonsRepository extends JpaRepository<Person, Long> {

    Boolean existsPersonBySocialSecurityNumber(String socialSecurityNumber);

    Person findPersonBySocialSecurityNumber(String socialSecurityNumber);




//    @Query("select p.id from Person p where p.socialSecurityNumber = :ssn")
//    Long findIdBySSN(String ssn);

}
