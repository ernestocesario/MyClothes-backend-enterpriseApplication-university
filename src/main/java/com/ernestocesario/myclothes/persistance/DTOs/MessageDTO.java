package com.ernestocesario.myclothes.persistance.DTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MessageDTO {
    private String content;
    private LocalDateTime creationTime;
    private boolean fromAdmin;
}
