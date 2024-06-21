package com.ernestocesario.myclothes;

import com.ernestocesario.myclothes.persistance.entities.User;
import com.ernestocesario.myclothes.persistance.repositories.AdminRepository;
import com.ernestocesario.myclothes.persistance.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MyClothesBackendEnterpriseApplicationUniversityApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyClothesBackendEnterpriseApplicationUniversityApplication.class, args);
    }

    @Bean
    public CommandLineRunner test(UserRepository userRepository, AdminRepository adminRepository) {
        return (args) -> {
        };
    }
}
