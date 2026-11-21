package com.grupo7.GestionInventarioAPI.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("API Gestión Inventario")
                        .version("1.0.1")
                        .description("""
                                API Rest de Gestión de Inventarios.
                                El sistema administra productos y sus categorías, los proveedores, usuarios 
                                y los movimientos de inventario hechos.
                                """)
                        .contact(new Contact()
                                .name("POO Grupo 7")
                                .email("am25008@ues.edu.sv")));
    }
}
