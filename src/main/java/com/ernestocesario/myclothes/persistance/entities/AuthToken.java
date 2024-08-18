package com.ernestocesario.myclothes.persistance.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "AuthTokens")
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class AuthToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "creation_time")
    @CreatedDate
    private LocalDateTime creationTime;

    @Column(name = "access_token", nullable = false)
    private String accessToken;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;


    //associations
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
