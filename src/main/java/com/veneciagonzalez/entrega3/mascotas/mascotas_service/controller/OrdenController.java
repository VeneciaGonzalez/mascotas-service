package com.veneciagonzalez.entrega3.mascotas.mascotas_service.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.veneciagonzalez.entrega3.mascotas.mascotas_service.dto.OrdenCompraRequestDTO;
import com.veneciagonzalez.entrega3.mascotas.mascotas_service.dto.OrdenCompraResponseDTO;
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
    public ResponseEntity<List<OrdenCompraResponseDTO>> obtenerTodas() {
        log.info("GET /ordenes - Obteniendo todas las órdenes");
        return ResponseEntity.ok(ordenService.obtenerTodas());
    }

    // GET por ID --> http://localhost:8080/ordenes/1
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        log.info("GET /ordenes/{} - Buscando orden por ID", id);
        OrdenCompraResponseDTO orden = ordenService.obtenerPorId(id);
        if (orden == null) {
            log.warn("Orden con ID {} no encontrada", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orden);
    }

    // GET por estado --> http://localhost:8080/ordenes/estado?estado=Completado
    @GetMapping("/estado")
    public ResponseEntity<List<OrdenCompraResponseDTO>> buscarPorEstado(@RequestParam String estado) {
        log.info("GET /ordenes/estado - Buscando por estado: {}", estado);
        return ResponseEntity.ok(ordenService.buscarPorEstado(estado));
    }

    // GET por nombre mascota exacto --> http://localhost:8080/ordenes/mascota?nombre=Rabito
    @GetMapping("/mascota")
    public ResponseEntity<List<OrdenCompraResponseDTO>> buscarPorNombreMascota(@RequestParam String nombre) {
        log.info("GET /ordenes/mascota - Buscando por mascota: {}", nombre);
        return ResponseEntity.ok(ordenService.buscarPorNombreMascota(nombre));
    }

    // GET por nombre mascota parcial --> http://localhost:8080/ordenes/mascota-parcial?nombre=Ra
    @GetMapping("/mascota-parcial")
    public ResponseEntity<List<OrdenCompraResponseDTO>> buscarPorNombreMascotaParcial(@RequestParam String nombre) {
        log.info("GET /ordenes/mascota-parcial - Buscando por mascota parcial: {}", nombre);
        return ResponseEntity.ok(ordenService.buscarPorNombreMascotaParcial(nombre));
    }

    // GET por producto --> http://localhost:8080/ordenes/producto?nombre=Collar
    @GetMapping("/producto")
    public ResponseEntity<List<OrdenCompraResponseDTO>> buscarPorProducto(@RequestParam String nombre) {
        log.info("GET /ordenes/producto - Buscando por producto: {}", nombre);
        return ResponseEntity.ok(ordenService.buscarPorProducto(nombre));
    }

    // GET activas --> http://localhost:8080/ordenes/activas
    @GetMapping("/activas")
    public ResponseEntity<List<OrdenCompraResponseDTO>> obtenerActivas() {
        log.info("GET /ordenes/activas - Obteniendo órdenes activas");
        return ResponseEntity.ok(ordenService.obtenerActivas());
    }

    // GET activas ordenadas por fecha --> http://localhost:8080/ordenes/activas-ordenadas
    @GetMapping("/activas-ordenadas")
    public ResponseEntity<List<OrdenCompraResponseDTO>> obtenerActivasOrdenadasPorFecha() {
        log.info("GET /ordenes/activas-ordenadas - Obteniendo órdenes activas ordenadas");
        return ResponseEntity.ok(ordenService.obtenerActivasOrdenadasPorFecha());
    }

    // GET activas por estado --> http://localhost:8080/ordenes/estado-activo?estado=Completado
    @GetMapping("/estado-activo")
    public ResponseEntity<List<OrdenCompraResponseDTO>> buscarPorEstadoYActivo(@RequestParam String estado) {
        log.info("GET /ordenes/estado-activo - estado: {}", estado);
        return ResponseEntity.ok(ordenService.buscarPorEstadoYActivo(estado));
    }

    // GET por cantidad mayor a --> http://localhost:8080/ordenes/cantidad?min=2
    @GetMapping("/cantidad")
    public ResponseEntity<List<OrdenCompraResponseDTO>> buscarPorCantidad(@RequestParam Integer min) {
        log.info("GET /ordenes/cantidad - cantidad mayor a: {}", min);
        return ResponseEntity.ok(ordenService.buscarPorCantidadMayorA(min));
    }

    // GET desde fecha --> http://localhost:8080/ordenes/fecha?fecha=2026-05-01&hora=00:00
    @GetMapping("/fecha")
    public ResponseEntity<List<OrdenCompraResponseDTO>> buscarPorFechaDesde(
            @RequestParam String fecha,
            @RequestParam(defaultValue = "00:00") String hora) {
        log.info("GET /ordenes/fecha - desde: {} {}", fecha, hora);
        LocalDateTime desde = LocalDateTime.parse(fecha + "T" + hora + ":00");
        return ResponseEntity.ok(ordenService.buscarPorFechaDesde(desde));
    }

    // GET rango fechas --> http://localhost:8080/ordenes/fecha-rango?fechaInicio=2026-05-01&fechaFin=2026-09-01
    @GetMapping("/fecha-rango")
    public ResponseEntity<List<OrdenCompraResponseDTO>> buscarPorRangoFechas(
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin,
            @RequestParam(defaultValue = "00:00") String horaInicio,
            @RequestParam(defaultValue = "23:59") String horaFin) {
        log.info("GET /ordenes/fecha-rango - Entre {} {} y {} {}", fechaInicio, horaInicio, fechaFin, horaFin);
        LocalDateTime inicio = LocalDateTime.parse(fechaInicio + "T" + horaInicio + ":00");
        LocalDateTime fin = LocalDateTime.parse(fechaFin + "T" + horaFin + ":00");
        return ResponseEntity.ok(ordenService.buscarPorRangoFechas(inicio, fin));
    }

    // GET búsqueda compleja --> http://localhost:8080/ordenes/buscar-complejo?producto=Collar
    @GetMapping("/buscar-complejo")
    public ResponseEntity<List<OrdenCompraResponseDTO>> buscarComplejo(@RequestParam String producto) {
        log.info("GET /ordenes/buscar-complejo - producto: {}", producto);
        return ResponseEntity.ok(ordenService.buscarComplejo(producto));
    }

    // POST crear --> http://localhost:8080/ordenes
    @PostMapping
    public ResponseEntity<OrdenCompraResponseDTO> crearOrden(@Valid @RequestBody OrdenCompraRequestDTO request) {
        log.info("POST /ordenes - Creando orden para mascota: {}", request.getNombreMascota());
        return ResponseEntity.status(HttpStatus.CREATED).body(ordenService.crearOrden(request));
    }

    // PUT actualizar --> http://localhost:8080/ordenes/1
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarOrden(@PathVariable Long id,
            @Valid @RequestBody OrdenCompraRequestDTO request) {
        log.info("PUT /ordenes/{} - Actualizando orden", id);
        OrdenCompraResponseDTO actualizada = ordenService.actualizarOrden(id, request);
        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizada);
    }

    // DELETE físico --> http://localhost:8080/ordenes/1
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarOrden(@PathVariable Long id) {
        log.info("DELETE /ordenes/{} - Eliminando orden", id);
        boolean eliminada = ordenService.eliminarOrden(id);
        if (!eliminada) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    // PUT desactivar --> http://localhost:8080/ordenes/1/desactivar
    @PutMapping("/{id}/desactivar")
    public ResponseEntity<?> desactivarOrden(@PathVariable Long id) {
        log.info("PUT /ordenes/{}/desactivar - Desactivando orden", id);
        boolean desactivada = ordenService.desactivarOrden(id);
        if (!desactivada) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}