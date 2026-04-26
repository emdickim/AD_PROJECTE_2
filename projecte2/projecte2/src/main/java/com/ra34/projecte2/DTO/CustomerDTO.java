package com.ra34.projecte2.DTO;

import java.util.List;

public class CustomerDTO {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private List<AddressDTO> addresses;

    public CustomerDTO() {
    }

    public CustomerDTO(Long id, String email, String firstName, String lastName, String phone, List<AddressDTO> addresses) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.addresses = addresses;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public List<AddressDTO> getAddresses() { return addresses; }
    public void setAddresses(List<AddressDTO> addresses) { this.addresses = addresses; }
    
}
