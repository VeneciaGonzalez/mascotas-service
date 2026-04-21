package com.veneciagonzalez.entrega3.mascotas.mascotas_service.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ordenes_compra")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdenCompraEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_mascota", nullable = false, length = 100)
    private String nombreMascota;

    @Column(nullable = false, length = 100)
    private String producto;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    @Column(name = "fecha_orden", nullable = false)
    private LocalDateTime fechaOrden;

    @Column(nullable = false)
    private Integer activo;
}