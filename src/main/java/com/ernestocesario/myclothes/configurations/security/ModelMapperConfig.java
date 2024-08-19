package com.ernestocesario.myclothes.configurations.security;

import com.ernestocesario.myclothes.persistance.DTOs.AuthResponseDTO;
import com.ernestocesario.myclothes.persistance.entities.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        PropertyMap<User, AuthResponseDTO> userAuthTokenDTOPropertyMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setAccessToken(source.getAccessToken());
                map().setRefreshToken(source.getRefreshToken());
            }
        };


        modelMapper.addMappings(userAuthTokenDTOPropertyMap);


        return modelMapper;
    }
}
