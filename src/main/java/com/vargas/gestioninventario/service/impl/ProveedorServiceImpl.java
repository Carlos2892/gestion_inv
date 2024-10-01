package com.vargas.gestioninventario.service.impl;

import com.vargas.gestioninventario.dto.ProveedorDTO;
import com.vargas.gestioninventario.entity.Proveedor;
import com.vargas.gestioninventario.entity.TipoDocumento;
import com.vargas.gestioninventario.repository.ProveedorRepository;
import com.vargas.gestioninventario.repository.TipoDocumentoRepository;
import com.vargas.gestioninventario.service.ProveedorService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProveedorServiceImpl implements ProveedorService {
    @Autowired
    private ProveedorRepository proveedorRepository;
    
    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;

    @Override
    public List<Proveedor> findAll() {
        return proveedorRepository.findAll();
    }

    @Override
    public Proveedor findById(Long id) {
        return proveedorRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Proveedor save(ProveedorDTO proveedorDTO) {
        // Validación para asegurarse de que no haya un proveedor con el mismo tipoDocumento y numeroDocumento
        Optional<Proveedor> proveedorExistente = proveedorRepository.findByTipoDocumento_IdAndNumeroDocumento(
                proveedorDTO.getTipoDocumentoId(), proveedorDTO.getNumeroDocumento());

        if (proveedorExistente.isPresent()) {
            throw new RuntimeException("Ya existe un proveedor registrado con este tipo de documento y número de documento.");
        }

        // Convertir DTO a entidad Proveedor
        Proveedor proveedor = convertirADominio(new Proveedor(), proveedorDTO);
        return proveedorRepository.save(proveedor);
    }
    
    @Override
    @Transactional
    public Proveedor update(Long id, ProveedorDTO proveedorDTO) {
        // Buscar el proveedor existente
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        // Actualizar los campos del proveedor
        Proveedor proveedorActualizado = convertirADominio(proveedor, proveedorDTO);
        return proveedorRepository.save(proveedorActualizado);
    }
    
    private Proveedor convertirADominio(Proveedor proveedor, ProveedorDTO proveedorDTO) {
        // Buscar el TipoDocumento usando el ID proporcionado en el DTO
        TipoDocumento tipoDocumento = tipoDocumentoRepository.findById(proveedorDTO.getTipoDocumentoId())
                .orElseThrow(() -> new RuntimeException("Tipo de documento no encontrado"));

        proveedor.setTipoDocumento(tipoDocumento);
        proveedor.setNumeroDocumento(proveedorDTO.getNumeroDocumento());
        proveedor.setRazonSocial(proveedorDTO.getRazonSocial());
        proveedor.setDireccion(proveedorDTO.getDireccion());
        proveedor.setTelefono(proveedorDTO.getTelefono());
        proveedor.setEstado(proveedorDTO.getEstado() != null ? proveedorDTO.getEstado() : "A"); // Estado por defecto

        return proveedor;
    }

    @Override
    public void deleteById(Long id) {
        proveedorRepository.deleteById(id);
    }
}
