package ru.fusing.costprice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "instrument")
@Data
public class Instrument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long instrumentId;

    @NotBlank(message = "The name field can't be blank")
    private String name;

    @NotNull(message = "The price field can't be null")
    private Double price;
}
