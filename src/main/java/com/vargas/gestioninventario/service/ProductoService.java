package com.vargas.gestioninventario.service;

import com.vargas.gestioninventario.entity.Producto;
import java.util.List;

public interface ProductoService {
    List<Producto> findAll();
    Producto findById(Long id);
    Producto save(Producto producto);
    void deleteById(Long id);
}
