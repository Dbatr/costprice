package ru.fusing.costprice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.fusing.costprice.entities.OrderComponent;

@Repository
public interface OrderComponentRepository extends JpaRepository<OrderComponent, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM OrderComponent oc WHERE oc.order.orderId = :orderId")
    void deleteByOrderId(Long orderId);
}
