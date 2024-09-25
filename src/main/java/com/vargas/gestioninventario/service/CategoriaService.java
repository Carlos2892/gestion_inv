package com.vargas.gestioninventario.service;

import com.vargas.gestioninventario.entity.Categoria;
import java.util.List;

public interface CategoriaService {
    List<Categoria> findAll();
    Categoria findById(Long id);
    Categoria save(Categoria categoria);
    void deleteById(Long id);
    List<Categoria> findByEstadoActivo();
}
