package com.ernestocesario.myclothes;

import org.springframework.beans.factory.annotation.Autowired;
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

    /*
    @Autowired
    private TestService testService;

    @Bean
    public CommandLineRunner test() {
        return (args) -> {
            testService.test1();
            testService.test2();
        };
    }
     */
}
