package com.veneciagonzalez.entrega3.mascotas.mascotas_service.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.veneciagonzalez.entrega3.mascotas.mascotas_service.dto.OrdenCompraRequestDTO;
import com.veneciagonzalez.entrega3.mascotas.mascotas_service.dto.OrdenCompraResponseDTO;
import com.veneciagonzalez.entrega3.mascotas.mascotas_service.entity.OrdenCompraEntity;
import com.veneciagonzalez.entrega3.mascotas.mascotas_service.repository.OrdenCompraRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrdenServiceImpl implements OrdenService {

    private final OrdenCompraRepository ordenCompraRepository;

    public OrdenServiceImpl(OrdenCompraRepository ordenCompraRepository) {
        this.ordenCompraRepository = ordenCompraRepository;
    }

    @Override
    public List<OrdenCompraResponseDTO> obtenerTodas() {
        log.info("Obteniendo todas las órdenes de compra");
        return ordenCompraRepository.findAll().stream().map(this::toDTO).toList();
    }

    @Override
    public OrdenCompraResponseDTO obtenerPorId(Long id) {
        log.info("Buscando orden con ID: {}", id);
        Optional<OrdenCompraEntity> orden = ordenCompraRepository.findById(id);
        return orden.map(this::toDTO).orElse(null);
    }

    @Override
    public List<OrdenCompraResponseDTO> buscarPorEstado(String estado) {
        log.info("Buscando órdenes por estado: {}", estado);
        return ordenCompraRepository.findByEstado(estado).stream().map(this::toDTO).toList();
    }

    @Override
    public List<OrdenCompraResponseDTO> buscarPorNombreMascota(String nombreMascota) {
        log.info("Buscando órdenes por mascota: {}", nombreMascota);
        return ordenCompraRepository.findByNombreMascota(nombreMascota).stream().map(this::toDTO).toList();
    }

    @Override
    public List<OrdenCompraResponseDTO> buscarPorNombreMascotaParcial(String nombreMascota) {
        log.info("Buscando órdenes por mascota parcial: {}", nombreMascota);
        return ordenCompraRepository.findByNombreMascotaContaining(nombreMascota).stream().map(this::toDTO).toList();
    }

    @Override
    public List<OrdenCompraResponseDTO> buscarPorProducto(String producto) {
        log.info("Buscando órdenes por producto: {}", producto);
        return ordenCompraRepository.findByProductoContaining(producto).stream().map(this::toDTO).toList();
    }

    @Override
    public List<OrdenCompraResponseDTO> obtenerActivas() {
        log.info("Obteniendo órdenes activas");
        return ordenCompraRepository.findByActivo(1).stream().map(this::toDTO).toList();
    }

    @Override
    public List<OrdenCompraResponseDTO> obtenerActivasOrdenadasPorFecha() {
        log.info("Obteniendo órdenes activas ordenadas por fecha");
        return ordenCompraRepository.findByActivoOrderByFechaOrdenAsc(1).stream().map(this::toDTO).toList();
    }

    @Override
    public List<OrdenCompraResponseDTO> buscarPorEstadoYActivo(String estado) {
        log.info("Buscando órdenes activas por estado: {}", estado);
        return ordenCompraRepository.findByEstadoAndActivo(estado, 1).stream().map(this::toDTO).toList();
    }

    @Override
    public List<OrdenCompraResponseDTO> buscarPorCantidadMayorA(Integer cantidad) {
        log.info("Buscando órdenes con cantidad mayor a: {}", cantidad);
        return ordenCompraRepository.findByCantidadGreaterThan(cantidad).stream().map(this::toDTO).toList();
    }

    @Override
    public List<OrdenCompraResponseDTO> buscarPorFechaDesde(LocalDateTime fecha) {
        log.info("Buscando órdenes desde: {}", fecha);
        return ordenCompraRepository.findByFechaOrdenAfter(fecha).stream().map(this::toDTO).toList();
    }

    @Override
    public List<OrdenCompraResponseDTO> buscarPorRangoFechas(LocalDateTime inicio, LocalDateTime fin) {
        log.info("Buscando órdenes entre {} y {}", inicio, fin);
        return ordenCompraRepository.findByFechaOrdenBetween(inicio, fin).stream().map(this::toDTO).toList();
    }

    @Override
    public List<OrdenCompraResponseDTO> buscarComplejo(String producto) {
        log.info("Búsqueda compleja: activas por producto: {}", producto);
        return ordenCompraRepository
                .findByActivoAndProductoContainingOrderByFechaOrdenAsc(1, producto)
                .stream().map(this::toDTO).toList();
    }

    @Override
    public OrdenCompraResponseDTO crearOrden(OrdenCompraRequestDTO request) {
        log.info("Creando nueva orden para mascota: {}", request.getNombreMascota());
        OrdenCompraEntity entity = new OrdenCompraEntity();
        entity.setNombreMascota(request.getNombreMascota());
        entity.setProducto(request.getProducto());
        entity.setCantidad(request.getCantidad());
        entity.setEstado(request.getEstado());
        entity.setFechaOrden(request.getFechaOrden());
        entity.setActivo(1);
        return toDTO(ordenCompraRepository.save(entity));
    }

    @Override
    public OrdenCompraResponseDTO actualizarOrden(Long id, OrdenCompraRequestDTO request) {
        log.info("Actualizando orden con ID: {}", id);
        Optional<OrdenCompraEntity> optional = ordenCompraRepository.findById(id);
        if (optional.isEmpty()) {
            log.warn("Orden con ID {} no encontrada para actualizar", id);
            return null;
        }
        OrdenCompraEntity entity = optional.get();
        entity.setNombreMascota(request.getNombreMascota());
        entity.setProducto(request.getProducto());
        entity.setCantidad(request.getCantidad());
        entity.setEstado(request.getEstado());
        entity.setFechaOrden(request.getFechaOrden());
        return toDTO(ordenCompraRepository.save(entity));
    }

    @Override
    public boolean eliminarOrden(Long id) {
        log.info("Eliminando orden con ID: {}", id);
        if (ordenCompraRepository.existsById(id)) {
            ordenCompraRepository.deleteById(id);
            return true;
        }
        log.warn("Orden con ID {} no encontrada para eliminar", id);
        return false;
    }

    @Override
    public boolean desactivarOrden(Long id) {
        log.info("Desactivando orden con ID: {}", id);
        Optional<OrdenCompraEntity> optional = ordenCompraRepository.findById(id);
        if (optional.isEmpty()) {
            log.warn("Orden con ID {} no encontrada para desactivar", id);
            return false;
        }
        OrdenCompraEntity entity = optional.get();
        entity.setActivo(0);
        ordenCompraRepository.save(entity);
        return true;
    }

    private OrdenCompraResponseDTO toDTO(OrdenCompraEntity entity) {
        return new OrdenCompraResponseDTO(
                entity.getId(),
                entity.getNombreMascota(),
                entity.getProducto(),
                entity.getCantidad(),
                entity.getEstado(),
                entity.getFechaOrden(),
                entity.getActivo()
        );
    }
}