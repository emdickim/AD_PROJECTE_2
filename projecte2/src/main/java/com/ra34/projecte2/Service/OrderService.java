package com.ra34.projecte2.Service;

import com.ra34.projecte2.Model.Order;
import com.ra34.projecte2.Model.OrderStatus;
import com.ra34.projecte2.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Order order) {
        order.setDateCreated(LocalDateTime.now());
        order.setDateUpdated(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.PENDING);
        return orderRepository.save(order);
    }

    public Optional<Order> getOrder(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> getOrdersByCustomer(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order updateOrder(Long id, Order orderDetails) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            Order existingOrder = order.get();
            existingOrder.setCustomerId(orderDetails.getCustomerId());
            existingOrder.setOrderDate(orderDetails.getOrderDate());
            existingOrder.setTotalAmount(orderDetails.getTotalAmount());
            existingOrder.setOrderStatus(orderDetails.getOrderStatus());
            existingOrder.setNotes(orderDetails.getNotes());
            existingOrder.setDateUpdated(LocalDateTime.now());
            return orderRepository.save(existingOrder);
        }
        return null;
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
