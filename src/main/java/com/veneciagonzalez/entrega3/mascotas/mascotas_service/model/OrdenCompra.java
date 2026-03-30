package com.veneciagonzalez.entrega3.mascotas.mascotas_service.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdenCompra {

    //Se validan los datos ingresados
    // @NotNull --> campos no nulos, puede enviar string  vacío "", pero no que la variable sea null. 
    // @NotBlank -->  Se valida string , texto no nulo, longitud sea mayor a cero sin eespacios en blanco
    @NotNull(message = "El ID no puede ser nulo")
    private Long id;
    //private int id; 

    @NotBlank(message = "Debe ingresar el nombre de la mascota")
    private String nombreMascota;

    @NotBlank(message = "Debe ingresar el nombre del producto")
    private String producto;

    @Min(value = 1, message = "Debe ingresar cantidad")
    private int cantidad;

    @NotBlank(message = "Debe ingresar el nombre de la mascota el estado")
    private String estado; 
}