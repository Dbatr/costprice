package ru.fusing.costprice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.fusing.costprice.dto.EntityResponse;
import ru.fusing.costprice.dto.MaterialDTO;
import ru.fusing.costprice.entities.Material;
import ru.fusing.costprice.services.MaterialService;

import java.util.List;
import java.util.Optional;

@Tag(name = "Операции с материалами")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class MaterialController {
    private final MaterialService materialService;

    //@PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Создание нового материала", description = "Создает новый материал с предоставленными данными, если роль - ADMIN")
    @PostMapping("/material")
    public ResponseEntity<Material> addMaterial(@RequestBody MaterialDTO materialDTO) {
        Material material = materialService.addMaterial(materialDTO);
        return ResponseEntity.ok(material);
    }

    @Operation(summary = "Получение всех материалов", description = "Возвращает список всех материалов")
    @GetMapping("/materials")
    public ResponseEntity<List<Material>> getAllMaterials() {
        List<Material> materials = materialService.getAllMaterials();
        return ResponseEntity.ok(materials);
    }

    @Operation(summary = "Получение материала по ID", description = "Возвращает материал по его ID")
    @GetMapping("/materials/{id}")
    public ResponseEntity<EntityResponse<Material>> getMaterialById(@PathVariable Long id) {
        Optional<Material> materialOptional = materialService.findMaterialById(id);
        return materialOptional.map(material -> ResponseEntity.ok(new EntityResponse<>(material, "success")))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new EntityResponse<>("Material with ID " + id + " does not exist.")));
    }

    //@PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Удаление материала", description = "Удаляет материал по его ID, но удаление происходит успешно, если данный предмет не использовался в ранее созданном заказе, если роль - ADMIN")
    @DeleteMapping("/materials/{id}")
    public ResponseEntity<String> deleteMaterial(@PathVariable Long id) {
        boolean isDeleted = materialService.deleteMaterial(id);
        if (isDeleted) {
            return ResponseEntity.ok("Material with ID " + id + " has been successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Material with ID " + id + " does not exist or it used in order.");
        }
    }

    //@PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Обновление цены материала", description = "Обновляет цену материала по его ID и новой цене, если роль - ADMIN.")
    @PutMapping("/materials/{id}/price/{newPrice}")
    public ResponseEntity<EntityResponse<Material>> updateMaterialPrice(@PathVariable Long id, @PathVariable Double newPrice) {
        Optional<Material> updatedMaterial = materialService.updateMaterialPrice(id, newPrice);
        return updatedMaterial.map(material -> ResponseEntity.ok(new EntityResponse<>(material, "Material price updated successfully")))
                .orElseGet(() -> ResponseEntity.ok(new EntityResponse<>("Material not found with materialId " + id)));
    }
}
