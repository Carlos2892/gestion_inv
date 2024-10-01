package com.vargas.gestioninventario.service.impl;

import com.vargas.gestioninventario.dto.ProductoMasVendidoDTO;
import com.vargas.gestioninventario.entity.DetalleVenta;
import com.vargas.gestioninventario.entity.Inventario;
import com.vargas.gestioninventario.entity.ProductoTalla;
import com.vargas.gestioninventario.repository.DetalleVentaRepository;
import com.vargas.gestioninventario.repository.InventarioRepository;
import com.vargas.gestioninventario.service.DetalleVentaService;
import java.time.LocalDate;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class DetalleVentaServiceImpl implements DetalleVentaService {
    @Autowired
    private DetalleVentaRepository detalleVentaRepository;
    
    @Autowired
    private InventarioRepository inventarioRepository;

    @Override
    public List<DetalleVenta> findAll() {
        return detalleVentaRepository.findAll();
    }

    @Override
    public DetalleVenta findById(Long id) {
        return detalleVentaRepository.findById(id).orElse(null);
    }

    @Override
    public DetalleVenta save(DetalleVenta detalleVenta) {
        return detalleVentaRepository.save(detalleVenta);
    }

    @Override
    public void deleteById(Long id) {
        detalleVentaRepository.deleteById(id);
    }
    
    @Override
    public Double obtenerGananciasDelMes() {
        Double gananciasDelMes = detalleVentaRepository.obtenerGananciasDelMes();
        return gananciasDelMes != null ? gananciasDelMes : 0.0; // Evitar nulos
    }
    
    @Override
    public List<ProductoMasVendidoDTO> obtenerTop10ProductosMasVendidos() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Object[]> productosVendidos = detalleVentaRepository.obtenerTop10ProductosMasVendidos(pageable);

        // Convertimos la lista a DTO usando el stream y el convertToDTO
        return productosVendidos.getContent().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<ProductoMasVendidoDTO> obtenerProductosMasVendidosPorFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Object[]> productosVendidos = detalleVentaRepository.obtenerProductosMasVendidosPorFechas(fechaInicio, fechaFin);

        // Convertimos la lista a DTO usando el stream y el convertToDTO
        return productosVendidos.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<ProductoMasVendidoDTO> obtenerProductosSinVentasPorFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Object[]> productosSinVentas = detalleVentaRepository.obtenerProductosSinVentas(fechaInicio, fechaFin);

        return productosSinVentas.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    private ProductoMasVendidoDTO convertToDTO(Object[] row) {
        ProductoTalla productoTalla = (ProductoTalla) row[0];
        Long totalCantidadVendida = (Long) row[1];
        Long totalVentas = (Long) row[2];

        // El importe de ventas es opcional, así que verificamos si está en los datos
        Double totalImporteVentas = (row.length > 3) ? (Double) row[3] : null;
        Long idProductoTalla = productoTalla.getId();

        Inventario inventario = inventarioRepository.findByProductoTalla(productoTalla);
        
        int stockActual = inventario.getStockActual();
        
        // Creamos la descripción completa
        String producto = productoTalla.getProducto().getDescripcion() 
                                    + " - " 
                                    + productoTalla.getProducto().getMarca().getNombre() 
                                    + " Talla: " 
                                    + productoTalla.getTalla().getNombre();

        // Creamos el DTO y lo retornamos
        return new ProductoMasVendidoDTO(
            idProductoTalla,
            producto,
            totalCantidadVendida,
            totalVentas,
            totalImporteVentas, // Si es null, no afecta el DTO
            stockActual
        );
    }
}
