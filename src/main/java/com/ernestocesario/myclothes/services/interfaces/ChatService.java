package com.ernestocesario.myclothes.services.interfaces;

import com.ernestocesario.myclothes.persistance.entities.Chat;
import com.ernestocesario.myclothes.persistance.entities.Customer;

import java.util.List;

public interface ChatService {
    List<Chat> getAllChatsOfCustomer(String customerId);
    Chat getChatById(String chatId);

    boolean startChat(Customer customer);
    boolean terminateChat(Customer customer, String chatId);
    boolean sendMessage(Customer customer, Chat chat, String message, boolean fromCustomer);
}
