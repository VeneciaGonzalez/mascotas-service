package com.veneciagonzalez.entrega3.mascotas.mascotas_service.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;


import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.veneciagonzalez.entrega3.mascotas.mascotas_service.dto.OrdenCompraResponseDTO;
import com.veneciagonzalez.entrega3.mascotas.mascotas_service.service.OrdenServiceImpl;

// entorno de prueba solo para el controlador
@WebMvcTest(OrdenController.class)
public class OrdenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Mock del servicio para aislar el controlador
    //@MockBean
    @MockitoBean
    private OrdenServiceImpl ordenServicioMock;

    @Test
    @DisplayName("Debe retornar lista de órdenes con status 200 y estructura HATEOAS")
    public void obtenerTodasTest() throws Exception {
        // Arrange
        OrdenCompraResponseDTO orden1 = new OrdenCompraResponseDTO(
                1L, "Rabito", "Collar", 2, "Pendiente",
                LocalDateTime.now().plusDays(1), 1);

        OrdenCompraResponseDTO orden2 = new OrdenCompraResponseDTO(
                2L, "Firulais", "Comida", 1, "Completado",
                LocalDateTime.now().plusDays(2), 1);

        List<OrdenCompraResponseDTO> ordenes = Arrays.asList(orden1, orden2);
        when(ordenServicioMock.obtenerTodas()).thenReturn(ordenes);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/ordenes"))
                .andExpect(status().isOk())
                // Verifica estructura HATEOAS - embedded list
                .andExpect(jsonPath("$._embedded.ordenCompraResponseDTOList").exists())
                .andExpect(jsonPath("$._embedded.ordenCompraResponseDTOList[0].nombreMascota").value("Rabito"))
                .andExpect(jsonPath("$._embedded.ordenCompraResponseDTOList[1].nombreMascota").value("Firulais"))
                // Verifica que existan los self links
                .andExpect(jsonPath("$._links.self").exists());
    }

    @Test
    @DisplayName("Debe retornar 404 cuando la orden no existe")
    public void obtenerPorIdNoEncontradoTest() throws Exception {
        // Arrange
        when(ordenServicioMock.obtenerPorId(99L)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/ordenes/99"))
                .andExpect(status().isNotFound());
    }
}