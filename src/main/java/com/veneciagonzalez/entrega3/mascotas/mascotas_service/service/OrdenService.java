package com.veneciagonzalez.entrega3.mascotas.mascotas_service.service;

import java.time.LocalDateTime;
import java.util.List;

import com.veneciagonzalez.entrega3.mascotas.mascotas_service.dto.OrdenCompraRequestDTO;
import com.veneciagonzalez.entrega3.mascotas.mascotas_service.dto.OrdenCompraResponseDTO;

public interface OrdenService {

    List<OrdenCompraResponseDTO> obtenerTodas();

    OrdenCompraResponseDTO obtenerPorId(Long id);

    List<OrdenCompraResponseDTO> buscarPorEstado(String estado);

    List<OrdenCompraResponseDTO> buscarPorNombreMascota(String nombreMascota);

    List<OrdenCompraResponseDTO> buscarPorNombreMascotaParcial(String nombreMascota);

    List<OrdenCompraResponseDTO> buscarPorProducto(String producto);

    List<OrdenCompraResponseDTO> obtenerActivas();

    List<OrdenCompraResponseDTO> obtenerActivasOrdenadasPorFecha();

    List<OrdenCompraResponseDTO> buscarPorEstadoYActivo(String estado);

    List<OrdenCompraResponseDTO> buscarPorCantidadMayorA(Integer cantidad);

    List<OrdenCompraResponseDTO> buscarPorFechaDesde(LocalDateTime fecha);

    List<OrdenCompraResponseDTO> buscarPorRangoFechas(LocalDateTime inicio, LocalDateTime fin);

    List<OrdenCompraResponseDTO> buscarComplejo(String producto);

    OrdenCompraResponseDTO crearOrden(OrdenCompraRequestDTO request);

    OrdenCompraResponseDTO actualizarOrden(Long id, OrdenCompraRequestDTO request);

    boolean eliminarOrden(Long id);

    boolean desactivarOrden(Long id);
}