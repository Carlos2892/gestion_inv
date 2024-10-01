package com.vargas.gestioninventario.service;

import com.vargas.gestioninventario.entity.Talla;
import java.util.List;

public interface TallaService {
    List<Talla> findAll();
    Talla findById(Long id);
    Talla save(Talla talla);
    void deleteById(Long id);
    List<Talla> findByEstadoActivo();
}
