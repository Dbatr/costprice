package ru.fusing.costprice.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fusing.costprice.dto.OrderDTO;
import ru.fusing.costprice.entities.*;
import ru.fusing.costprice.repositories.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class OrderService {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Autowired
    private OrderRepository orderRepository;


    public Order createOrder(OrderDTO orderDTO) {
        Material material = materialRepository.findById(orderDTO.getMaterialId())
                .orElseThrow(() -> new RuntimeException("Material not found"));

        Order order = new Order();

        order.setOrder_name(orderDTO.getOrder_name());
        order.setMaterial(material);

        AtomicReference<Double> totalPrice = new AtomicReference<>(material.getPrice());

        List<OrderComponent> orderComponents = orderDTO.getComponents().stream().map(componentDTO -> {
            Component component = componentRepository.findById(componentDTO.getComponentId())
                    .orElseThrow(() -> new RuntimeException("Component not found"));
            Size size = sizeRepository.findById(componentDTO.getSizeId())
                    .orElseThrow(() -> new RuntimeException("Size not found"));

            Double componentPrice = component.getPrice() * size.getPrice();
            totalPrice.updateAndGet(v -> v * componentPrice);

            OrderComponent orderComponent = new OrderComponent();
            orderComponent.setComponent(component);
            orderComponent.setSize(size);
            orderComponent.setOrder(order);
            return orderComponent;
        }).collect(Collectors.toList());

        // Обработка информации об инструментах
        List<OrderInstrument> orderInstruments = orderDTO.getInstrumentIds().stream().map(instrumentId -> {
            Instrument instrument = instrumentRepository.findById(instrumentId)
                    .orElseThrow(() -> new RuntimeException("Instrument not found"));
            totalPrice.updateAndGet(v -> v + instrument.getPrice());

            OrderInstrument orderInstrument = new OrderInstrument();
            orderInstrument.setInstrument(instrument);
            orderInstrument.setOrder(order);
            return orderInstrument;
        }).collect(Collectors.toList());

        order.setComponents(orderComponents);
        order.setInstruments(orderInstruments); // Добавлено поле для инструментов
        order.setTotalPrice(totalPrice.get());
        return orderRepository.save(order);
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> findOrderById(Long id) {
        return orderRepository.findById(id);
    }
}
