package ru.fusing.costprice.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {
    private Long materialId;
    private List<ComponentDTO> components;
    private List<Long> instrumentIds;

    @Data
    public static class ComponentDTO {
        private Long componentId;
        private Long sizeId;

    }
}
