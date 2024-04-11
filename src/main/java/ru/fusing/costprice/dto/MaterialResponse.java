package ru.fusing.costprice.dto;

import lombok.Getter;
import ru.fusing.costprice.entities.Material;

@Getter
public class MaterialResponse {
    private Material material;
    private final String message;

    public MaterialResponse(Material material, String message) {
        this.material = material;
        this.message = message;
    }
    public MaterialResponse(String message) {
        this.message = message;
    }
}
