package com.example.bonusapp.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private Integer status;
    private String message;
    private LocalDateTime timeStamp;
}
