package com.vargas.gestioninventario.service;

import com.vargas.gestioninventario.entity.TipoDocumento;
import java.util.List;

public interface TipoDocumentoService {
    List<TipoDocumento> findAll();
    TipoDocumento findById(Long id);
    TipoDocumento save(TipoDocumento tipoDocumento);
    void deleteById(Long id);
}