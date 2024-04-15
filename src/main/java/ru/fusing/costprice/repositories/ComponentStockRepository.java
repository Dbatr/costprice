package ru.fusing.costprice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fusing.costprice.entities.ComponentStock;

@Repository
public interface ComponentStockRepository extends JpaRepository<ComponentStock, Long> {
    ComponentStock findByComponentId(Long componentId);
}
