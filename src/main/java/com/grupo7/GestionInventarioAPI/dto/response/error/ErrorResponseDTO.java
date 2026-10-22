package com.grupo7.GestionInventarioAPI.dto.response.error;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter @Builder
public class ErrorResponseDTO {

    private String error;

    private String mensaje;

    private LocalDateTime timestamp;

    private int status;
}
