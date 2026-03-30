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


    // Devuelve todas las ordenes en memoria y la nueva orden creada --> http://localhost:8080/ordenes
    @GetMapping 
    public List<OrdenCompra> listarTodas() {

        return ordenService.consultaListaOrden();
    }


    // Filtra por ID -->  http://localhost:8080/ordenes/1
    @GetMapping("/{id}") //GET POR ID
    public OrdenCompra buscarPorId(@PathVariable Long id) {
        return ordenService.consultaOrdenesId(id);
    } 
    

    // Se verifican ordenes por estado -->  http://localhost:8080/ordenes/estado/COMPLETADO 
    // Se delega, el controller recibe, y llama al servico consultaEstadoOrden para que filtre 
    @GetMapping("/estado/{estado}")
    public ResponseEntity<?> obtenerOrdenPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(ordenService.consultaEstadoOrden(estado));
    }


    // POST
    // Se agrega nueva orden de compra a la lista,  método agregarOrdenCompra Service
    // add --> http://localhost:8080/ordenes
    //      {
    //     "id": 5,
    //     "nombreMascota": "Kura",
    //     "producto": "Shampoo para gatos",
    //     "cantidad": 0,
    //     "estado": "Pendiente"
    // }
    @PostMapping 
    public ResponseEntity<OrdenCompra> crearOrden(@Valid @RequestBody OrdenCompra nuevaOrden) {

        OrdenCompra ordenGuardada = ordenService.agregaNuevaOrdenDeCompra(nuevaOrden);

        return ResponseEntity.status(HttpStatus.CREATED).body(ordenGuardada);
    }


    // PUT
    // Se ctualiza el estado --> http://localhost:8080/ordenes/1/estado 
    // {"estado": "ENTREGADO"}
    @PutMapping("/{id}/estado") 
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String nuevoEstado = body.get("estado");
        // Primero se colsulta la orden Id = 1  -->    listaOrdenes.add(new OrdenCompra(1L, "Rabito",    "Alimento",  2, "Completado"));
        OrdenCompra orden = ordenService.consultaOrdenesId(id);
        
        if (orden != null && nuevoEstado != null) {
            // Luego se setae el nuevo estado
            orden.setEstado(nuevoEstado);

            return ResponseEntity.ok(orden);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Orden no encontrada"));
    }



    
}