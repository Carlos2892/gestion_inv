package com.vargas.gestioninventario.service.impl;

import com.vargas.gestioninventario.entity.TipoComprobante;
import com.vargas.gestioninventario.repository.TipoComprobanteRepository;
import com.vargas.gestioninventario.service.TipoComprobanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TipoComprobanteServiceImpl implements TipoComprobanteService {
    @Autowired
    private TipoComprobanteRepository tipoComprobanteRepository;

    @Override
    public List<TipoComprobante> findAll() {
        return tipoComprobanteRepository.findAll();
    }

    @Override
    public TipoComprobante findById(Long id) {
        return tipoComprobanteRepository.findById(id).orElse(null);
    }

    @Override
    public TipoComprobante save(TipoComprobante tipoComprobante) {
        return tipoComprobanteRepository.save(tipoComprobante);
    }

    @Override
    public void deleteById(Long id) {
        tipoComprobanteRepository.deleteById(id);
    }
}
