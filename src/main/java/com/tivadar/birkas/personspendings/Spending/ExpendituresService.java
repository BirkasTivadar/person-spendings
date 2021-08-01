package com.tivadar.birkas.personspendings.Spending;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ExpendituresService {

    private ModelMapper modelMapper;

    private ExpendituresRepository repository;

    public List<SpendingDto> getExpenditures() {
        return repository.findAll().stream()
                .map(sp -> modelMapper.map(sp, SpendingDto.class))
                .toList();
    }

    public SpendingDto getSpendingById(long id) {
        Spending spending = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found spending with id: " + id));
        return modelMapper.map(spending, SpendingDto.class);
    }

    public SpendingDto createSpending(CreateSpendingCommand command) {
        Spending spending = new Spending(command.getDate(), command.getProductOrService(), command.getCost());
        repository.save(spending);
        return modelMapper.map(spending, SpendingDto.class);
    }

    @Transactional
    public SpendingDto changeSpendingCost(long id, ChangeSpendingCostCommand command) {
        Spending spending = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found spending with id: " + id));
        spending.setCost(command.getCost());
        return modelMapper.map(spending, SpendingDto.class);
    }

    public void deleteSpending(long id) {
        repository.deleteById(id);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
