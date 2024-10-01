package com.vargas.gestioninventario.service;

import com.vargas.gestioninventario.dto.ProductoVentaDTO;
import com.vargas.gestioninventario.entity.ProductoTalla;
import java.util.List;

public interface ProductoTallaService {
    List<ProductoTalla> findAll();
    ProductoTalla findById(Long id);
    ProductoTalla save(ProductoTalla productoTalla);
    void deleteById(Long id);
    List<ProductoVentaDTO> buscarProductosDisponibles(String keyword);
    long contarTotalProductos();
}
