package com.grupo7.GestionInventarioAPI.controller;

import com.grupo7.GestionInventarioAPI.dto.response.ProveedorResponseDTO;
import com.grupo7.GestionInventarioAPI.service.ProveedorService;
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

@WebMvcTest(ProveedorController.class)
class ProveedorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProveedorService proveedorService;

    @Test
    void listar_Retorna200YJson() throws Exception {
        ProveedorResponseDTO proveedor = new ProveedorResponseDTO(1, "Proveedor Uno", "contacto@example.com", "555-0100", "Calle 1");
        when(proveedorService.listarActivos()).thenReturn(List.of(proveedor));

        mockMvc.perform(get("/api/proveedores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreProveedor").value("Proveedor Uno"));
    }
}
