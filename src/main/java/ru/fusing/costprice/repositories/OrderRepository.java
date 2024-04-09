package ru.fusing.costprice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fusing.costprice.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
