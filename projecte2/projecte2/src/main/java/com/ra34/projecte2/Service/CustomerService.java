package com.ra34.projecte2.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ra34.projecte2.Model.Customer;
import com.ra34.projecte2.Repository.AddressRepository;
import com.ra34.projecte2.Repository.CustomerRepository;

@Service
public class CustomerService {
    

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    public void deleteAdresses(Long idCustomer) {

        Optional<Customer> customer = customerRepository.findById(idCustomer);
        

        if (customer.isEmpty()) {
            throw new NoSuchElementException("Customer con id " + idCustomer + " no encontrado");
        }

        addressRepository.deleteAll(customer.get().getAddresses());
    }

    public List<Customer> getAllCustomers() {
         
        List<Customer> customers = new ArrayList<>();

        customers = customerRepository.findAll();

        if (customers.isEmpty()) {
            throw new NoSuchElementException("No customers found");
        }
        

        return null;
    }

}


        
