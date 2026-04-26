package com.ra34.projecte2.Mapper;

import com.ra34.projecte2.DTO.AddressDTO;
import com.ra34.projecte2.Model.Address;

public class AddressMapper {

    public static AddressDTO toDTO(Address address) {

        AddressDTO dto = new AddressDTO();

        dto.setAddress(address.getAddress());
        dto.setCity(address.getCity());
        dto.setPostalCode(address.getPostalCode());
        dto.setCountry(address.getCountry());
        dto.setIsDefault(address.isDefault());

        return dto;
    }
}