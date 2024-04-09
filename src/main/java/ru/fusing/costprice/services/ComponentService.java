package ru.fusing.costprice.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fusing.costprice.dto.Component_DTO;
import ru.fusing.costprice.entities.Component;
import ru.fusing.costprice.repositories.ComponentRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ComponentService {

    private final ComponentRepository componentRepository;

    public Component addComponent(Component_DTO componentDTO) {
        Component component = new Component();
        component.setType(componentDTO.getType());
        component.setPrice(componentDTO.getPrice());
        return componentRepository.save(component);
    }

    public List<Component> getAllComponents() {
        return componentRepository.findAll();
    }

    public Optional<Component> findComponentById(Long id) {
        return componentRepository.findById(id);
    }
}
