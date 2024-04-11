package ru.fusing.costprice.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fusing.costprice.dto.ExpensesDTO;
import ru.fusing.costprice.entities.Expenses;
import ru.fusing.costprice.repositories.ExpensesRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ExpensesService {
    private final ExpensesRepository expensesRepository;

    public Expenses addExpense(ExpensesDTO expensesDTO) {
        Expenses expenses = new Expenses();
        expenses.setType(expensesDTO.getType());
        expenses.setPrice(expensesDTO.getPrice());
        return expensesRepository.save(expenses);
    }

    public List<Expenses> getAllExpenses() {
        return expensesRepository.findAll();
    }

    public Optional<Expenses> findExpenseById(Long id) {
        return expensesRepository.findById(id);
    }

    public boolean deleteExpense(Long id) {
        if (expensesRepository.existsById(id)) {
            expensesRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
