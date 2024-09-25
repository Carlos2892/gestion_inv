package com.vargas.gestioninventario.service;

import com.vargas.gestioninventario.entity.Marca;
import java.util.List;

public interface MarcaService {
    List<Marca> findAll();
    Marca findById(Long id);
    Marca save(Marca marca);
    void deleteById(Long id);
}
