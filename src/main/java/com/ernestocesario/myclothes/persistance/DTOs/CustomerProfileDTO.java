package com.ernestocesario.myclothes.persistance.DTOs;

import com.ernestocesario.myclothes.persistance.entities.utils.CustomerShippingInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CustomerProfileDTO extends UserProfileDTO {
    private double balance;
    private boolean banned;
    private CustomerShippingInfo shippingInfo;
}
