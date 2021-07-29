package com.tivadar.birkas.personspendings;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonsRepository extends JpaRepository<Person, Long> {
}
