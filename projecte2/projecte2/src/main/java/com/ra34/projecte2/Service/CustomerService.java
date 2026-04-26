package com.ra34.projecte2.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ra34.projecte2.DTO.AddressDTO;
import com.ra34.projecte2.DTO.CustomerDTO;
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

    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();

        if (customers.isEmpty()) {
            throw new NoSuchElementException("No customers found");
        }

        return customers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private CustomerDTO convertToDTO(Customer customer) {
        List<AddressDTO> addressDTOs = customer.getAddresses() != null ?
                customer.getAddresses().stream()
                    .map(address -> new AddressDTO(
                            address.getId(),
                            address.getAddress(),
                            address.getCity(),
                            address.getPostalCode(),
                            address.getCountry(),
                            address.isDefault()
                    ))
                    .collect(Collectors.toList())
                : List.of();

        String email = customer.getUser() != null ? customer.getUser().getEmail() : null;

        return new CustomerDTO(
                customer.getId(),
                email,
                customer.getFirstName(),
                customer.getLastName(),
                customer.getPhone(),
                addressDTOs
        );
    }
}

