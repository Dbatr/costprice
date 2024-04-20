package ru.fusing.costprice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.fusing.costprice.entities.OrderExpenses;

@Repository
public interface OrderExpensesRepository extends JpaRepository<OrderExpenses, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM OrderExpenses oe WHERE oe.order.orderId = :orderId")
    void deleteByOrderId(Long orderId);
}