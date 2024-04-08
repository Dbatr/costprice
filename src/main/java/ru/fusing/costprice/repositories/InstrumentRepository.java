package ru.fusing.costprice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fusing.costprice.entities.Instrument;

@Repository
public interface InstrumentRepository extends JpaRepository<Instrument, Long> {
}
