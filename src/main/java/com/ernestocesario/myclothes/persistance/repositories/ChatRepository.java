package com.ernestocesario.myclothes.persistance.repositories;

import com.ernestocesario.myclothes.persistance.entities.Chat;
import com.ernestocesario.myclothes.persistance.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, String> {
    Page<Chat> findAllByCustomer_Id(String customerId, Pageable pageable);
    Page<Chat> findAllByActiveIsTrue(Pageable pageable);

    boolean existsChatByActiveIsTrueAndCustomer(Customer customer);
    boolean existsChatByIdAndCustomer(String chatId, Customer customer);
}
