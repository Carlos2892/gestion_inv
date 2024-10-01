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
        // Verificar si ya existe un cliente con el mismo tipoDocumento y numeroDocumento
        Optional<Cliente> clienteExistente = clienteRepository.findByTipoDocumento_IdAndNumeroDocumento(
                clienteDTO.getTipoDocumentoId(), clienteDTO.getNumeroDocumento()
        );

        if (clienteExistente.isPresent()) {
            // Lanzar excepción si el cliente ya existe
            throw new RuntimeException("Ya existe un cliente registrado con este tipo de documento y número de documento.");
        }

        // Si no existe, crear el cliente
        Cliente cliente = convertirADominio(new Cliente(), clienteDTO);
        return clienteRepository.save(cliente);
    }

    @Override
    public void editarCliente(Long clienteId, ClienteDTO clienteDTO) {
        // Buscar el cliente existente
        Cliente clienteExistente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        // Reutilizar el método convertirADominio para actualizar los campos necesarios
        Cliente clienteActualizado = convertirADominio(clienteExistente, clienteDTO);

        // Guardar los cambios
        clienteRepository.save(clienteActualizado);
    }

    /**
     * Método para convertir un ClienteDTO a un Cliente.
     * Si el cliente ya existe (edición), se actualizarán los campos necesarios.
     */
    private Cliente convertirADominio(Cliente cliente, ClienteDTO clienteDTO) {
        // Buscar el TipoDocumento usando el ID proporcionado en el DTO
        TipoDocumento tipoDocumento = tipoDocumentoRepository.findById(clienteDTO.getTipoDocumentoId())
                .orElseThrow(() -> new RuntimeException("Tipo de documento no encontrado"));

        // Asignar valores del DTO al cliente
        cliente.setTipoDocumento(tipoDocumento);
        cliente.setNumeroDocumento(clienteDTO.getNumeroDocumento());
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setApellido(clienteDTO.getApellido());

        return cliente;
    }
}
