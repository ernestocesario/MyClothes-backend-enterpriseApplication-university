package com.ernestocesario.myclothes.services.interfaces;

import com.ernestocesario.myclothes.persistance.entities.Customer;
import com.ernestocesario.myclothes.persistance.entities.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrdersOfCustomer(Customer customer);
    Order getOrderById(String orderId);

    boolean placeOrder(Customer customer, Order order);
    boolean cancelOrder(Customer customer, String orderId);
}
