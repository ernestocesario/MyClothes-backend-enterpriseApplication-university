package com.ernestocesario.myclothes.persistance.DTOs;

import com.ernestocesario.myclothes.persistance.entities.utils.CustomerShippingInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class FullOrderDTO extends OrderDTO {
    private CustomerShippingInfo shippingInfo;
    private List<ProductRequestDTO> products;
}
