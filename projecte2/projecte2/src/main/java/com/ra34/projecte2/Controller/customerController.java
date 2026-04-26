package com.ra34.projecte2.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ra34.projecte2.DTO.CustomerDetailsDTO;
import com.ra34.projecte2.Model.Address;
import com.ra34.projecte2.Model.Customer;
import com.ra34.projecte2.Service.CustomerService;


@RestController
@RequestMapping("/customers")
public class customerController {

    @Autowired
    private CustomerService customerService;
 
    
    @PutMapping("/{id}/addresses")
    public ResponseEntity<Customer> addAddresses(
        @PathVariable Long id,
        @RequestBody List<Address> addresses) {

        try {
            Customer result = customerService.addAddresses(id, addresses);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
        public ResponseEntity<CustomerDetailsDTO> getCustomer(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(customerService.getCustomer(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
