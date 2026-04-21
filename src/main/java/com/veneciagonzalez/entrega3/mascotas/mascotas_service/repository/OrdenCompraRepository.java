package com.veneciagonzalez.entrega3.mascotas.mascotas_service.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.veneciagonzalez.entrega3.mascotas.mascotas_service.entity.OrdenCompraEntity;

@Repository
public interface OrdenCompraRepository extends JpaRepository<OrdenCompraEntity, Long> {

    // Estado  
    List<OrdenCompraEntity> findByEstado(String estado);

    // Nombre mascota  
    List<OrdenCompraEntity> findByNombreMascota(String nombreMascota);

    // Nombre mascota parcial
    List<OrdenCompraEntity> findByNombreMascotaContaining(String nombreMascota);

    // Producto parcial
    List<OrdenCompraEntity> findByProductoContaining(String producto);

    // Solo activas
    List<OrdenCompraEntity> findByActivo(Integer activo);

    // Activas por fecha
    List<OrdenCompraEntity> findByActivoOrderByFechaOrdenAsc(Integer activo);

    // Estado y activo
    List<OrdenCompraEntity> findByEstadoAndActivo(String estado, Integer activo);

    // Cantidad mayor a
    List<OrdenCompraEntity> findByCantidadGreaterThan(Integer cantidad);

    // Ordenes fecha
    List<OrdenCompraEntity> findByFechaOrdenAfter(LocalDateTime fecha);

    // Ordenes rango fechas
    List<OrdenCompraEntity> findByFechaOrdenBetween(LocalDateTime inicio, LocalDateTime fin);

    // Activas por producto ordenadas y fecha
    List<OrdenCompraEntity> findByActivoAndProductoContainingOrderByFechaOrdenAsc(Integer activo, String producto);
}