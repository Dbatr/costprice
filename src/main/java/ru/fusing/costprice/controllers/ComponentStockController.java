package ru.fusing.costprice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.fusing.costprice.dto.EntityResponse;
import ru.fusing.costprice.entities.ComponentStock;
import ru.fusing.costprice.services.ComponentStockService;

import java.util.List;
import java.util.Optional;

@Tag(name = "Операции с количеством компонентов")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ComponentStockController {
    private final ComponentStockService componentStockService;

    @Operation(summary = "Получение всех записей ComponentStock", description = "Возвращает список всех записей ComponentStock.")
    @GetMapping("/component-stocks")
    public ResponseEntity<List<ComponentStock>> getAllComponentStocks() {
        List<ComponentStock> componentStocks = componentStockService.getAllComponentStocks();
        return ResponseEntity.ok(componentStocks);
    }

    @Operation(summary = "Получение ComponentStock по ID", description = "Возвращает ComponentStock по его ID.")
    @GetMapping("/component-stocks/{id}")
    public ResponseEntity<EntityResponse<ComponentStock>> getComponentStockById(@PathVariable Long id) {
        Optional<ComponentStock> componentStock = componentStockService.getComponentStockById(id);
        return componentStock.map(stock -> ResponseEntity.ok(new EntityResponse<>(stock, "ComponentStock found")))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new EntityResponse<>("ComponentStock not found with id " + id)));
    }

    @Operation(summary = "Получение ComponentStock по ID компонента", description = "Возвращает ComponentStock по ID компонента.")
    @GetMapping("/component-stocks/component/{componentId}")
    public ResponseEntity<EntityResponse<ComponentStock>> getComponentStocksByComponentId(@PathVariable Long componentId) {
        Optional<ComponentStock> componentStock = Optional.ofNullable(componentStockService.getComponentStocksByComponentId(componentId));
        return componentStock.map(stock -> ResponseEntity.ok(new EntityResponse<>(stock, "ComponentStock found"))).orElseGet(() -> ResponseEntity.ok(new EntityResponse<>("ComponentStock not found with componentId " + componentId)));
    }

    //@PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Обновление количества компонента", description = "Обновляет количество компонента по его ID и новому количеству, если роль - ADMIN.")
    @PutMapping("/component-stocks/component/{id}/quantity/{newQuantity}")
    public ResponseEntity<EntityResponse<ComponentStock>> updateComponentStockQuantity(@PathVariable Long id, @PathVariable Integer newQuantity) {
        Optional<ComponentStock> updatedComponentStock = componentStockService.updateComponentStockQuantity(id, newQuantity);
        return updatedComponentStock.map(componentStock -> ResponseEntity.ok(new EntityResponse<>(componentStock, "ComponentStock quantity updated successfully")))
                .orElseGet(() -> ResponseEntity.ok(new EntityResponse<>("ComponentStock not found with componentId " + id)));
    }
}
