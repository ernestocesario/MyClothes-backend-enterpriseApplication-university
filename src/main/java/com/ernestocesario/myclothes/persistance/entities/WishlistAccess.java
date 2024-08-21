package com.ernestocesario.myclothes.persistance.entities;

import com.ernestocesario.myclothes.persistance.entities.utils.WishlistPermission;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "wishlist_accesses")
@NoArgsConstructor
@Data
public class WishlistAccess {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "permission", nullable = false)
    @Enumerated(EnumType.STRING)
    private WishlistPermission wishlistPermission;



    //associations
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "wishlist_id", nullable = false)
    private Wishlist wishlist;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "customer_id", nullable = true)
    private Customer customer;
}
