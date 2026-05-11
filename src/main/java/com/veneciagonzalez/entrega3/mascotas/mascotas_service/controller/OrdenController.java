
package com.veneciagonzalez.entrega3.mascotas.mascotas_service.controller;

//import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.veneciagonzalez.entrega3.mascotas.mascotas_service.dto.OrdenCompraRequestDTO;
import com.veneciagonzalez.entrega3.mascotas.mascotas_service.dto.OrdenCompraResponseDTO;
import com.veneciagonzalez.entrega3.mascotas.mascotas_service.exception.OrdenNotFoundException;
import com.veneciagonzalez.entrega3.mascotas.mascotas_service.service.OrdenService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/ordenes")
public class OrdenController {

    private final OrdenService ordenService;

    public OrdenController(OrdenService ordenService) {
        this.ordenService = ordenService;
    }

    // GET todas --> http://localhost:8080/ordenes
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<OrdenCompraResponseDTO>>> obtenerTodas() {
        log.info("GET /ordenes - Obteniendo todas las órdenes");

        List<EntityModel<OrdenCompraResponseDTO>> ordenes = ordenService.obtenerTodas()
                .stream()
                .map(orden -> EntityModel.of(orden,
                        linkTo(methodOn(this.getClass()).obtenerPorId(orden.getId())).withSelfRel(),
                        linkTo(methodOn(this.getClass()).obtenerTodas()).withRel("all-ordenes")))
                .toList();

        CollectionModel<EntityModel<OrdenCompraResponseDTO>> recursos =
                CollectionModel.of(ordenes,
                        linkTo(methodOn(this.getClass()).obtenerTodas()).withSelfRel());

        return ResponseEntity.ok(recursos);
    }

    // GET por ID --> http://localhost:8080/ordenes/1
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<OrdenCompraResponseDTO>> obtenerPorId(@PathVariable Long id) {
        log.info("GET /ordenes/{} - Buscando orden por ID", id);

        OrdenCompraResponseDTO orden = ordenService.obtenerPorId(id);
        if (orden == null) {
            throw new OrdenNotFoundException("Orden no encontrada con ID: " + id);
        }

        EntityModel<OrdenCompraResponseDTO> recurso = EntityModel.of(orden,
                linkTo(methodOn(this.getClass()).obtenerPorId(id)).withSelfRel(),
                linkTo(methodOn(this.getClass()).obtenerTodas()).withRel("all-ordenes"));

        return ResponseEntity.ok(recurso);
    }

    // POST crear --> http://localhost:8080/ordenes
    @PostMapping
    public ResponseEntity<EntityModel<OrdenCompraResponseDTO>> crearOrden(
            @Valid @RequestBody OrdenCompraRequestDTO request) {
        log.info("POST /ordenes - Creando orden para mascota: {}", request.getNombreMascota());

        OrdenCompraResponseDTO creada = ordenService.crearOrden(request);

        EntityModel<OrdenCompraResponseDTO> recurso = EntityModel.of(creada,
                linkTo(methodOn(this.getClass()).obtenerPorId(creada.getId())).withSelfRel(),
                linkTo(methodOn(this.getClass()).obtenerTodas()).withRel("all-ordenes"));

        return ResponseEntity.status(HttpStatus.CREATED).body(recurso);
    }

    // PUT actualizar --> http://localhost:8080/ordenes/1
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<OrdenCompraResponseDTO>> actualizarOrden(
            @PathVariable Long id,
            @Valid @RequestBody OrdenCompraRequestDTO request) {
        log.info("PUT /ordenes/{} - Actualizando orden", id);

        OrdenCompraResponseDTO actualizada = ordenService.actualizarOrden(id, request);
        if (actualizada == null) {
            throw new OrdenNotFoundException("Orden no encontrada con ID: " + id);
        }

        EntityModel<OrdenCompraResponseDTO> recurso = EntityModel.of(actualizada,
                linkTo(methodOn(this.getClass()).obtenerPorId(id)).withSelfRel(),
                linkTo(methodOn(this.getClass()).obtenerTodas()).withRel("all-ordenes"));

        return ResponseEntity.ok(recurso);
    }

    // DELETE físico --> http://localhost:8080/ordenes/1
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarOrden(@PathVariable Long id) {
        log.info("DELETE /ordenes/{} - Eliminando orden", id);
        boolean eliminada = ordenService.eliminarOrden(id);
        if (!eliminada) {
            throw new OrdenNotFoundException("Orden no encontrada con ID: " + id);
        }
        return ResponseEntity.noContent().build();
    }

    // PUT desactivar --> http://localhost:8080/ordenes/1/desactivar
    @PutMapping("/{id}/desactivar")
    public ResponseEntity<?> desactivarOrden(@PathVariable Long id) {
        log.info("PUT /ordenes/{}/desactivar - Desactivando orden", id);
        boolean desactivada = ordenService.desactivarOrden(id);
        if (!desactivada) {
            throw new OrdenNotFoundException("Orden no encontrada con ID: " + id);
        }
        return ResponseEntity.noContent().build();
    }

    // GET por estado --> http://localhost:8080/ordenes/estado?estado=Completado
    @GetMapping("/estado")
    public ResponseEntity<List<OrdenCompraResponseDTO>> buscarPorEstado(@RequestParam String estado) {
        log.info("GET /ordenes/estado - Buscando por estado: {}", estado);
        return ResponseEntity.ok(ordenService.buscarPorEstado(estado));
    }

    // GET activas --> http://localhost:8080/ordenes/activas
    @GetMapping("/activas")
    public ResponseEntity<List<OrdenCompraResponseDTO>> obtenerActivas() {
        log.info("GET /ordenes/activas - Obteniendo órdenes activas");
        return ResponseEntity.ok(ordenService.obtenerActivas());
    }
}