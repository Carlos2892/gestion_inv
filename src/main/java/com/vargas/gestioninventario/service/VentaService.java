package com.vargas.gestioninventario.service;

import com.vargas.gestioninventario.dto.VentaDTO;
import com.vargas.gestioninventario.dto.VentaSaveDTO;
import com.vargas.gestioninventario.entity.Venta;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

public interface VentaService {
    List<Venta> findAll();
    List<VentaDTO> listarVentas(); 
    Venta findById(Long id);
    Venta save(Venta venta);
    void deleteById(Long id);
    Integer obtenerUltimoCorrelativo();
    Venta registrarVenta(VentaSaveDTO ventaSaveDTO);
    ByteArrayInputStream generarPdfVenta(Long compraId);
    long contarVentasDelDia();
    Double obtenerTotalVentasDelMes();
    List<Object[]> obtenerVentasMensualesDelAnio();
    List<Map<String, Object>> obtenerVentasPorCategoriaDelMes();
}
