package com.ernestocesario.myclothes.persistance.repositories;

import com.ernestocesario.myclothes.persistance.entities.CartElement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartElementRepository extends JpaRepository<CartElement, String> {
}
