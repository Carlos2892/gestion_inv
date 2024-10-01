package com.vargas.gestioninventario.service;

import com.vargas.gestioninventario.dto.ProductoMasVendidoDTO;
import com.vargas.gestioninventario.entity.DetalleVenta;
import java.time.LocalDate;
import java.util.List;

public interface DetalleVentaService {
    List<DetalleVenta> findAll();
    DetalleVenta findById(Long id);
    DetalleVenta save(DetalleVenta detalleVenta);
    void deleteById(Long id);
    Double obtenerGananciasDelMes();
    List<ProductoMasVendidoDTO> obtenerTop10ProductosMasVendidos();
    List<ProductoMasVendidoDTO> obtenerProductosMasVendidosPorFechas(LocalDate fechaInicio, LocalDate fechaFin);
    List<ProductoMasVendidoDTO> obtenerProductosSinVentasPorFechas(LocalDate fechaInicio, LocalDate fechaFin);
}
