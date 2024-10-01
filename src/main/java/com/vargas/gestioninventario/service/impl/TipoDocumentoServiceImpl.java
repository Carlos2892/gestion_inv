package com.vargas.gestioninventario.service.impl;

import com.vargas.gestioninventario.entity.TipoDocumento;
import com.vargas.gestioninventario.repository.TipoDocumentoRepository;
import com.vargas.gestioninventario.service.TipoDocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TipoDocumentoServiceImpl implements TipoDocumentoService {
    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;

    @Override
    public List<TipoDocumento> findAll() {
        return tipoDocumentoRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TipoDocumento> listarTodosExceptoIdCero() {
        // Llamar al repositorio para obtener los tipos de documentos cuyo ID no es igual a 0
        return tipoDocumentoRepository.findByIdNot(0L);
    }

    @Override
    public TipoDocumento findById(Long id) {
        return tipoDocumentoRepository.findById(id).orElse(null);
    }

    @Override
    public TipoDocumento save(TipoDocumento tipoDocumento) {
        return tipoDocumentoRepository.save(tipoDocumento);
    }

    @Override
    public void deleteById(Long id) {
        tipoDocumentoRepository.deleteById(id);
    }
}
