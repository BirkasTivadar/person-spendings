package com.tivadar.birkas.personspendings.Spending;

import com.tivadar.birkas.personspendings.Person.Person;
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

    public List<SpendingDto> getExpenditures() {
        return repository.findAll().stream()
                .map(sp -> modelMapper.map(sp, SpendingDto.class))
                .toList();
    }

    public SpendingDto getSpendingById(long id) {
        Spending spending = repository.findById(id).orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_SPENDING_WITH_ID + id));
        return modelMapper.map(spending, SpendingDto.class);
    }

    public SpendingDto createSpending(CreateSpendingCommand command) {
        Spending spending = new Spending(command.getSpendingDate(), command.getProductOrService(), command.getCost());
        repository.save(spending);
        return modelMapper.map(spending, SpendingDto.class);
    }

    public List<SpendingDto> getExpendituresByPerson_IdAndCostBetween(Long id, int min, int max) {
        return repository.findAllByPerson_IdAndCostBetween(id, min, max).stream()
                .map(sp -> modelMapper.map(sp, SpendingDto.class))
                .toList();
    }

    public Integer getSumCostsOfMonth(int numOfYear, int numOfMonth){
        return repository.sumCostsOfMonth(numOfYear, numOfMonth);
    }

    @Transactional
    public SpendingDto changeSpendingCost(long id, ChangeSpendingCostCommand command) {
        Spending spending = repository.findById(id).orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_SPENDING_WITH_ID + id));
        setSumCosts(command, spending);
        spending.setCost(command.getCost());
        return modelMapper.map(spending, SpendingDto.class);
    }

    private void setSumCosts(ChangeSpendingCostCommand command, Spending spending) {
        int minusCost = spending.getCost();
        int newCost = command.getCost();
        if(spending.getPerson() != null){
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

    public List<SpendingDto> getExpendituresByPersonId(long id) {
        return repository.findAllByPerson_Id(id).stream()
                .map(sp -> modelMapper.map(sp, SpendingDto.class))
                .toList();
    }
}
