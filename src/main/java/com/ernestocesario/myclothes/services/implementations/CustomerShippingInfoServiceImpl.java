package com.ernestocesario.myclothes.services.implementations;

import com.ernestocesario.myclothes.persistance.entities.utils.CustomerShippingInfo;
import com.ernestocesario.myclothes.services.interfaces.CustomerShippingInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class CustomerShippingInfoServiceImpl implements CustomerShippingInfoService {
    @Override
    public boolean validateShippingInfo(CustomerShippingInfo customerShippingInfo) {
        return
                StringUtils.hasLength(customerShippingInfo.getFirstName()) &&
                StringUtils.hasLength(customerShippingInfo.getLastName()) &&
                StringUtils.hasLength(customerShippingInfo.getPhoneNumber()) &&
                StringUtils.hasLength(customerShippingInfo.getStreet()) &&
                StringUtils.hasLength(customerShippingInfo.getCivicNumber()) &&
                StringUtils.hasLength(customerShippingInfo.getCity()) &&
                StringUtils.hasLength(customerShippingInfo.getState()) &&
                StringUtils.hasLength(customerShippingInfo.getZipCode());
    }
}
