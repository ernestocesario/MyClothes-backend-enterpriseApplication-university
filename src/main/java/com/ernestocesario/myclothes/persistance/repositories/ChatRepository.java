package com.ernestocesario.myclothes.persistance.repositories;

import com.ernestocesario.myclothes.persistance.entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, String> {
}
