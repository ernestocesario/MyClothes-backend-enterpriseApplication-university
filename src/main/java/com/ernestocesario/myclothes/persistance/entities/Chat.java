package com.ernestocesario.myclothes.persistance.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chats")
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "creation_time")
    @CreatedDate
    private LocalDateTime creationTime;

    @Column(name = "closing_time")
    private LocalDateTime closingTime;


    //associations
    @OneToMany(mappedBy = "chat", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}
