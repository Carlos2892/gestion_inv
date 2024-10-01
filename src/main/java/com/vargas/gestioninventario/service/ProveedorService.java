package com.vargas.gestioninventario.service;

import com.vargas.gestioninventario.dto.ProveedorDTO;
import com.vargas.gestioninventario.entity.Proveedor;
import java.util.List;

public interface ProveedorService {
    List<Proveedor> findAll();
    Proveedor findById(Long id);
    Proveedor save(ProveedorDTO proveedorDTO);
    Proveedor update(Long id, ProveedorDTO proveedorDTO);
    void deleteById(Long id);
}
