package com.ernestocesario.myclothes.services.interfaces;

import com.ernestocesario.myclothes.persistance.entities.Customer;
import com.ernestocesario.myclothes.persistance.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    Page<Order> getAllOrdersOfCustomer(String customerId, Pageable pageable);
    Order getOrderById(String orderId);

    boolean placeOrderFromCart(String discountCode);
    boolean cancelOrder(String orderId);
}
