package com.ernestocesario.myclothes.persistance.entities.utils;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Data
public class UserShippingInfo {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String street;
    private String civicNumber;
    private String city;
    private String state;
    private String zipCode;
}
