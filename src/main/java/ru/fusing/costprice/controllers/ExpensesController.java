package ru.fusing.costprice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fusing.costprice.dto.EntityResponse;
import ru.fusing.costprice.dto.ExpensesDTO;
import ru.fusing.costprice.entities.Expenses;
import ru.fusing.costprice.services.ExpensesService;

import java.util.List;
import java.util.Optional;

@Tag(name = "Операции с расходами")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ExpensesController {
    private final ExpensesService expensesService;

    @Operation(summary = "Создание нового расхода", description = "Создает новый расход с предоставленными данными")
    @PostMapping("/addExpense")
    public ResponseEntity<Expenses> addExpense(@RequestBody ExpensesDTO expensesDTO) {
        Expenses expenses = expensesService.addExpense(expensesDTO);
        return ResponseEntity.ok(expenses);
    }

    @Operation(summary = "Получение всех расходов", description = "Возвращает список всех расходов")
    @GetMapping("/allExpenses")
    public ResponseEntity<List<Expenses>> getAllExpenses() {
        List<Expenses> expenses = expensesService.getAllExpenses();
        return ResponseEntity.ok(expenses);
    }

    @Operation(summary = "Получение расхода по ID", description = "Возвращает расход по его ID")
    @GetMapping("/expense/{id}")
    public ResponseEntity<EntityResponse<Expenses>> getExpenseById(@PathVariable Long id) {
        Optional<Expenses> expensesOptional = expensesService.findExpenseById(id);
        return expensesOptional.map(expenses -> ResponseEntity.ok(new EntityResponse<>(expenses, "success")))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new EntityResponse<>("Expense with ID " + id + " does not exist.")));
    }

    @Operation(summary = "Удаление расхода", description = "Удаляет расход по его ID")
    @DeleteMapping("/deleteExpense/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable Long id) {
        boolean isDeleted = expensesService.deleteExpense(id);
        if (isDeleted) {
            return ResponseEntity.ok("Expense with ID " + id + " has been successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense with ID " + id + " does not exist.");
        }
    }
}
