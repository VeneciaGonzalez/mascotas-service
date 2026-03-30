package com.veneciagonzalez.entrega3.mascotas.mascotas_service.controller;

import com.veneciagonzalez.entrega3.mascotas.mascotas_service.model.OrdenCompra;
import com.veneciagonzalez.entrega3.mascotas.mascotas_service.service.OrdenService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController 
@RequestMapping("/ordenes") 
public class OrdenController {

    @Autowired 
    private OrdenService ordenService; 

    // Devuelve todas las órdenes en memoria y la nueva orden creada --> http://localhost:8080/ordenes
    @GetMapping 
    public List<OrdenCompra> listarTodas() {
        return ordenService.consultaListaOrden();
    }


    // Filtra por ID -->  http://localhost:8080/ordenes/1
    @GetMapping("/{id}") //GET POR ID
    public OrdenCompra buscarPorId(@PathVariable Long id) {
        return ordenService.consultaOrdenesId(id);
    } 
    

    //================
    // GET
    // Se verifican ordenes por estado -->  http://localhost:8080/ordenes/estado/COMPLETADO
    @GetMapping("/estado/{estado}") 
    public ResponseEntity<?> obtenerOrdenPorEstado(@PathVariable String estado) {
        List<OrdenCompra> filtradas = ordenService.consultaListaOrden().stream()
                .filter(o -> o.getEstado().equalsIgnoreCase(estado))
                .toList();
        return ResponseEntity.ok(filtradas);
    }


    // POST
    // Se agrega nueva orden de compra a la lista,  método agregarOrdenCompra Service
    // add --> http://localhost:8080/ordenes
    @PostMapping 
    public ResponseEntity<OrdenCompra> crearOrden(@Valid @RequestBody OrdenCompra nuevaOrden) {

        OrdenCompra ordenGuardada = ordenService.agregaNuevaOrdenDeCompra(nuevaOrden);

        return ResponseEntity.status(HttpStatus.CREATED).body(ordenGuardada);
    }


    // PUT
    // Se ctualiza el estado --> http://localhost:8080/ordenes/1/estado  {"estado": "ENTREGADO"}
    @PutMapping("/{id}/estado") 
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String nuevoEstado = body.get("estado");
        OrdenCompra orden = ordenService.consultaOrdenesId(id);
        
        if (orden != null && nuevoEstado != null) {
            orden.setEstado(nuevoEstado);
            return ResponseEntity.ok(orden);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Orden no encontrada"));
    }



    
}