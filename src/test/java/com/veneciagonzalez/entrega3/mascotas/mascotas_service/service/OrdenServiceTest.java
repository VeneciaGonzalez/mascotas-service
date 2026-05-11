package com.veneciagonzalez.entrega3.mascotas.mascotas_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.veneciagonzalez.entrega3.mascotas.mascotas_service.dto.OrdenCompraRequestDTO;
import com.veneciagonzalez.entrega3.mascotas.mascotas_service.dto.OrdenCompraResponseDTO;
import com.veneciagonzalez.entrega3.mascotas.mascotas_service.entity.OrdenCompraEntity;
import com.veneciagonzalez.entrega3.mascotas.mascotas_service.repository.OrdenCompraRepository;

// Extiende con Mockito para inyección automática de mocks
@ExtendWith(MockitoExtension.class)
public class OrdenServiceTest {

    // Inyecta automáticamente los mocks en el servicio
    @InjectMocks
    private OrdenServiceImpl ordenServicio;

    // Crea un mock del repositorio para aislar el servicio
    @Mock
    private OrdenCompraRepository ordenCompraRepository;

    @Test
    @DisplayName("Debe crear una orden y retornar el DTO con los datos correctos")
    public void crearOrdenTest() {
        // Arrange
        OrdenCompraRequestDTO request = new OrdenCompraRequestDTO(
                "Rabito", "Collar", 2, "Pendiente",
                LocalDateTime.now().plusDays(1));

        OrdenCompraEntity entityGuardada = new OrdenCompraEntity();
        entityGuardada.setId(1L);
        entityGuardada.setNombreMascota("Rabito");
        entityGuardada.setProducto("Collar");
        entityGuardada.setCantidad(2);
        entityGuardada.setEstado("Pendiente");
        entityGuardada.setFechaOrden(request.getFechaOrden());
        entityGuardada.setActivo(1);

        // Configura el mock para devolver la entity cuando se llame save
        when(ordenCompraRepository.save(any())).thenReturn(entityGuardada);

        // Act
        OrdenCompraResponseDTO resultado = ordenServicio.crearOrden(request);

        // Assert
        assertNotNull(resultado);
        assertEquals("Rabito", resultado.getNombreMascota());
    }

    @Test
    @DisplayName("Debe retornar null cuando no existe la orden buscada por ID")
    public void obtenerPorIdInexistenteTest() {
        // Arrange
        when(ordenCompraRepository.findById(99L))
                .thenReturn(java.util.Optional.empty());

        // Act
        OrdenCompraResponseDTO resultado = ordenServicio.obtenerPorId(99L);

        
        // Assert
        assertEquals(null, resultado);
    }
}