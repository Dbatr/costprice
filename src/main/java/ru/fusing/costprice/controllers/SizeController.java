package ru.fusing.costprice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.fusing.costprice.dto.EntityResponse;
import ru.fusing.costprice.dto.SizeDTO;
import ru.fusing.costprice.entities.Size;
import ru.fusing.costprice.services.SizeService;

import java.util.List;
import java.util.Optional;

@Tag(name = "Операции с размерами")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class SizeController {
    private final SizeService sizeService;

    //@PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Создание нового размера", description = "Создает новый размер с предоставленными данными, если роль - ADMIN")
    @PostMapping("/size")
    public ResponseEntity<Size> addSize(@RequestBody SizeDTO sizeDTO) {
        Size size = sizeService.addSize(sizeDTO);
        return ResponseEntity.ok(size);
    }

    @Operation(summary = "Получение всех размеров", description = "Возвращает список всех размеров")
    @GetMapping("/sizes")
    public ResponseEntity<List<Size>> getAllSizes() {
        List<Size> sizes = sizeService.getAllSizes();
        return ResponseEntity.ok(sizes);
    }

    @Operation(summary = "Получение размера по ID", description = "Возвращает размер по его ID")
    @GetMapping("/sizes/{id}")
    public ResponseEntity<EntityResponse<Size>> getSizeById(@PathVariable Long id) {
        Optional<Size> sizeOptional = sizeService.findSizeById(id);
        return sizeOptional.map(size -> ResponseEntity.ok(new EntityResponse<>(size, "success")))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new EntityResponse<>("Size with ID " + id + " does not exist.")));
    }

    //@PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Удаление размера", description = "Удаляет размер по его ID, но удаление происходит успешно, если данный предмет не использовался в ранее созданном заказе, если роль - ADMIN")
    @DeleteMapping("/sizes/{id}")
    public ResponseEntity<String> deleteSize(@PathVariable Long id) {
        boolean isDeleted = sizeService.deleteSize(id);
        if (isDeleted) {
            return ResponseEntity.ok("Size with ID " + id + " has been successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Size with ID " + id + " does not exist or it used in order.");
        }
    }

    //@PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Обновление цены размера", description = "Обновляет цену размера по его ID и новой цене, если роль - ADMIN.")
    @PutMapping("/sizes/{id}/price/{newPrice}")
    public ResponseEntity<EntityResponse<Size>> updateSizePrice(@PathVariable Long id, @PathVariable Double newPrice) {
        Optional<Size> updatedSize = sizeService.updateSizePrice(id, newPrice);
        return updatedSize.map(size -> ResponseEntity.ok(new EntityResponse<>(size, "Size price updated successfully")))
                .orElseGet(() -> ResponseEntity.ok(new EntityResponse<>("Size not found with sizeId " + id)));
    }
}
