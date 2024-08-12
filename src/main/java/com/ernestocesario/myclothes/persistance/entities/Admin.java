package com.ernestocesario.myclothes.persistance.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "admin_id")
@Table(name = "admins")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class Admin extends User {

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Message> messages = new ArrayList<>();

    @Column(name = "kk", nullable = false)
    private String kk = "ciao";
}
