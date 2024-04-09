package ru.fusing.costprice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fusing.costprice.entities.OrderComponent;

@Repository
public interface OrderComponentRepository extends JpaRepository<OrderComponent, Long> {
}
