package ru.fusing.costprice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Operation(summary = "Создание нового инструмента", description = "Создает новый инструмент с предоставленными данными")
    @PostMapping("/addInstrument")
    public ResponseEntity<Instrument> addInstrument(@RequestBody InstrumentDTO instrumentDTO) {
        Instrument instrument = instrumentService.addInstrument(instrumentDTO);
        return ResponseEntity.ok(instrument);
    }

    @Operation(summary = "Получение всех инструментов", description = "Возвращает список всех инструментов")
    @GetMapping("/allInstruments")
    public ResponseEntity<List<Instrument>> getAllInstruments() {
        List<Instrument> instruments = instrumentService.getAllInstruments();
        return ResponseEntity.ok(instruments);
    }

    @Operation(summary = "Получение инструмента по ID", description = "Возвращает инструмент по его ID")
    @GetMapping("/instrument/{id}")
    public ResponseEntity<EntityResponse<Instrument>> getInstrumentById(@PathVariable Long id) {
        Optional<Instrument> instrumentOptional = instrumentService.findInstrumentById(id);
        return instrumentOptional.map(instrument -> ResponseEntity.ok(new EntityResponse<>(instrument, "success")))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new EntityResponse<>("Instrument with ID " + id + " does not exist.")));
    }

    @Operation(summary = "Удаление инструмента", description = "Удаляет инструмент по его ID, но удаление происходит успешно, если данный предмет не использовался в ранее созданном заказе")
    @DeleteMapping("/deleteInstrument/{id}")
    public ResponseEntity<String> deleteInstrument(@PathVariable Long id) {
        boolean isDeleted = instrumentService.deleteInstrument(id);
        if (isDeleted) {
            return ResponseEntity.ok("Instrument with ID " + id + " has been successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Instrument with ID " + id + " does not exist or it used in order.");
        }
    }

    @Operation(summary = "Обновление цены инструмента", description = "Обновляет цену инструмента по его ID и новой цене.")
    @PutMapping("/instrument/{instrumentId}/price/{newPrice}")
    public ResponseEntity<EntityResponse<Instrument>> updateInstrumentPrice(@PathVariable Long instrumentId, @PathVariable Double newPrice) {
        Optional<Instrument> updatedInstrument = instrumentService.updateInstrumentPrice(instrumentId, newPrice);
        return updatedInstrument.map(instrument -> ResponseEntity.ok(new EntityResponse<>(instrument, "Instrument price updated successfully")))
                .orElseGet(() -> ResponseEntity.ok(new EntityResponse<>("Instrument not found with instrumentId " + instrumentId)));
    }
}
