package com.ernestocesario.myclothes.services.implementations;

import com.ernestocesario.myclothes.configurations.security.authorization.AuthorizationChecker;
import com.ernestocesario.myclothes.configurations.security.authorization.predicates.user.IsCustomer;
import com.ernestocesario.myclothes.configurations.security.authorization.predicates.customer.CustomerOwnCustomerOrIsAdmin;
import com.ernestocesario.myclothes.configurations.security.authorization.predicates.order.CustomerOwnOrderOrIsAdmin;
import com.ernestocesario.myclothes.exceptions.InternalServerErrorException;
import com.ernestocesario.myclothes.exceptions.customer.CustomerNotExistsException;
import com.ernestocesario.myclothes.exceptions.discountCode.InvalidDiscountCodeException;
import com.ernestocesario.myclothes.exceptions.order.OrderCannotBeCancelledException;
import com.ernestocesario.myclothes.exceptions.order.OrderNotExistsException;
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
    private final DiscountCodeRepository discountCodeRepository;
    private final OrderProductRepository orderProductRepository;
    private final CustomerOwnCustomerOrIsAdmin customerOwnCustomerOrIsAdmin;
    private final CustomerOwnOrderOrIsAdmin customerOwnOrderOrIsAdmin;
    private final IsCustomer isCustomer;

    @Override
    @Transactional
    public Page<Order> getAllOrdersOfCustomer(String customerId, Pageable pageable) {
        AuthorizationChecker.check(customerOwnCustomerOrIsAdmin, userServiceImpl.getCurrentUser(), customerId);

        Customer customer = customerRepository.findById(customerId).orElseThrow(CustomerNotExistsException::new);

        Sort sort = Sort.by(Sort.Direction.DESC, "orderDate");
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return orderRepository.findAllByCustomer(customer, pageable);
    }

    @Override
    @Transactional
    public Order getOrderById(String orderId) {
        AuthorizationChecker.check(customerOwnOrderOrIsAdmin, userServiceImpl.getCurrentUser(), orderId);

        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotExistsException::new);

        return order;
    }

    @Override
    @Transactional
    public boolean placeOrderFromCart(String discountCode) {
        AuthorizationChecker.check(isCustomer, userServiceImpl.getCurrentUser());

        Customer customer = (Customer) userServiceImpl.getCurrentUser();
        Cart cart = customer.getCart();

        Order order = new Order();
        order.setCustomer(customer);
        order.setShippingInfo(customer.getShippingInfo());
        order.setOrderStatus(OrderStatus.PENDING);

        orderRepository.save(order);

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
        AuthorizationChecker.check(customerOwnOrderOrIsAdmin, userServiceImpl.getCurrentUser(), orderId);

        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotExistsException::new);

        if (order.getOrderStatus() != OrderStatus.PENDING)
            throw new OrderCannotBeCancelledException();

        orderRepository.delete(order);

        return true;
    }
}