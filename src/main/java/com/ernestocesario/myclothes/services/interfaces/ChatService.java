package com.ernestocesario.myclothes.services.interfaces;

import com.ernestocesario.myclothes.persistance.entities.Chat;
import com.ernestocesario.myclothes.persistance.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChatService {
    Page<Chat> getAllChatsOfCustomer(String customerId, Pageable pageable);
    Page<Chat> getAllActiveChats(Pageable pageable);
    Chat getChatById(String chatId);

    boolean startChat();
    boolean terminateChat(String chatId);
    boolean sendMessage(String chatId, String message);
}
