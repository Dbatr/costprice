package ru.fusing.costprice.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fusing.costprice.dto.SizeDTO;
import ru.fusing.costprice.entities.Size;
import ru.fusing.costprice.repositories.SizeRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SizeService {
    private final SizeRepository sizeRepository;

    public Size addSize(SizeDTO sizeDTO) {
        Size size = new Size();
        size.setWidth(sizeDTO.getWidth());
        size.setLength(sizeDTO.getLength());
        size.setThickness(sizeDTO.getThickness());
        size.setPrice(sizeDTO.getPrice());
        return sizeRepository.save(size);
    }

    public List<Size> getAllSizes() {
        return sizeRepository.findAll();
    }

    public Optional<Size> findSizeById(Long id) {
        return sizeRepository.findById(id);
    }

    public boolean deleteSize(Long id) {
        if (sizeRepository.existsById(id)) {
            sizeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
