package com.ernestocesario.myclothes.persistance.entities;

import com.ernestocesario.myclothes.persistance.entities.utils.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "creation_date")
    @CreatedDate
    private LocalDateTime creationDate;

    @Column(name = "last_modification_date")
    @LastModifiedDate
    private LocalDateTime lastModificationDate;

    public UserRole getRole() {
        if (this instanceof Customer)
            return UserRole.CUSTOMER;
        else if (this instanceof Admin)
            return UserRole.ADMIN;

        return null;
    }


    //associations
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private AuthToken authToken;
}
