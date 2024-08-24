package com.ernestocesario.myclothes.services.interfaces;

import com.ernestocesario.myclothes.persistance.entities.utils.CustomerShippingInfo;

public interface CustomerShippingInfoService {
    boolean validateShippingInfo(CustomerShippingInfo customerShippingInfo);
}
