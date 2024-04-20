package ru.fusing.costprice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.fusing.costprice.entities.OrderInstrument;

@Repository
public interface OrderInstrumentRepository extends JpaRepository<OrderInstrument, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM OrderInstrument oi WHERE oi.order.orderId = :orderId")
    void deleteByOrderId(Long orderId);
}
