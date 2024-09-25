package com.vargas.gestioninventario.service.impl;

import com.vargas.gestioninventario.dto.ClienteDTO;
import com.vargas.gestioninventario.entity.Cliente;
import com.vargas.gestioninventario.entity.TipoDocumento;
import com.vargas.gestioninventario.repository.ClienteRepository;
import com.vargas.gestioninventario.repository.TipoDocumentoRepository;
import com.vargas.gestioninventario.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;
    

    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente findById(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        clienteRepository.deleteById(id);
    }
    
    @Override
    public Optional<Cliente> buscarPorTipoYNumeroDocumento(Long tipoDocumentoId, String numeroDocumento) {
        return clienteRepository.findByTipoDocumento_IdAndNumeroDocumento(tipoDocumentoId, numeroDocumento);
    }
    
    @Override
    public Cliente save(ClienteDTO clienteDTO) {
        Cliente cliente = convertirADominio(clienteDTO);
        return clienteRepository.save(cliente);
    }

    private Cliente convertirADominio(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        
        // Buscar el objeto TipoDocumento usando el ID
        TipoDocumento tipoDocumento = tipoDocumentoRepository.findById(clienteDTO.getTipoDocumentoId())
                .orElseThrow(() -> new RuntimeException("Tipo de documento no encontrado"));

        // Asignar los valores del DTO al objeto Cliente
        cliente.setTipoDocumento(tipoDocumento);
        cliente.setNumeroDocumento(clienteDTO.getNumeroDocumento());
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setApellido(clienteDTO.getApellido());

        return cliente;
    }
}
