package com.veneciagonzalez.entrega3.mascotas.mascotas_service.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdenCompraResponseDTO {

    private Long id;
    private String nombreMascota;
    private String producto;
    private Integer cantidad;
    private String estado;
    private LocalDateTime fechaOrden;
    private Integer activo;
}