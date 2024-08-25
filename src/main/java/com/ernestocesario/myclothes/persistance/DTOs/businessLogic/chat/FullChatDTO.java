package com.ernestocesario.myclothes.persistance.DTOs.businessLogic.chat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class FullChatDTO extends ChatDTO {
    private List<MessageDTO> messages;

    @Override
    public String toString() {
        return "FullChatDTO{" +
                "id='" + getId() + '\'' +
                ", active=" + isActive() +
                ", creationTime=" + getCreationTime() +
                ", closingTime=" + getClosingTime() +
                ", userEmail='" + getUserEmail() + '\'' +
                ", messages=" + messages +
                '}';
    }
}
