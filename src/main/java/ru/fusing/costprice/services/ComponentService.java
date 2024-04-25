package ru.fusing.costprice.services;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fusing.costprice.dto.Component_DTO;
import ru.fusing.costprice.entities.Component;
import ru.fusing.costprice.entities.ComponentStock;
import ru.fusing.costprice.repositories.ComponentRepository;
import ru.fusing.costprice.repositories.ComponentStockRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ComponentService {

    private final ComponentRepository componentRepository;
    private final ComponentStockRepository componentStockRepository;

    @Transactional
    public Component addComponent(Component_DTO componentDTO) {
        Component component = new Component();
        component.setType(componentDTO.getType());
        component.setPrice(componentDTO.getPrice());
        Component savedComponent = componentRepository.save(component);

        ComponentStock componentStock = new ComponentStock();
        componentStock.setComponent(savedComponent);
        componentStock.setQuantity(componentDTO.getQuantity());
        componentStockRepository.save(componentStock);

        return savedComponent;
    }

    public List<Component> getAllComponents() {
        return componentRepository.findAll();
    }

    public Optional<Component> findComponentById(Long id) {
        return componentRepository.findById(id);
    }

    @Transactional
    public boolean deleteComponent(Long id) {
        if (componentRepository.existsById(id)) {
            try {
                ComponentStock componentStock = componentStockRepository.findByComponentId(id);
                if (componentStock != null) {
                    componentStockRepository.delete(componentStock);
                }

                componentRepository.deleteById(id);
                return true;
            } catch (DataIntegrityViolationException e) {
                return false;
            }
        }
        return false;
    }

    public Optional<Component> updateComponentPrice(Long componentId, Double newPrice) {
        Optional<Component> optionalComponent = componentRepository.findById(componentId);
        if (optionalComponent.isPresent()) {
            Component component = optionalComponent.get();
            component.setPrice(newPrice);
            return Optional.of(componentRepository.save(component));
        } else {
            return Optional.empty();
        }
    }
}
