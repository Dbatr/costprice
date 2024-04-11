package ru.fusing.costprice.dto;

import lombok.Getter;

@Getter
public class EntityResponse<T> {
    private T entity;
    private final String message;

    public EntityResponse(T entity, String message) {
        this.entity = entity;
        this.message = message;
    }

    public EntityResponse(String message) {
        this.message = message;
    }
}
