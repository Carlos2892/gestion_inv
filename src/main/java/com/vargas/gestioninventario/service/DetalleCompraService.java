package com.vargas.gestioninventario.service;

import com.vargas.gestioninventario.entity.DetalleCompra;
import java.util.List;

public interface DetalleCompraService {
    List<DetalleCompra> findAll();
    DetalleCompra findById(Long id);
    DetalleCompra save(DetalleCompra detalleCompra);
    void deleteById(Long id);
}
