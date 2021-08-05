package com.tivadar.birkas.personspendings.spending;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpendituresRepository extends JpaRepository<Spending, Long> {

    List<Spending> findAllByPersonId(Long id);

    List<Spending> findAllByPersonIdAndCostBetween(Long id, int min, int max);

    @Query("select SUM(s.cost) from Spending s where YEAR(spendingDate) = :numOfYear AND MONTH(spendingDate) = :numOfMonth")
    Integer sumCostsOfMonth(int numOfYear, int numOfMonth);
}
