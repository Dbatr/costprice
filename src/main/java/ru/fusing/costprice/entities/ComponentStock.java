package ru.fusing.costprice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "component_stocks")
@Data
public class ComponentStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "component_id", nullable = false)
    private Component component;

    @Min(value = 1, message = "The quantity must be greater than 0")
    @NotNull(message = "The quantity field can't be null")
    @Column(name = "quantity")
    private Integer quantity;
}
