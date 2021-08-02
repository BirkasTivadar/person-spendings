package com.tivadar.birkas.personspendings.Spending;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpendituresRepository extends JpaRepository<Spending, Long> {

    List<Spending> findAllByPerson_Id(Long id);
}
