package com.ra34.projecte2.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ra34.projecte2.DTO.CustomerDTO;
import com.ra34.projecte2.DTO.ErrorDTO;
import com.ra34.projecte2.Service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<?> getAllCustomers() {
        try {
            List<CustomerDTO> customers = customerService.getAllCustomers();
            return ResponseEntity.ok(customers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorDTO(HttpStatus.NOT_FOUND.value(), "Error: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}/addresses")
    public ResponseEntity<?> deleteCustomersAdresses(@PathVariable Long id) {
        try {
            customerService.deleteAdresses(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error :" + e.getMessage());    
        }
    }
}
















/*


        @DeleteMapping("/{id}/addresses")
        public ResponseEntity<?> deleteCustomerAddresses(@PathVariable Long id) {
            try {
                customerService.deleteAdresses(id);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
            }
        }*/