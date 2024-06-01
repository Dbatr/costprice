package ru.fusing.costprice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.fusing.costprice.dto.EntityResponse;
import ru.fusing.costprice.dto.InstrumentDTO;
import ru.fusing.costprice.entities.Instrument;
import ru.fusing.costprice.services.InstrumentService;

import java.util.List;
import java.util.Optional;

@Tag(name = "Операции с инструментами")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class InstrumentController {
    private final InstrumentService instrumentService;

    //@PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Создание нового инструмента", description = "Создает новый инструмент с предоставленными данными, если роль - ADMIN")
    @PostMapping("/instrument")
    public ResponseEntity<Instrument> addInstrument(@RequestBody InstrumentDTO instrumentDTO) {
        Instrument instrument = instrumentService.addInstrument(instrumentDTO);
        return ResponseEntity.ok(instrument);
    }

    @Operation(summary = "Получение всех инструментов", description = "Возвращает список всех инструментов")
    @GetMapping("/instruments")
    public ResponseEntity<List<Instrument>> getAllInstruments() {
        List<Instrument> instruments = instrumentService.getAllInstruments();
        return ResponseEntity.ok(instruments);
    }

    @Operation(summary = "Получение инструмента по ID", description = "Возвращает инструмент по его ID")
    @GetMapping("/instruments/{id}")
    public ResponseEntity<EntityResponse<Instrument>> getInstrumentById(@PathVariable Long id) {
        Optional<Instrument> instrumentOptional = instrumentService.findInstrumentById(id);
        return instrumentOptional.map(instrument -> ResponseEntity.ok(new EntityResponse<>(instrument, "success")))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new EntityResponse<>("Instrument with ID " + id + " does not exist.")));
    }

    //@PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Удаление инструмента", description = "Удаляет инструмент по его ID, но удаление происходит успешно, если данный предмет не использовался в ранее созданном заказе, если роль - ADMIN")
    @DeleteMapping("/instruments/{id}")
    public ResponseEntity<String> deleteInstrument(@PathVariable Long id) {
        boolean isDeleted = instrumentService.deleteInstrument(id);
        if (isDeleted) {
            return ResponseEntity.ok("Instrument with ID " + id + " has been successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Instrument with ID " + id + " does not exist or it used in order.");
        }
    }

    //@PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Обновление цены инструмента", description = "Обновляет цену инструмента по его ID и новой цене, если роль - ADMIN.")
    @PutMapping("/instruments/{id}/price/{newPrice}")
    public ResponseEntity<EntityResponse<Instrument>> updateInstrumentPrice(@PathVariable Long id, @PathVariable Double newPrice) {
        Optional<Instrument> updatedInstrument = instrumentService.updateInstrumentPrice(id, newPrice);
        return updatedInstrument.map(instrument -> ResponseEntity.ok(new EntityResponse<>(instrument, "Instrument price updated successfully")))
                .orElseGet(() -> ResponseEntity.ok(new EntityResponse<>("Instrument not found with instrumentId " + id)));
    }
}
