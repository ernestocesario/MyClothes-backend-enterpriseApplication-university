package com.ernestocesario.myclothes.persistance.entities;

import com.ernestocesario.myclothes.persistance.entities.utils.OrderProduct;
import com.ernestocesario.myclothes.persistance.entities.utils.OrderStatus;
import com.ernestocesario.myclothes.persistance.entities.utils.UserShippingInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "order_date", nullable = false)
    @CreatedDate
    private LocalDateTime orderDate;

    @Column(name = "subtotal_price", nullable = false)
    @Positive
    private double subtotalPrice;

    @Column(name = "discount_applied", nullable = false)
    @PositiveOrZero
    private double discount;

    @Transient
    private double totalPrice;

    private UserShippingInfo shippingInfo;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;



    //associations
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> products;


    //private methods
    @PostLoad
    private void calculateTotalPrice() {
        totalPrice = subtotalPrice - discount;
    }
}
