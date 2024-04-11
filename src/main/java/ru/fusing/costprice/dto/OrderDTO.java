package ru.fusing.costprice.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {
    private String order_name;
    private Long materialId;
    private List<ComponentDTO> components;
    private List<Long> instrumentIds;
    private List<Long> expensesIds;

    @Data
    public static class ComponentDTO {
        private Long componentId;
        private Long sizeId;

    }
}
