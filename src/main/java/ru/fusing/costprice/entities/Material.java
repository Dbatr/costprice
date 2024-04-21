package ru.fusing.costprice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "materials")
@Data
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The group_name field can't be blank")
    @Column(name = "group_name")
    private String group_name;

    @NotBlank(message = "The type field can't be blank")
    @Column(name = "type")
    private String type;

    @DecimalMin(value = "0.0", inclusive = false, message = "The price must be greater than 0")
    @NotNull(message = "The price field can't be null")
    @Column(name = "price")
    private Double price;

}
