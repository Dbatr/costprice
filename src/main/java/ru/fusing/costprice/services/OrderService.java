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
    private ExpensesRepository expensesRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ComponentStockRepository componentStockRepository;


    public Order createOrder(OrderDTO orderDTO) {
        Order order = new Order();

        order.setOrder_name(orderDTO.getOrder_name());

        Material material = materialRepository.findById(orderDTO.getMaterialId())
                .orElseThrow(() -> new RuntimeException("Material not found"));
        order.setMaterial(material);

        AtomicReference<Double> totalPrice = new AtomicReference<>(material.getPrice());

        List<OrderComponent> orderComponents = getOrderComponents(orderDTO, totalPrice, order);
        List<OrderInstrument> orderInstruments = getOrderInstruments(orderDTO, totalPrice, order);
        List<OrderExpenses> orderExpenses = getOrderExpenses(orderDTO, totalPrice, order);

        order.setComponents(orderComponents);
        order.setInstruments(orderInstruments);
        order.setExpenses(orderExpenses);
        order.setTotalPrice(totalPrice.get());
        return orderRepository.save(order);
    }

    private List<OrderExpenses> getOrderExpenses(OrderDTO orderDTO, AtomicReference<Double> totalPrice, Order order) {
        return orderDTO.getExpensesIds().stream().map(expensesId -> {
            Expenses expenses = expensesRepository.findById(expensesId)
                    .orElseThrow(() -> new RuntimeException("Expenses not found"));
            totalPrice.updateAndGet(v -> v + expenses.getPrice());

            OrderExpenses orderExpense = new OrderExpenses();
            orderExpense.setExpenses(expenses);
            orderExpense.setOrder(order);
            return orderExpense;
        }).collect(Collectors.toList());
    }

    private List<OrderInstrument> getOrderInstruments(OrderDTO orderDTO, AtomicReference<Double> totalPrice, Order order) {
        return orderDTO.getInstrumentIds().stream().map(instrumentId -> {
            Instrument instrument = instrumentRepository.findById(instrumentId)
                    .orElseThrow(() -> new RuntimeException("Instrument not found"));
            totalPrice.updateAndGet(v -> v + instrument.getPrice());

            OrderInstrument orderInstrument = new OrderInstrument();
            orderInstrument.setInstrument(instrument);
            orderInstrument.setOrder(order);
            return orderInstrument;
        }).collect(Collectors.toList());
    }

    private List<OrderComponent> getOrderComponents(OrderDTO orderDTO, AtomicReference<Double> totalPrice, Order order) {
        return orderDTO.getComponents().stream().map(componentDTO -> {
            Component component = componentRepository.findById(componentDTO.getComponentId())
                    .orElseThrow(() -> new RuntimeException("Component not found"));
            Size size = sizeRepository.findById(componentDTO.getSizeId())
                    .orElseThrow(() -> new RuntimeException("Size not found"));

            Double componentPrice = component.getPrice() * size.getPrice();
            totalPrice.updateAndGet(v -> v * componentPrice);

            updateComponentStock(component);

            OrderComponent orderComponent = new OrderComponent();
            orderComponent.setComponent(component);
            orderComponent.setSize(size);
            orderComponent.setOrder(order);
            return orderComponent;
        }).collect(Collectors.toList());
    }

    private void updateComponentStock(Component component) {
        ComponentStock componentStock = componentStockRepository.findByComponentId(component.getId());
        if (componentStock == null) {
            throw new RuntimeException("Component stock not found");
        }
        if (componentStock.getQuantity() < 1) {
            throw new RuntimeException("Not enough components in stock");
        }
        componentStock.setQuantity(componentStock.getQuantity() - 1);
        componentStockRepository.save(componentStock);
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> findOrderById(Long id) {
        return orderRepository.findById(id);
    }
}
