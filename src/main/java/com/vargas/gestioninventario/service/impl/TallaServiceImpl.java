package com.vargas.gestioninventario.service.impl;

import com.vargas.gestioninventario.entity.Talla;
import com.vargas.gestioninventario.repository.TallaRepository;
import com.vargas.gestioninventario.service.TallaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TallaServiceImpl implements TallaService {
    @Autowired
    private TallaRepository tallaRepository;

    @Override
    public List<Talla> findAll() {
        return tallaRepository.findAll();
    }

    @Override
    public Talla findById(Long id) {
        return tallaRepository.findById(id).orElse(null);
    }

    @Override
    public Talla save(Talla talla) {
        return tallaRepository.save(talla);
    }

    @Override
    public void deleteById(Long id) {
        tallaRepository.deleteById(id);
    }
    
    @Override
    public List<Talla> findByEstadoActivo() {
        return tallaRepository.findByEstado("A"); // Llama al repositorio y filtra por estado "A"
    }
}
