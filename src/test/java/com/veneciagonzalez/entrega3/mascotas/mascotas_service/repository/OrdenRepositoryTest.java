package com.veneciagonzalez.entrega3.mascotas.mascotas_service.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.test.context.ActiveProfiles;
import com.veneciagonzalez.entrega3.mascotas.mascotas_service.entity.OrdenCompraEntity;


@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class OrdenRepositoryTest {

    @Autowired
    private OrdenCompraRepository ordenCompraRepository;

    @Test
    @DisplayName("Debe guardar una orden y retornar con ID generado")
    public void guardarOrdenTest() {
        // Arrange
        OrdenCompraEntity orden = new OrdenCompraEntity();
        orden.setNombreMascota("Rabito");
        orden.setProducto("Collar");
        orden.setCantidad(2);
        orden.setEstado("Pendiente");
        orden.setFechaOrden(LocalDateTime.now().plusDays(1));
        orden.setActivo(1);

        // Act
        OrdenCompraEntity resultado = ordenCompraRepository.save(orden);

        // Assert
        assertNotNull(resultado.getId());
        assertEquals("Rabito", resultado.getNombreMascota());
    }

    @Test
    @DisplayName("Debe encontrar órdenes por estado")
    public void buscarPorEstadoTest() {
        // Arrange
        OrdenCompraEntity orden = new OrdenCompraEntity();
        orden.setNombreMascota("Firulais");
        orden.setProducto("Comida");
        orden.setCantidad(1);
        orden.setEstado("Completado");
        orden.setFechaOrden(LocalDateTime.now().plusDays(1));
        orden.setActivo(1);
        ordenCompraRepository.save(orden);

        // Act
        List<OrdenCompraEntity> resultado = ordenCompraRepository.findByEstado("Completado");

        // Assert
        assertNotNull(resultado);
        assertEquals("Completado", resultado.get(0).getEstado());
    }
}