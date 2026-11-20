package com.grupo7.GestionInventarioAPI.controller;

import com.grupo7.GestionInventarioAPI.dto.response.UsuarioResponseDTO;
import com.grupo7.GestionInventarioAPI.model.enums.RolUsuario;
import com.grupo7.GestionInventarioAPI.service.impl.UsuarioServiceImpl;
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

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsuarioServiceImpl usuarioService;

    @Test
    void obtenerTodos_Retorna200YJson() throws Exception {
        UsuarioResponseDTO usuario = new UsuarioResponseDTO(1, "Ana", "López", RolUsuario.ADMIN);
        when(usuarioService.obtenerTodos(1)).thenReturn(List.of(usuario));

        mockMvc.perform(get("/api/usuarios").param("user", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreUsuario").value("Ana"));
    }
}
