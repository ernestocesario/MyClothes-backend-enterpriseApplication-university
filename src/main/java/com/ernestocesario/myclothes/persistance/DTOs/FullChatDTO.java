package com.ernestocesario.myclothes.persistance.DTOs;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class FullChatDTO extends ChatDTO {
    private List<MessageDTO> messages;
}
