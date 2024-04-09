package ru.fusing.costprice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "components")
@Data
public class Component {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The type field can't be blank")
    private String type;

    @NotNull(message = "The price field can't be null")
    private Double price;

}
