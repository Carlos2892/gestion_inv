package com.vargas.gestioninventario.service.impl;

import com.vargas.gestioninventario.entity.DetalleCompra;
import com.vargas.gestioninventario.repository.DetalleCompraRepository;
import com.vargas.gestioninventario.service.DetalleCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DetalleCompraServiceImpl implements DetalleCompraService {
    @Autowired
    private DetalleCompraRepository detalleCompraRepository;

    @Override
    public List<DetalleCompra> findAll() {
        return detalleCompraRepository.findAll();
    }

    @Override
    public DetalleCompra findById(Long id) {
        return detalleCompraRepository.findById(id).orElse(null);
    }

    @Override
    public DetalleCompra save(DetalleCompra detalleCompra) {
        return detalleCompraRepository.save(detalleCompra);
    }

    @Override
    public void deleteById(Long id) {
        detalleCompraRepository.deleteById(id);
    }
}
