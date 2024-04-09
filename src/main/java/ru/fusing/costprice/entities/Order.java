package ru.fusing.costprice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @NotBlank(message = "The order name field can't be blank")
    private String order_name;

    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    @JsonManagedReference
    private List<OrderComponent> components;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    @JsonManagedReference
    private List<OrderInstrument> instruments; // Добавлена связь с инструментами

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
