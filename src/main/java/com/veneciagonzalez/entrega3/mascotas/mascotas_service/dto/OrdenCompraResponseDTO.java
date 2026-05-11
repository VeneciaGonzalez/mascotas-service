
package com.veneciagonzalez.entrega3.mascotas.mascotas_service.dto;

import java.time.LocalDateTime;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)// Se agrega EqualsAndHashCode(callSuper = false) porque la clase hereda de
// RepresentationModel de Spring HATEOAS. De esta forma Lombok no incluye los atributos de la superclase (links HATEOAS) en los métodos equals() y
// hashCode(), evitando warnings y manteniendo la comparación solo sobre los datos propios del DTO.
public class OrdenCompraResponseDTO extends RepresentationModel<OrdenCompraResponseDTO> { 
    // Se agregan los enlaces HATEOAS.  estee DTO ahora hereeda de  RepresentationModel Y esa superclase tiene 
    // atributos internos relacionados con _links. La idea más importante de todas
    // REST “normal”: Cliente conoce las URLs. REST con HATEOAS: La API guía al cliente mediante links. Y ESO es literalmente el corazón de HATEOAS.

    private Long id;
    private String nombreMascota;
    private String producto;
    private Integer cantidad;
    private String estado;
    private LocalDateTime fechaOrden;
    private Integer activo;
}