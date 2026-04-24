    package com.ra34.projecte2.Service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ra34.projecte2.Model.CreateUserRequest;
import com.ra34.projecte2.Model.Customer;
import com.ra34.projecte2.Model.User;
import com.ra34.projecte2.Repository.CustomerRepository;
import com.ra34.projecte2.Repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    private CreateUserRequest createUserRequest;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CustomerRepository customerRepository;

    /*
     Endpoint per crear un usuari (1 punt)
        Ha de rebre la informació de l’usuari i del customer.
        Si ja existeix un usuari amb un email igual no ha de deixar crear-los.
        Ha de crear l’usuari i el customer en una mateixa transacció.
        Ha de retornar només la informació de l’usuari.
    */
    @Transactional
    public User createUser(CreateUserRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("El email ya existe paleto" + request.getEmail());
        }

        User user = new User();

        
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setDataCreated(LocalDateTime.now());
        user.setDataUpdated(LocalDateTime.now());

        Customer customer = new Customer();
        //customer.setAddresses(r);
        customer.setUser(user);
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setPhone(request.getPhone());
        customer.setStatus(true);
        customer.setDataCreated(LocalDateTime.now());
        customer.setDataUpdated(LocalDateTime.now());

        customerRepository.save(customer);

        return userRepository.save(user);
    }



}
