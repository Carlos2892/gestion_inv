package com.vargas.gestioninventario.service;

import com.vargas.gestioninventario.entity.Rol;
import java.util.List;

public interface RolService {
    List<Rol> findAll();
    Rol findById(Long id);
    Rol save(Rol rol);
    void deleteById(Long id);
}
