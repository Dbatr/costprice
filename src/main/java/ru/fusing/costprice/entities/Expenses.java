package ru.fusing.costprice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "expenses")
@Data
public class Expenses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expensesId;

    @NotBlank(message = "The type field can't be blank")
    private String type;

    @DecimalMin(value = "0.0", inclusive = false, message = "The price must be greater than 0")
    @NotNull(message = "The price field can't be null")
    private Double price;
}
