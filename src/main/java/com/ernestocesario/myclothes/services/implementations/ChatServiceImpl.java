package com.ernestocesario.myclothes.services.implementations;

import com.ernestocesario.myclothes.exceptions.ActiveChatAlreadyExistsForCustomerException;
import com.ernestocesario.myclothes.exceptions.InternalServerErrorException;
import com.ernestocesario.myclothes.persistance.entities.*;
import com.ernestocesario.myclothes.persistance.repositories.ChatRepository;
import com.ernestocesario.myclothes.persistance.repositories.MessageRepository;
import com.ernestocesario.myclothes.services.interfaces.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UserServiceImpl userServiceImpl;
    private final CustomerServiceImpl customerServiceImpl;
    private final MessageRepository messageRepository;

    @Override
    @Transactional
    public Page<Chat> getAllChatsOfCustomer(String customerId, Pageable pageable) {  //only admin
        Sort sort = Sort.by(Sort.Direction.DESC, "creationTime");
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return chatRepository.findAllByCustomer_Id(customerId, pageable);
    }

    @Override
    @Transactional
    public Page<Chat> getAllActiveChats(Pageable pageable) {  //only admin
        Sort sort = Sort.by(Sort.Direction.ASC, "lastMessageTime");
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return chatRepository.findAllByActiveIsTrue(pageable);
    }

    @Override
    @Transactional
    public Chat getChatById(String chatId) {
        return chatRepository.findById(chatId).orElse(null);
    }

    @Override
    @Transactional
    public boolean startChat() {  //only customers
        Customer customer = (Customer) userServiceImpl.getCurrentUser();

        if (customerServiceImpl.hasActiveChat(customer))
            throw new ActiveChatAlreadyExistsForCustomerException();

        Chat chat = new Chat();
        chat.setCustomer(customer);
        chatRepository.save(chat);

        return true;
    }

    @Override
    @Transactional
    public boolean terminateChat(String chatId) {
        Chat chat = chatRepository.findById(chatId).orElse(null);
        if (chat == null)
            throw new InternalServerErrorException();

        chat.setActive(false);
        chatRepository.save(chat);

        return true;
    }

    @Override
    @Transactional
    public boolean sendMessage(String chatId, String messageContent) {
        User currentUser = userServiceImpl.getCurrentUser();
        boolean fromCustomer = currentUser.isCustomer();

        Chat chat = chatRepository.findById(chatId).orElse(null);
        if (chat == null || !chat.isActive())
            throw new InternalServerErrorException();

        Message message = new Message();
        message.setChat(chat);
        message.setAdmin((fromCustomer) ? null : (Admin) currentUser);
        message.setContent(messageContent);
        messageRepository.save(message);

        return true;
    }
}