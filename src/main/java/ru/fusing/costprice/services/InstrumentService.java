package ru.fusing.costprice.services;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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
            try {
                instrumentRepository.deleteById(id);
                return true;
            } catch (DataIntegrityViolationException e) {
                System.err.println("Failed to delete instrument with ID " + id + ": " + e.getMessage());
                return false;
            }
        }
        return false;
    }

    public Optional<Instrument> updateInstrumentPrice(Long instrumentId, Double newPrice) {
        Optional<Instrument> optionalInstrument = instrumentRepository.findById(instrumentId);
        if (optionalInstrument.isPresent()) {
            Instrument instrument = optionalInstrument.get();
            instrument.setPrice(newPrice);
            return Optional.of(instrumentRepository.save(instrument));
        } else {
            return Optional.empty();
        }
    }
}
