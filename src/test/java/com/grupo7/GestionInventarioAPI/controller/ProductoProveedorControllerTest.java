package com.grupo7.GestionInventarioAPI.controller;

import com.grupo7.GestionInventarioAPI.dto.response.ProductoProveedorResponseDTO;
import com.grupo7.GestionInventarioAPI.service.impl.ProductoProveedorServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductoProveedorController.class)
class ProductoProveedorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductoProveedorServiceImpl service;

    @Test
    void obtenerTodos_Retorna200YJson() throws Exception {
        ProductoProveedorResponseDTO asociacion = ProductoProveedorResponseDTO.builder()
                .idProductoProveedor(1)
                .producto("Monitor")
                .proveedor("Proveedor Uno")
                .precioCompra(new BigDecimal("100.00"))
                .build();
        when(service.obtenerTodos()).thenReturn(List.of(asociacion));

        mockMvc.perform(get("/api/producto-proveedor"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].producto").value("Monitor"));
    }
}
