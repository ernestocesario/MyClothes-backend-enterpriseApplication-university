package com.ernestocesario.myclothes.persistance.repositories;

import com.ernestocesario.myclothes.persistance.entities.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthTokenRepository extends JpaRepository<AuthToken, String> {

}
