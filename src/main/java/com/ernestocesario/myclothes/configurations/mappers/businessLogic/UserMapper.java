package com.ernestocesario.myclothes.configurations.mappers.businessLogic;

import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.users.AuthResponseDTO;
import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.users.UserProfileDTO;
import com.ernestocesario.myclothes.persistance.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "imageUrl", source = "imageUrl")
    UserProfileDTO toUserProfileDTO(User user);

    @Mapping(target = "accessToken", source = "accessToken")
    @Mapping(target = "refreshToken", source = "refreshToken")
    AuthResponseDTO toAuthResponseDTO(User user);


}
