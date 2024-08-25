package com.ernestocesario.myclothes.persistance.entities;

import com.ernestocesario.myclothes.persistance.entities.utils.OrderStatus;
import com.ernestocesario.myclothes.persistance.entities.utils.CustomerShippingInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "order_date")
    @CreatedDate
    private LocalDateTime orderDate;

    @Column(name = "subtotal_price", nullable = false, columnDefinition = "double precision default 0")
    @Positive
    private double subtotalPrice;

    @Column(name = "discount_applied", nullable = false, columnDefinition = "double precision default 0")
    @PositiveOrZero
    private double discountPrice;

    @Transient
    private double totalPrice;

    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(nullable = false)),
            @AttributeOverride(name = "lastName", column = @Column(nullable = false)),
            @AttributeOverride(name = "phoneNumber", column = @Column(nullable = false)),
            @AttributeOverride(name = "street", column = @Column(nullable = false)),
            @AttributeOverride(name = "civicNumber", column = @Column(nullable = false)),
            @AttributeOverride(name = "city", column = @Column(nullable = false)),
            @AttributeOverride(name = "state", column = @Column(nullable = false)),
            @AttributeOverride(name = "zipCode", column = @Column(nullable = false))
    })
    private CustomerShippingInfo shippingInfo;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;



    //associations
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderProducts = new ArrayList<>();


    //public methods
    public void calculateSubtotalPrice() {
        subtotalPrice = orderProducts.stream().mapToDouble(OrderProduct::getTotalPrice).sum();
    }

    public void applyDiscount(int discountPercentage) {
        discountPrice = subtotalPrice * discountPercentage / 100;
    }

    @PostLoad
    public void calculateTotalPrice() {
        totalPrice = subtotalPrice - discountPrice;
    }
}
