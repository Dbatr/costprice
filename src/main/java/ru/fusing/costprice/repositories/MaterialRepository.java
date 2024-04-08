package ru.fusing.costprice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fusing.costprice.entities.Material;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
}
