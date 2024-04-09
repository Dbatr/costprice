package ru.fusing.costprice.dto;

import lombok.Getter;
import ru.fusing.costprice.entities.Component;

@Getter
public class ComponentResponse {
    private Component component;
    private final String message;

    public ComponentResponse(Component component, String message) {
        this.component = component;
        this.message = message;
    }
    public ComponentResponse(String message) {
        this.message = message;
    }
}
