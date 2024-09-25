package com.vargas.gestioninventario.service.impl;

import com.vargas.gestioninventario.entity.Marca;
import com.vargas.gestioninventario.repository.MarcaRepository;
import com.vargas.gestioninventario.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MarcaServiceImpl implements MarcaService {
    @Autowired
    private MarcaRepository marcaRepository;

    @Override
    public List<Marca> findAll() {
        return marcaRepository.findAll();
    }

    @Override
    public Marca findById(Long id) {
        return marcaRepository.findById(id).orElse(null);
    }

    @Override
    public Marca save(Marca marca) {
        return marcaRepository.save(marca);
    }

    @Override
    public void deleteById(Long id) {
        marcaRepository.deleteById(id);
    }
}
