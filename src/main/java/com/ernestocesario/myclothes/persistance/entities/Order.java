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
    private UserShippingInfo shippingInfo;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;



    //associations
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderProducts;


    //private methods
    @PostLoad
    private void calculateTotalPrice() {
        totalPrice = subtotalPrice - discount;
    }
}
