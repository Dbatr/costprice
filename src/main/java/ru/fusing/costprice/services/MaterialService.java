package ru.fusing.costprice.services;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.fusing.costprice.dto.MaterialDTO;
import ru.fusing.costprice.entities.Material;
import ru.fusing.costprice.repositories.MaterialRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class MaterialService {
    private final MaterialRepository materialRepository;

    public Material addMaterial(MaterialDTO materialDTO) {
        Material material = new Material();
        material.setGroup_name(materialDTO.getGroup_name());
        material.setType(materialDTO.getType());
        material.setPrice(materialDTO.getPrice());
        return materialRepository.save(material);
    }

    public List<Material> getAllMaterials() {
        return materialRepository.findAll();
    }

    public Optional<Material> findMaterialById(Long id) {
        return materialRepository.findById(id);
    }

    public boolean deleteMaterial(Long id) {
        if (materialRepository.existsById(id)) {
            try {
                materialRepository.deleteById(id);
                return true;
            } catch (DataIntegrityViolationException e) {
                System.err.println("Failed to delete material with ID " + id + ": " + e.getMessage());
                return false;
            }
        }
        return false;
    }

    public Optional<Material> updateMaterialPrice(Long materialId, Double newPrice) {
        Optional<Material> optionalMaterial = materialRepository.findById(materialId);
        if (optionalMaterial.isPresent()) {
            Material material = optionalMaterial.get();
            material.setPrice(newPrice);
            return Optional.of(materialRepository.save(material));
        } else {
            return Optional.empty();
        }
    }
}
