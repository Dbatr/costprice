package ru.fusing.costprice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fusing.costprice.entities.OrderInstrument;

@Repository
public interface OrderInstrumentRepository extends JpaRepository<OrderInstrument, Long> {
}
