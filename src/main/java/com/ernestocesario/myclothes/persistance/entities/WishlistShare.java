package com.ernestocesario.myclothes.persistance.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "wishlist_shares")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WishlistShare {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;


    //associations
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wishlist_id", nullable = false)
    private Wishlist wishlist;
}
