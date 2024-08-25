package com.ernestocesario.myclothes.configurations.mappers.businessLogic;

import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.chat.ChatDTO;
import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.chat.FullChatDTO;
import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.chat.MessageDTO;
import com.ernestocesario.myclothes.persistance.entities.Admin;
import com.ernestocesario.myclothes.persistance.entities.Chat;
import com.ernestocesario.myclothes.persistance.entities.Message;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "active", source = "active")
    @Mapping(target = "creationTime", source = "creationTime")
    @Mapping(target = "closingTime", source = "closingTime")
    @Mapping(target = "userEmail", source = "customer.email")
    ChatDTO toChatDTO(Chat chat);

    default FullChatDTO toFullChatDTO(Chat chat) {
        FullChatDTO fullChatDTO = new FullChatDTO();
        ChatDTO chatDTO = toChatDTO(chat);
        BeanUtils.copyProperties(chatDTO, fullChatDTO);

        fullChatDTO.setMessages(toMessageDTOList(chat.getMessages()));
        return fullChatDTO;
    }

    @Named("toMessageDTO")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "creationTime", source = "creationTime")
    @Mapping(target = "fromAdmin", source = "admin", qualifiedByName = "isFromAdmin")
    MessageDTO toMessageDTO(Message message);



    // Utility methods
    @Named("isFromAdmin")
    default boolean isFromAdmin(Admin admin) {
        return admin != null;
    }

    @IterableMapping(qualifiedByName = "toMessageDTO")
    List<MessageDTO> toMessageDTOList(List<Message> messages);
}