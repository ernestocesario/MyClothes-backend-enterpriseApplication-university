package com.ernestocesario.myclothes.persistance.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "allowed_admins")
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class AllowedAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "admin_email", nullable = false)
    private String adminEmail;
}
