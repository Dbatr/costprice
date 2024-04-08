package ru.fusing.costprice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fusing.costprice.entities.Component;

@Repository
public interface ComponentRepository extends JpaRepository<Component, Long> {

}
