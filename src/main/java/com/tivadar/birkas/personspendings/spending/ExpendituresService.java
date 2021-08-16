package com.tivadar.birkas.personspendings.spending;

import com.tivadar.birkas.personspendings.person.Person;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ExpendituresService {

    private static final String NOT_FOUND_SPENDING_WITH_ID = "Not found person with id: ";

    private ModelMapper modelMapper;

    private ExpendituresRepository repository;

    public List<SpendingDTO> getExpenditures() {
        return repository.findAll().stream()
                .map(sp -> modelMapper.map(sp, SpendingDTO.class))
                .toList();
    }

    public SpendingDTO getSpendingById(long id) {
        Spending spending = repository.findById(id).orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_SPENDING_WITH_ID + id));
        return modelMapper.map(spending, SpendingDTO.class);
    }

    public SpendingDTO createSpending(CreateSpendingCommand command) {
        Spending spending = new Spending(command.getSpendingDate(), command.getProductOrService(), command.getCost());
        repository.save(spending);
        return modelMapper.map(spending, SpendingDTO.class);
    }

    public List<SpendingDTO> getExpendituresByPersonIdAndCostBetween(Long id, Integer min, Integer max) {
        if (min == null) min = 0;
        if (max == null) max = Integer.MAX_VALUE;
        return repository.findAllByPersonIdAndCostBetween(id, min, max).stream()
                .map(sp -> modelMapper.map(sp, SpendingDTO.class))
                .toList();
    }

    public Integer getSumCostsOfMonth(Integer numOfYear, Integer numOfMonth) {

        if (numOfMonth == null) {
            return repository.sumCostsOfYear(numOfYear);
        } else {
            return repository.sumCostsOfMonth(numOfYear, numOfMonth);
        }

    }

    @Transactional
    public SpendingDTO changeSpendingCost(long id, ChangeSpendingCostCommand command) {
        Spending spending = repository.findById(id).orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_SPENDING_WITH_ID + id));
        setSumCosts(command, spending);
        spending.setCost(command.getCost());
        return modelMapper.map(spending, SpendingDTO.class);
    }

    private void setSumCosts(ChangeSpendingCostCommand command, Spending spending) {
        int minusCost = spending.getCost();
        int newCost = command.getCost();
        if (spending.getPerson() != null) {
            Person person = spending.getPerson();
            long cost = person.getSumCosts();
            cost -= minusCost;
            cost += newCost;
            person.setSumCosts(cost);
        }
    }

    public void deleteSpending(long id) {
        repository.deleteById(id);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public List<SpendingDTO> getExpendituresByPersonId(long id) {
        return repository.findAllByPersonId(id).stream()
                .map(sp -> modelMapper.map(sp, SpendingDTO.class))
                .toList();
    }
}
