package com.ernestocesario.myclothes.controllers;

import com.ernestocesario.myclothes.configurations.mappers.businessLogic.ChatMapper;
import com.ernestocesario.myclothes.exceptions.InvalidInputException;
import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.chat.ChatDTO;
import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.chat.FullChatDTO;
import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.chat.MessageDTO;
import com.ernestocesario.myclothes.persistance.entities.Chat;
import com.ernestocesario.myclothes.services.implementations.ChatServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "${chatsControllerPath}", produces = "application/json")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ChatController {

    private final ChatServiceImpl chatServiceImpl;
    private final ChatMapper chatMapper;

    @GetMapping("${customerChatsControllerSubPath}/{customerId}")
    public ResponseEntity<Page<ChatDTO>> getAllChatsOfCustomer(@PathVariable String customerId, Pageable pageable) {
        Page<Chat> chatPage = chatServiceImpl.getAllChatsOfCustomer(customerId, pageable);
        return ResponseEntity.ok(chatPage.map(chatMapper::toChatDTO));
    }

    @GetMapping("")
    public ResponseEntity<Page<ChatDTO>> getAllActiveChats(Pageable pageable) {
        Page<Chat> chatPage = chatServiceImpl.getAllActiveChats(pageable);
        return ResponseEntity.ok(chatPage.map(chatMapper::toChatDTO));
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<FullChatDTO> getChatById(@PathVariable String chatId) {
        Chat chat = chatServiceImpl.getChatById(chatId);
        return ResponseEntity.ok(chatMapper.toFullChatDTO(chat));
    }

    @PostMapping("")
    public ResponseEntity<ChatDTO> startNewChat() {
        Chat chat = chatServiceImpl.startChat();
        return ResponseEntity.ok(chatMapper.toChatDTO(chat));
    }

    @PostMapping("/{chatId}")
    public ResponseEntity<Void> sendMessage(@PathVariable String chatId, @Valid @RequestBody MessageDTO messageDTO) {
        chatServiceImpl.sendMessage(chatId, messageDTO.getContent());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{chatId}")
    public ResponseEntity<Void> terminate(@PathVariable String chatId) {
        chatServiceImpl.terminateChat(chatId);
        return ResponseEntity.ok().build();
    }
}