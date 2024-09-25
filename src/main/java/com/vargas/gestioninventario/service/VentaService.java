package com.vargas.gestioninventario.service;

import com.vargas.gestioninventario.dto.VentaSaveDTO;
import com.vargas.gestioninventario.entity.Venta;
import java.util.List;

public interface VentaService {
    List<Venta> findAll();
    Venta findById(Long id);
    Venta save(Venta venta);
    void deleteById(Long id);
    Integer obtenerUltimoCorrelativo();
    void registrarVenta(VentaSaveDTO ventaSaveDTO);
}
