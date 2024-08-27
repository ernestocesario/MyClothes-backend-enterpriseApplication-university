package com.ernestocesario.myclothes.persistance.DTOs.businessLogic.chat;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MessageDTO {
    @NotBlank
    private String content;

    private LocalDateTime creationTime;
    private boolean fromAdmin;

    @Override
    public String toString() {
        return "MessageDTO{" +
                "content='" + content + '\'' +
                ", creationTime=" + creationTime +
                ", fromAdmin=" + fromAdmin +
                '}';
    }
}
