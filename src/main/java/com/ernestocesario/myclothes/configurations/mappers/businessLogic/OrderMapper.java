package com.ernestocesario.myclothes.configurations.mappers.businessLogic;

import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.order.FullOrderDTO;
import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.order.OrderDTO;
import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.product.ProductRequestDTO;
import com.ernestocesario.myclothes.persistance.entities.Order;
import com.ernestocesario.myclothes.persistance.entities.OrderProduct;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface OrderMapper {

    ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "orderDate", source = "orderDate")
    @Mapping(target = "subtotalPrice", source = "subtotalPrice")
    @Mapping(target = "discountPrice", source = "discountPrice")
    @Mapping(target = "totalPrice", source = "totalPrice")
    @Mapping(target = "orderStatus", source = "orderStatus")
    OrderDTO toOrderDTO(Order order);

    default FullOrderDTO toFullOrderDTO(Order order) {
        OrderDTO orderDTO = toOrderDTO(order);
        FullOrderDTO fullOrderDTO = new FullOrderDTO();
        BeanUtils.copyProperties(orderDTO, fullOrderDTO);

        fullOrderDTO.setShippingInfo(order.getShippingInfo());
        fullOrderDTO.setProductRequestDTOList(toProductRequestDTOList(order.getOrderProducts()));

        return fullOrderDTO;
    }


    //Utility methods
    @IterableMapping(qualifiedByName = "toProductRequestDTOFromOrderProduct")
    List<ProductRequestDTO> toProductRequestDTOList(List<OrderProduct> orderProducts);
}
