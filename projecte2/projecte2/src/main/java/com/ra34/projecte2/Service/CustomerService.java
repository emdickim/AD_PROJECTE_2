package com.ra34.projecte2.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ra34.projecte2.DTO.AddressDTO;
import com.ra34.projecte2.DTO.CustomerDetailsDTO;
import com.ra34.projecte2.Model.Address;
import com.ra34.projecte2.Model.Customer;
import com.ra34.projecte2.Model.User;
import com.ra34.projecte2.Repository.CustomerRepository;
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer addAddresses(Long customerId, List<Address> newAddresses) {

        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new RuntimeException("Customer not found"));

        for (Address address : newAddresses) {
            address.setCustomer(customer); 
        }

        customer.getAddresses().addAll(newAddresses);

        Customer updated = customerRepository.save(customer);

        return updated;
    }

    public CustomerDetailsDTO getCustomer(Long customerId) {

        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new RuntimeException("Customer not found"));

        User user = customer.getUser(); // 1:1

        CustomerDetailsDTO dto = new CustomerDetailsDTO();

        dto.setEmail(user.getEmail());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setPhone(customer.getPhone());

        List<AddressDTO> addressDTOs = customer.getAddresses()
            .stream()
            .map(this::toAddressDTO)
            .toList();

        dto.setAddresses(addressDTOs);

        return dto;
    }
    private AddressDTO toAddressDTO(Address address) {

    AddressDTO dto = new AddressDTO();

    dto.setAddress(address.getAddress());
    dto.setCity(address.getCity());
    dto.setPostalCode(address.getPostalCode());
    dto.setCountry(address.getCountry());
    dto.setIsDefault(address.isDefault());

    return dto;
}
}
