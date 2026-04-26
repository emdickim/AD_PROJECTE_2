package com.ra34.projecte2.Controller;

import com.ra34.projecte2.Model.OrderItem;
import com.ra34.projecte2.Service.OrderItemService;
import com.ra34.projecte2.DTO.ErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @PostMapping
    public ResponseEntity<OrderItem> createOrderItem(@RequestBody OrderItem orderItem) {
        OrderItem createdItem = orderItemService.createOrderItem(orderItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderItem(@PathVariable Long id) {
        Optional<OrderItem> orderItem = orderItemService.getOrderItem(id);
        if (orderItem.isPresent()) {
            return ResponseEntity.ok(orderItem.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorDTO("OrderItem not found"));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItem>> getOrderItemsByOrder(@PathVariable Long orderId) {
        List<OrderItem> items = orderItemService.getOrderItemsByOrder(orderId);
        return ResponseEntity.ok(items);
    }

    @GetMapping
    public ResponseEntity<List<OrderItem>> getAllOrderItems() {
        List<OrderItem> items = orderItemService.getAllOrderItems();
        return ResponseEntity.ok(items);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderItem(@PathVariable Long id, @RequestBody OrderItem orderItemDetails) {
        OrderItem updatedItem = orderItemService.updateOrderItem(id, orderItemDetails);
        if (updatedItem != null) {
            return ResponseEntity.ok(updatedItem);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorDTO("OrderItem not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderItem(@PathVariable Long id) {
        Optional<OrderItem> orderItem = orderItemService.getOrderItem(id);
        if (orderItem.isPresent()) {
            orderItemService.deleteOrderItem(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorDTO("OrderItem not found"));
    }
}
