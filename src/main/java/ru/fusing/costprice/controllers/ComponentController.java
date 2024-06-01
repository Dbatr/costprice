package ru.fusing.costprice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.fusing.costprice.dto.Component_DTO;
import ru.fusing.costprice.dto.EntityResponse;
import ru.fusing.costprice.entities.Component;
import ru.fusing.costprice.services.ComponentService;

import java.util.List;
import java.util.Optional;

@Tag(name = "Операции с компонентами")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ComponentController {
    private final ComponentService componentService;

    //@PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Создание нового компонента", description = "Создает новый компонент с предоставленными данными, если роль - ADMIN")
    @PostMapping("/component")
    public ResponseEntity<Component> addComponent(@RequestBody Component_DTO componentDTO) {
        Component component = componentService.addComponent(componentDTO);
        return ResponseEntity.ok(component);
    }

    @Operation(summary = "Получение всех компонентов", description = "Возвращает список всех компонентов")
    @GetMapping("/components")
    public ResponseEntity<List<Component>> getAllComponents() {
        List<Component> components = componentService.getAllComponents();
        return ResponseEntity.ok(components);
    }

    @Operation(summary = "Получение компонента по ID", description = "Возвращает компонент по его ID")
    @GetMapping("/components/{id}")
    public ResponseEntity<EntityResponse<Component>> getComponentById(@PathVariable Long id) {
        Optional<Component> componentOptional = componentService.findComponentById(id);
        return componentOptional.map(component -> ResponseEntity.ok(new EntityResponse<>(component, "success")))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new EntityResponse<>("Component with ID " + id + " does not exist.")));
    }

    //@PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Удаление компонента", description = "Удаляет компонент по его ID, но удаление происходит успешно, если данный предмет не использовался в ранее созданном заказе, если роль - ADMIN")
    @DeleteMapping("/components/{id}")
    public ResponseEntity<String> deleteComponent(@PathVariable Long id) {
        boolean isDeleted = componentService.deleteComponent(id);
        if (isDeleted) {
            return ResponseEntity.ok("Component with ID " + id + " has been successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Component with ID " + id + " does not exist.");
        }
    }

    //@PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Обновление цены компонента", description = "Обновляет цену компонента по его ID и новой цене, если роль - ADMIN")
    @PutMapping("/components/{id}/price/{newPrice}")
    public ResponseEntity<EntityResponse<Component>> updateComponentPrice(@PathVariable Long id, @PathVariable Double newPrice) {
        Optional<Component> updatedComponent = componentService.updateComponentPrice(id, newPrice);
        return updatedComponent.map(component -> ResponseEntity.ok(new EntityResponse<>(component, "Component price updated successfully")))
                .orElseGet(() -> ResponseEntity.ok(new EntityResponse<>("Component not found with componentId " + id)));
    }
}
