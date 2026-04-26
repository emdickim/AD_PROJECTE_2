    package com.ra34.projecte2.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ra34.projecte2.DTO.AddRolesToUserRequest;
import com.ra34.projecte2.DTO.RoleDTO;
import com.ra34.projecte2.DTO.UserDTO;
import com.ra34.projecte2.DTO.UserWithRolesDTO;
import com.ra34.projecte2.Model.CreateUserRequest;
import com.ra34.projecte2.Model.Customer;
import com.ra34.projecte2.Model.Role;
import com.ra34.projecte2.Model.User;
import com.ra34.projecte2.Repository.CustomerRepository;
import com.ra34.projecte2.Repository.RoleRepository;
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

public List<UserDTO> getAllUsers() {

    return userRepository.findAll()
        .stream()
        .map(this::toDTO)
        .toList();
}

    private UserDTO toDTO(User user) {

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setStatus(user.getStatus());
        return dto;
    }
    
    public UserDTO editUser(Long id, UserDTO userDTO) {

        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        user.setEmail(userDTO.getEmail());
        user.setStatus(userDTO.getStatus());
        user.setDataUpdated(LocalDateTime.now());

        User updated = userRepository.save(user);

        return toDTO(updated);
    }
    @Autowired
private RoleRepository roleRepository;

@Transactional
public UserWithRolesDTO addRolesToUser(AddRolesToUserRequest request) {

    // 1. Verifica que el usuario existe
    User user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + request.getUserId()));

    // 2. Busca los roles y los añade
    for (Long roleId : request.getRoleIds()) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + roleId));

        // Evita duplicados
        if (!user.getRoles().contains(role)) {
            user.getRoles().add(role);
        }
    }

    userRepository.save(user);

    return toUserWithRolesDTO(user);
}

    private UserWithRolesDTO toUserWithRolesDTO(User user) {
        UserWithRolesDTO dto = new UserWithRolesDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setStatus(user.getStatus());

        List<RoleDTO> roleDTOs = new ArrayList<>();
        for (Role role : user.getRoles()) {
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setId(role.getId());
            roleDTO.setName(role.getName());
            roleDTO.setDescription(role.getDescription());
            roleDTOs.add(roleDTO);
        }

        dto.setRoles(roleDTOs);
        return dto; 
    }
}
