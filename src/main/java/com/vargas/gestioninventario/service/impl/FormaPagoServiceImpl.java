package com.vargas.gestioninventario.service.impl;

import com.vargas.gestioninventario.entity.FormaPago;
import com.vargas.gestioninventario.repository.FormaPagoRepository;
import com.vargas.gestioninventario.service.FormaPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FormaPagoServiceImpl implements FormaPagoService {
    @Autowired
    private FormaPagoRepository formaPagoRepository;

    @Override
    public List<FormaPago> findAll() {
        return formaPagoRepository.findAll();
    }

    @Override
    public FormaPago findById(Long id) {
        return formaPagoRepository.findById(id).orElse(null);
    }

    @Override
    public FormaPago save(FormaPago formaPago) {
        return formaPagoRepository.save(formaPago);
    }

    @Override
    public void deleteById(Long id) {
        formaPagoRepository.deleteById(id);
    }
}
