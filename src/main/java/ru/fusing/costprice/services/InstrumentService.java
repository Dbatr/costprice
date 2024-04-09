package ru.fusing.costprice.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fusing.costprice.dto.InstrumentDTO;
import ru.fusing.costprice.entities.Instrument;
import ru.fusing.costprice.repositories.InstrumentRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class InstrumentService {
    private final InstrumentRepository instrumentRepository;


    public Instrument addInstrument(InstrumentDTO instrumentDTO) {
        Instrument instrument = new Instrument();
        instrument.setName(instrumentDTO.getName());
        instrument.setPrice(instrumentDTO.getPrice());
        return instrumentRepository.save(instrument);
    }

    public List<Instrument> getAllInstruments() {
        return instrumentRepository.findAll();
    }

    public Optional<Instrument> findInstrumentById(Long id) {
        return instrumentRepository.findById(id);
    }

    public boolean deleteInstrument(Long id) {
        if (instrumentRepository.existsById(id)) {
            instrumentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
