package ru.fusing.costprice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "sizes")
@Data
public class Size {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sizeId;

    @DecimalMin(value = "0.0", inclusive = false, message = "The width must be greater than 0")
    @NotNull(message = "The width field can't be null")
    @Column(name = "width")
    private Double width;

    @DecimalMin(value = "0.0", inclusive = false, message = "The length must be greater than 0")
    @NotNull(message = "The length field can't be null")
    @Column(name = "length")
    private Double length;

    @DecimalMin(value = "0.0", inclusive = false, message = "The thickness must be greater than 0")
    @NotNull(message = "The thickness field can't be null")
    @Column(name = "thickness")
    private Double thickness;

    @DecimalMin(value = "0.0", inclusive = false, message = "The price must be greater than 0")
    @NotNull(message = "The price field can't be null")
    @Column(name = "price")
    private Double price;

}
