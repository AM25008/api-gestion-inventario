package com.grupo7.GestionInventarioAPI.controller;

import com.grupo7.GestionInventarioAPI.dto.response.MovimientoInventarioResponseDTO;
import com.grupo7.GestionInventarioAPI.model.enums.TipoMovimiento;
import com.grupo7.GestionInventarioAPI.service.impl.MovimientoInventarioServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovimientoInventarioController.class)
class MovimientoInventarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MovimientoInventarioServiceImpl service;

    @Test
    void obtenerTodos_Retorna200YJson() throws Exception {
        MovimientoInventarioResponseDTO movimiento = MovimientoInventarioResponseDTO.builder()
                .idMovimiento(1)
                .producto("Monitor")
                .tipoMovimiento(TipoMovimiento.ENTRADA)
                .cantidad(5)
                .build();
        when(service.obtenerTodos()).thenReturn(List.of(movimiento));

        mockMvc.perform(get("/api/movimientos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].producto").value("Monitor"));
    }
}
