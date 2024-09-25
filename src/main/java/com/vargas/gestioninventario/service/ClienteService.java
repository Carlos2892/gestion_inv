package com.vargas.gestioninventario.service;

import com.vargas.gestioninventario.dto.ClienteDTO;
import com.vargas.gestioninventario.entity.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteService {
    List<Cliente> findAll();
    Cliente findById(Long id);
    Cliente save(ClienteDTO clienteDTO);
    void deleteById(Long id);
    Optional<Cliente> buscarPorTipoYNumeroDocumento(Long tipoDocumentoId, String nroDocumento);
}
