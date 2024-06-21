package com.ernestocesario.myclothes.persistance.entities;

import com.ernestocesario.myclothes.persistance.entities.utils.UserShippingInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("customer")
@Table(name = "users")
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;


    @Column(name = "email", nullable = false, unique = true)
    @Email
    private String email;

    @Column(name = "banned", nullable = false, columnDefinition = "boolean default false")
    private boolean banned;

    @Column(name = "creation_date")
    @CreatedDate
    private LocalDateTime creationDate;

    @Column(name = "last_modification_date")
    @LastModifiedDate
    private LocalDateTime lastModificationDate;

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



    //associations
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DiscountCode> discountCodes;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Message> sentMessages;

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Message> receivedMessages;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Order> orders;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<WishlistAccess> wishlistAccesses;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Review> reviews;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CartElement> cartElements;
}
