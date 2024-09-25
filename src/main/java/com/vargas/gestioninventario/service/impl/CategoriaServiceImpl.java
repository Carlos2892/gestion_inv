package com.vargas.gestioninventario.service.impl;

import com.vargas.gestioninventario.entity.Categoria;
import com.vargas.gestioninventario.repository.CategoriaRepository;
import com.vargas.gestioninventario.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    @Override
    public Categoria findById(Long id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    @Override
    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public void deleteById(Long id) {
        categoriaRepository.deleteById(id);
    }
    
    @Override
    public List<Categoria> findByEstadoActivo() {
        return categoriaRepository.findByEstado("A"); // Llama al repositorio y filtra por estado "A"
    }
}
