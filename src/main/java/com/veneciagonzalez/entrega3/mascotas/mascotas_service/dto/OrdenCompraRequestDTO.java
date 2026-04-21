package com.veneciagonzalez.entrega3.mascotas.mascotas_service.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdenCompraRequestDTO {

    @NotBlank(message = "Debe ingresar el nombre de la mascota.")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres.")
    private String nombreMascota;

    @NotBlank(message = "Debe ingresar el producto.")
    @Size(min = 2, max = 100, message = "El producto debe tener entre 2 y 100 caracteres.")
    private String producto;

    @NotNull(message = "Debe ingresar la cantidad.")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0.")
    private Integer cantidad;

    @NotBlank(message = "Debe ingresar el estado de la orden.")
    private String estado;

    @NotNull(message = "Debe ingresar la fecha de la orden.")
    @Future(message = "La fecha de la orden debe ser futura.")
    private LocalDateTime fechaOrden;
}