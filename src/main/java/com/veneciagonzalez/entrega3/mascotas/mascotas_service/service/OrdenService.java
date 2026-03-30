package com.veneciagonzalez.entrega3.mascotas.mascotas_service.service;

import com.veneciagonzalez.entrega3.mascotas.mascotas_service.model.OrdenCompra;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdenService {
    
    private List<OrdenCompra> listaOrdenes = new ArrayList<>();

    public OrdenService() {
        // Tipo Long para id, para escalabilidad, sufijo L para indicar valor literal, largo 64 bits de Java
        listaOrdenes.add(new OrdenCompra(1L, "Rabito",    "Alimento",  2, "Completado"));
        listaOrdenes.add(new OrdenCompra(2L, "Caina",     "Arena",     1, "Pendiente"));
        listaOrdenes.add(new OrdenCompra(3L, "Tuché",     "Juguete",   3, "Enviado"));
        listaOrdenes.add(new OrdenCompra(4L, "Rumberito", "Collar",    1, "Procesando"));

        // listaOrdenes.add(new OrdenCompra(1, "Rabito",    "Alimento",    2, "Completado"));
        // listaOrdenes.add(new OrdenCompra(2, "Caina",     "Arena",       1, "Pendiente"));
        // listaOrdenes.add(new OrdenCompra(3, "Tuché",     "Juguete",     3, "Enviado"));
        // listaOrdenes.add(new OrdenCompra(4, "Rumberito", "Collar",      1, "Procesando"));

    }

    //Devuelve lista de Ordenes 
    public List<OrdenCompra> consultaListaOrden() {
        return listaOrdenes;
    }

    //Devuelve orden por Id
    public OrdenCompra consultaOrdenesId(Long id) {

        return listaOrdenes.stream()

        .filter(o -> o.getId().equals(id))
        .findFirst()
        .orElse(null);
    }

    
    //Devuelve total ordenes segun estado  
    public List<OrdenCompra> consultaEstadoOrden(String estado) {
        return listaOrdenes.stream()
                .filter(c -> c.getEstado().equalsIgnoreCase(estado))
                .collect(Collectors.toList());
    }

    //Agrega nueva orden
    public OrdenCompra agregaNuevaOrdenDeCompra(OrdenCompra ordenNueva){
        listaOrdenes.add(ordenNueva);
        return ordenNueva;
    }
}