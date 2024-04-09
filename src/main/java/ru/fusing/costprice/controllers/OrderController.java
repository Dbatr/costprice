package ru.fusing.costprice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fusing.costprice.dto.OrderDTO;
import ru.fusing.costprice.dto.OrderResponse;
import ru.fusing.costprice.entities.Order;
import ru.fusing.costprice.services.OrderService;

import java.util.List;
import java.util.Optional;

@Tag(name = "Операции с заказами")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private OrderService orderService;

    @Operation(summary = "Создание нового заказа", description = "Создает новый заказ с предоставленными данными")
    @PostMapping("/addOrder")
    public ResponseEntity<Double> createOrder(@RequestBody OrderDTO orderDTO) {
        Order order = orderService.createOrder(orderDTO);
        return new ResponseEntity<>(order.getTotalPrice(), HttpStatus.CREATED);
    }

    @Operation(summary = "Получение всех заказов", description = "Возвращает список всех заказов")
    @GetMapping("/allOrders")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.findAllOrders();
        return ResponseEntity.ok(orders);
    }

    @Operation(summary = "Получение заказа по ID", description = "Возвращает заказ по его ID")
    @GetMapping("/order/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        Optional<Order> orderOptional = orderService.findOrderById(id);
        return orderOptional.map(order -> ResponseEntity.ok(new OrderResponse(order, "success")))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new OrderResponse("Order with ID " + id + " does not exist.")));
    }
}
