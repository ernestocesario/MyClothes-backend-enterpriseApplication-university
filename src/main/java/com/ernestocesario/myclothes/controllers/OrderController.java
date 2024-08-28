package com.ernestocesario.myclothes.controllers;

import com.ernestocesario.myclothes.configurations.mappers.businessLogic.OrderMapper;
import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.order.FullOrderDTO;
import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.order.OrderDTO;
import com.ernestocesario.myclothes.persistance.entities.Order;
import com.ernestocesario.myclothes.services.implementations.OrderServiceImpl;
import com.ernestocesario.myclothes.services.implementations.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "${ordersControllerPath}", produces = "application/json")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class OrderController {
    private final OrderServiceImpl orderServiceImpl;
    private final UserServiceImpl userServiceImpl;
    private final OrderMapper orderMapper;

    @GetMapping("")
    public Page<OrderDTO> getAllOrdersOfCustomer(@RequestParam(required = false) String customerId, Pageable pageable) {
        if (customerId == null || customerId.isBlank())
            customerId = userServiceImpl.getCurrentUser().getId();

        Page<Order> orderPage = orderServiceImpl.getAllOrdersOfCustomer(customerId, pageable);
        return orderPage.map(orderMapper::toOrderDTO);
    }

    @GetMapping("/{orderId}")
    public FullOrderDTO getOrderById(@PathVariable String orderId) {
        Order order = orderServiceImpl.getOrderById(orderId);
        return orderMapper.toFullOrderDTO(order);
    }

    @PostMapping("")
    public ResponseEntity<Void> placeOrderFromCart(@RequestParam(required = false) String discountCode) {
        orderServiceImpl.placeOrderFromCart(discountCode);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> cancelOrder(@PathVariable String orderId) {
        orderServiceImpl.cancelOrder(orderId);
        return ResponseEntity.ok().build();
    }
}
