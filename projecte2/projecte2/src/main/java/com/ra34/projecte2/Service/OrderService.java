package com.ra34.projecte2.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ra34.projecte2.DTO.AddProductsToOrderRequest;
import com.ra34.projecte2.DTO.OrderItemDTO;
import com.ra34.projecte2.DTO.OrderResponseDTO;
import com.ra34.projecte2.Model.Order;
import com.ra34.projecte2.Model.OrderItem;
import com.ra34.projecte2.Model.OrderStatus;
import com.ra34.projecte2.Model.Product;
import com.ra34.projecte2.Repository.OrderItemRepository;
import com.ra34.projecte2.Repository.OrderRepository;
import com.ra34.projecte2.Repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Transactional
    public OrderResponseDTO addProductsToOrder(AddProductsToOrderRequest request) {

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order no encontrado con id: " + request.getOrderId()));

        List<OrderItem> newItems = new ArrayList<>();

        for (Long productId : request.getProductIds()) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + productId));

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(1);
            item.setUnitPrice(BigDecimal.valueOf(product.getPrice()));

            newItems.add(item);
        }

        orderItemRepository.saveAll(newItems);

        List<OrderItem> allItems = orderItemRepository.findByOrderId(order.getId());
        BigDecimal total = allItems.stream()
                .map(i -> i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalAmount(total);
        orderRepository.save(order);

        return toDTO(order, allItems);
    }

    private OrderResponseDTO toDTO(Order order, List<OrderItem> items) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setCustomerId(order.getCustomerId());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setOrderStatus(order.getOrderStatus().name());

        List<OrderItemDTO> itemDTOs = new ArrayList<>();
        for (OrderItem item : items) {
            OrderItemDTO itemDTO = new OrderItemDTO();
            itemDTO.setId(item.getId());
            itemDTO.setProductId(item.getProduct().getId());
            itemDTO.setProductName(item.getProduct().getName());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setUnitPrice(item.getUnitPrice());
            itemDTOs.add(itemDTO);
        }

        dto.setOrderItems(itemDTOs);
        return dto;
    }
    @Transactional
    public OrderResponseDTO cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order no encontrado con id: " + orderId));

        if (order.getOrderStatus() != OrderStatus.PENDENT) {
            throw new RuntimeException("Solo se pueden cancelar orders en estado PENDENT. Estado actual: " + order.getOrderStatus());
        }

        order.setOrderStatus(OrderStatus.CANCELAT);
        orderRepository.save(order);

        List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());
        return toDTO(order, items);
    }
}
