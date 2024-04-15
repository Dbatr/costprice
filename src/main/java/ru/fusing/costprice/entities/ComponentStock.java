package ru.fusing.costprice.entities;

import jakarta.persistence.*;
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

    @Column(name = "quantity")
    private Integer quantity;
}
