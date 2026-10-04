package com.grupo7.GestionInventarioAPI.dto.response.error;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder @Getter
public class ValidationErrorResponseDTO {
    private String error;

    private List<String> mensajes;

    private LocalDateTime timestamp;

    private int status;
}
