package com.ernestocesario.myclothes.persistance.DTOs.businessLogic.order;

import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.product.ProductRequestDTO;
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
    private List<ProductRequestDTO> productRequestDTOList;

    @Override
    public String toString() {
        return "FullOrderDTO{" +
                "id='" + getId() + '\'' + ", " +
                "orderDate=" + getOrderDate() + ", " +
                "subtotalPrice=" + getSubtotalPrice() + ", " +
                "discountPrice=" + getDiscountPrice() + ", " +
                "totalPrice=" + getTotalPrice() + ", " +
                "orderStatus=" + getOrderStatus() + ", " +
                "shippingInfo=" + shippingInfo + ", " +
                "productRequestDTOList=" + productRequestDTOList + '}';
    }
}
