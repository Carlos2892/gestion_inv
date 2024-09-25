package com.vargas.gestioninventario.service;

import com.vargas.gestioninventario.entity.FormaPago;
import java.util.List;

public interface FormaPagoService {
    List<FormaPago> findAll();
    FormaPago findById(Long id);
    FormaPago save(FormaPago formaPago);
    void deleteById(Long id);
}
