package com.ernestocesario.myclothes.persistance.DTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ChatDTO {
    private String id;
    private boolean active;
    private LocalDateTime creationTime;
    private LocalDateTime closingTime;
    private String userEmail;
}
