package com.vargas.gestioninventario.service;

import com.vargas.gestioninventario.entity.TipoComprobante;
import java.util.List;

public interface TipoComprobanteService {
    List<TipoComprobante> findAll();
    TipoComprobante findById(Long id);
    TipoComprobante save(TipoComprobante tipoComprobante);
    void deleteById(Long id);
}
