package com.ra34.projecte2.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ra34.projecte2.DTO.CreateOrderRequest;
import com.ra34.projecte2.DTO.OrderDTO;
import com.ra34.projecte2.DTO.OrderItemDTO;
import com.ra34.projecte2.Model.Customer;
import com.ra34.projecte2.Model.Order;
import com.ra34.projecte2.Model.OrderItem;
import com.ra34.projecte2.Model.OrderStatus;
import com.ra34.projecte2.Model.Product;
import com.ra34.projecte2.Repository.CustomerRepository;
import com.ra34.projecte2.Repository.OrderRepository;
import com.ra34.projecte2.Repository.OrderItemRepository;
import com.ra34.projecte2.Repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public OrderDTO createOrder(CreateOrderRequest request) {
        Optional<Customer> customer = customerRepository.findById(request.getCustomerId());
        if (customer.isEmpty()) {
            throw new IllegalArgumentException("Customer no encontrado");
        }

        Order order = new Order();
        order.setCustomer(customer.get());
        order.setCustomerId(request.getCustomerId());
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setStatus(true);
        order.setDateCreated(LocalDateTime.now());
        order.setDateUpdated(LocalDateTime.now());
        order.setTotalAmount(BigDecimal.ZERO);

        Order savedOrder = orderRepository.save(order);

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CreateOrderRequest.OrderItemRequest itemRequest : request.getItems()) {
            Optional<Product> product = productRepository.findById(itemRequest.getProductId());
            if (product.isEmpty()) {
                throw new IllegalArgumentException("Producto no encontrado");
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(product.get());
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setUnitPrice(new BigDecimal(product.get().getPrice()));

            BigDecimal itemTotal = new BigDecimal(product.get().getPrice())
                    .multiply(new BigDecimal(itemRequest.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);

            orderItems.add(orderItem);
        }

        orderItemRepository.saveAll(orderItems);

        savedOrder.setTotalAmount(totalAmount);
        savedOrder.setOrderItems(orderItems);
        Order updatedOrder = orderRepository.save(savedOrder);

        return convertToDTO(updatedOrder);
    }

    @Transactional
    public OrderDTO processOrder(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()) {
            throw new IllegalArgumentException("Orden no encontrada");
        }

        Order existingOrder = order.get();
        if (existingOrder.getOrderStatus() != OrderStatus.PENDING) {
            throw new IllegalArgumentException("La orden solo puede procesarse si está en estado PENDING");
        }

        existingOrder.setOrderStatus(OrderStatus.PROCESSSAT);
        existingOrder.setDateUpdated(LocalDateTime.now());

        Order updatedOrder = orderRepository.save(existingOrder);
        return convertToDTO(updatedOrder);
    }

    private OrderDTO convertToDTO(Order order) {
        OrderDTO dto = new OrderDTO(
                order.getId(),
                order.getCustomerId(),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getOrderStatus()
        );

        List<OrderItemDTO> itemDTOs = order.getOrderItems().stream()
                .map(item -> new OrderItemDTO(
                        item.getId(),
                        order.getId(),
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getUnitPrice()
                ))
                .collect(Collectors.toList());

        dto.setOrderItems(itemDTOs);
        return dto;
    }
}
