package com.ernestocesario.myclothes.services.implementations;

import com.ernestocesario.myclothes.exceptions.InternalServerErrorException;
import com.ernestocesario.myclothes.exceptions.InvalidDiscountCodeException;
import com.ernestocesario.myclothes.persistance.entities.*;
import com.ernestocesario.myclothes.persistance.entities.utils.OrderStatus;
import com.ernestocesario.myclothes.persistance.entities.utils.ProductSnapshot;
import com.ernestocesario.myclothes.persistance.repositories.CustomerRepository;
import com.ernestocesario.myclothes.persistance.repositories.DiscountCodeRepository;
import com.ernestocesario.myclothes.persistance.repositories.OrderProductRepository;
import com.ernestocesario.myclothes.persistance.repositories.OrderRepository;
import com.ernestocesario.myclothes.services.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {


    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final UserServiceImpl userServiceImpl;
    private final DiscountCodeServiceImpl discountCodeServiceImpl;
    private final DiscountCodeRepository discountCodeRepository;
    private final OrderProductRepository orderProductRepository;

    @Override
    @Transactional
    public Page<Order> getAllOrdersOfCustomer(String customerId, Pageable pageable) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null)
            throw new InternalServerErrorException();

        Sort sort = Sort.by(Sort.Direction.DESC, "orderDate");
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return orderRepository.findAllByCustomer(customer, pageable);
    }

    @Override
    @Transactional
    public Order getOrderById(String orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null)
            throw new InternalServerErrorException();

        return order;
    }

    @Override
    @Transactional
    public boolean placeOrderFromCart(String discountCode) {
        User user = userServiceImpl.getCurrentUser();
        if (!user.isCustomer())
            throw new InternalServerErrorException();

        Customer customer = (Customer) user;
        Cart cart = customer.getCart();

        Order order = new Order();
        order.setCustomer(customer);
        order.setShippingInfo(customer.getShippingInfo());
        order.setOrderStatus(OrderStatus.PENDING);

        for (CartElement cartElement : cart.getCartElements()) {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setQuantity(cartElement.getQuantity());
            orderProduct.setProductSnapshot(ProductSnapshot.fromProductVariant(cartElement.getProductVariant()));
            orderProductRepository.save(orderProduct);

            order.getOrderProducts().add(orderProduct);
        }

        order.calculateSubtotalPrice();

        if (discountCode != null) {
            DiscountCode discountCodeEntity = discountCodeRepository.findByCustomerAndCodeAndUsedIsFalse(customer, discountCode);
            if (discountCodeEntity != null) {
                order.applyDiscount(discountCodeEntity.getDiscountPercentage());
                discountCodeEntity.setUsed(true);
                discountCodeRepository.save(discountCodeEntity);
            }
            else
                throw new InvalidDiscountCodeException();
        }
        order.calculateTotalPrice();

        orderRepository.save(order);

        return true;
    }

    @Override
    @Transactional
    public boolean cancelOrder(String orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null)
            throw new InternalServerErrorException();

        if (order.getOrderStatus() != OrderStatus.PENDING)
            return false;

        orderRepository.delete(order);

        return true;
    }
}