package com.ernestocesario.myclothes.persistance.entities.utils;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerShippingInfo {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String street;
    private String civicNumber;
    private String city;
    private String state;
    private String zipCode;


    //methods
    public boolean isComplete() {
        return firstName != null && !firstName.isEmpty() &&
                lastName != null && !lastName.isEmpty() &&
                phoneNumber != null && !phoneNumber.isEmpty() &&
                street != null && !street.isEmpty() &&
                civicNumber != null && !civicNumber.isEmpty() &&
                city != null && !city.isEmpty() &&
                state != null && !state.isEmpty() &&
                zipCode != null && !zipCode.isEmpty();
    }
}
