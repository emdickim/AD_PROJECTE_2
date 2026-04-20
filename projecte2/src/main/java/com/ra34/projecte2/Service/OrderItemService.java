package com.ra34.projecte2.Service;

import com.ra34.projecte2.Model.OrderItem;
import com.ra34.projecte2.Repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    public Optional<OrderItem> getOrderItem(Long id) {
        return orderItemRepository.findById(id);
    }

    public List<OrderItem> getOrderItemsByOrder(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public OrderItem updateOrderItem(Long id, OrderItem orderItemDetails) {
        Optional<OrderItem> orderItem = orderItemRepository.findById(id);
        if (orderItem.isPresent()) {
            OrderItem existingItem = orderItem.get();
            existingItem.setOrder(orderItemDetails.getOrder());
            existingItem.setProduct(orderItemDetails.getProduct());
            existingItem.setQuantity(orderItemDetails.getQuantity());
            existingItem.setUnitPrice(orderItemDetails.getUnitPrice());
            return orderItemRepository.save(existingItem);
        }
        return null;
    }

    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }
}
