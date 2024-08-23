package com.ernestocesario.myclothes.configurations.mappers;

import com.ernestocesario.myclothes.persistance.DTOs.CustomerProfileDTO;
import com.ernestocesario.myclothes.persistance.DTOs.UserProfileDTO;
import com.ernestocesario.myclothes.persistance.entities.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeanUtils;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface CustomerMapper {

    UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    default CustomerProfileDTO toCustomerProfileDTO(Customer customer) {
        CustomerProfileDTO customerProfileDTO = new CustomerProfileDTO();
        UserProfileDTO userProfileDTO = userMapper.toUserProfileDTO(customer);
        BeanUtils.copyProperties(userProfileDTO, customerProfileDTO);

        customerProfileDTO.setBalance(customer.getBalance());
        customerProfileDTO.setBanned(customer.isBanned());
        customerProfileDTO.setShippingInfo(customer.getShippingInfo());

        return customerProfileDTO;
    }
}