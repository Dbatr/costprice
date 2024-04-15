package ru.fusing.costprice.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fusing.costprice.entities.ComponentStock;
import ru.fusing.costprice.repositories.ComponentStockRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ComponentStockService {
    private final ComponentStockRepository componentStockRepository;

    public List<ComponentStock> getAllComponentStocks() {
        return componentStockRepository.findAll();
    }

    public Optional<ComponentStock> getComponentStockById(Long id) {
        return componentStockRepository.findById(id);

    }

    public ComponentStock getComponentStocksByComponentId(Long componentId) {
        return componentStockRepository.findByComponentId(componentId);
    }

    public Optional<ComponentStock> updateComponentStockQuantity(Long componentId, Integer newQuantity) {
        Optional<ComponentStock> optionalComponentStock = Optional.ofNullable(componentStockRepository.findByComponentId(componentId));
        if (optionalComponentStock.isPresent()) {
            ComponentStock componentStock = optionalComponentStock.get();
            componentStock.setQuantity(newQuantity);
            return Optional.of(componentStockRepository.save(componentStock));
        } else {
            return Optional.empty();
        }
    }
}
