package com.ernestocesario.myclothes.persistance.entities;

import com.ernestocesario.myclothes.persistance.entities.utils.CustomerShippingInfo;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "customer_id")
@Table(name = "customers")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class Customer extends User {
    @Column(name = "balance", nullable = false, columnDefinition = "double precision default 0")
    private double balance;

    @Column(name = "banned", nullable = false, columnDefinition = "boolean default false")
    private boolean banned;

    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column()),
            @AttributeOverride(name = "lastName", column = @Column()),
            @AttributeOverride(name = "phoneNumber", column = @Column()),
            @AttributeOverride(name = "street", column = @Column()),
            @AttributeOverride(name = "civicNumber", column = @Column()),
            @AttributeOverride(name = "city", column = @Column()),
            @AttributeOverride(name = "state", column = @Column()),
            @AttributeOverride(name = "zipCode", column = @Column())
    })
    private CustomerShippingInfo shippingInfo;



    //associations
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DiscountCode> discountCodes = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private Cart cart = new Cart();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Chat> chats = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Wishlist> wishlists = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<WishlistShare> wishlistShares = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();
}
