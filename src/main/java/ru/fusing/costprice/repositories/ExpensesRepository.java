package ru.fusing.costprice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fusing.costprice.entities.Expenses;

@Repository
public interface ExpensesRepository extends JpaRepository<Expenses, Long> {
}
