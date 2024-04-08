package ru.fusing.costprice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fusing.costprice.entities.Size;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {
}
