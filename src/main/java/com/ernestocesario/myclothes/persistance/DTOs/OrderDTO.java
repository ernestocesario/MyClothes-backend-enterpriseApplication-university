package com.ernestocesario.myclothes.persistance.DTOs;

import com.ernestocesario.myclothes.persistance.entities.utils.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class OrderDTO {
    private String id;
    private LocalDateTime orderDate;
    private double subtotalPrice;
    private double discount;
    private double totalPrice;
    private OrderStatus orderStatus;
}