package ru.fusing.costprice.dto;

import lombok.Getter;
import ru.fusing.costprice.entities.Order;


@Getter
public class OrderResponse {
    private Order order;
    private final String message;

    public OrderResponse(Order order, String message) {
        this.order = order;
        this.message = message;
    }

    public OrderResponse(String message) {
        this.message = message;
    }

}
