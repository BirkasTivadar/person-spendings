package com.tivadar.birkas.personspendings;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpendingsRepository extends JpaRepository<Spending, Long> {
}