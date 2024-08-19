package com.ernestocesario.myclothes.persistance.repositories;

import com.ernestocesario.myclothes.persistance.entities.AllowedAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllowedAdminRepository extends JpaRepository<AllowedAdmin, String> {
    boolean existsByAdminEmail(String email);
    void deleteByAdminEmail(String email);
}
