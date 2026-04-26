package com.ra34.projecte2.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ra34.projecte2.DTO.CreateOrderRequest;
import com.ra34.projecte2.DTO.ErrorDTO;
import com.ra34.projecte2.DTO.OrderDTO;
import com.ra34.projecte2.Service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest request) {
        try {
            if (request.getCustomerId() == null || request.getItems() == null || request.getItems().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorDTO(HttpStatus.BAD_REQUEST.value(), "customerId e items son requeridos"));
            }
            OrderDTO orderDTO = orderService.createOrder(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(orderDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorDTO(HttpStatus.BAD_REQUEST.value(), "Error: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error: " + e.getMessage()));
        }
    }

    @PostMapping("/{orderId}/process")
    public ResponseEntity<?> processOrder(@PathVariable Long orderId) {
        try {
            OrderDTO orderDTO = orderService.processOrder(orderId);
            return ResponseEntity.ok(orderDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorDTO(HttpStatus.BAD_REQUEST.value(), "Error: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error: " + e.getMessage()));
        }
    }
}
