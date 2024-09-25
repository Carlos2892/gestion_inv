
package com.vargas.gestioninventario.service.impl;

import com.vargas.gestioninventario.dto.CruceInventarioDTO;
import com.vargas.gestioninventario.dto.CruceInventarioListDTO;
import com.vargas.gestioninventario.dto.DetalleCruceInventarioDTO;
import com.vargas.gestioninventario.entity.CruceInventario;
import com.vargas.gestioninventario.entity.DetalleCruceInventario;
import com.vargas.gestioninventario.entity.HistoricoCambios;
import com.vargas.gestioninventario.entity.Inventario;
import com.vargas.gestioninventario.entity.Usuario;
import com.vargas.gestioninventario.repository.CruceInventarioRepository;
import com.vargas.gestioninventario.repository.DetalleCruceInventarioRepository;
import com.vargas.gestioninventario.repository.HistoricoCambiosRepository;
import com.vargas.gestioninventario.repository.InventarioRepository;
import com.vargas.gestioninventario.service.CruceInventarioService;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CruceInventarioServiceImpl implements CruceInventarioService {

    @Autowired
    private CruceInventarioRepository cruceInventarioRepository;
    
    @Autowired
    private HistoricoCambiosRepository historicoCambiosRepository;
    
    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private DetalleCruceInventarioRepository detalleCruceInventarioRepository;
    
    @Override
    public List<CruceInventarioListDTO> listarCruces() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) authentication.getPrincipal();
        return cruceInventarioRepository.findByUsuario(usuario).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public CruceInventarioListDTO obtenerCrucePorId(Long cruceId) {
        // Buscar la compra en la base de datos
        CruceInventario cruce = cruceInventarioRepository.findById(cruceId)
                .orElseThrow(() -> new RuntimeException("Cruce no encontrada con el ID: " + cruceId));
        // Convertir la entidad Compra a DTO
        CruceInventarioListDTO cruceInventarioDTO = convertToDTO(cruce);
        return cruceInventarioDTO;
    }
    
    private CruceInventarioListDTO convertToDTO(CruceInventario cruceInventario) {
        CruceInventarioListDTO dto = new CruceInventarioListDTO();
        dto.setIdCruce(cruceInventario.getId());
        dto.setUsuarioId(cruceInventario.getUsuario().getId());
        dto.setUsuarioNombre("Usuario: "+cruceInventario.getUsuario().getUsername()+" - Nombre: "+cruceInventario.getUsuario().getNombre());
        dto.setFecha(cruceInventario.getFechaCruce());
        dto.setEstadoCruce(cruceInventario.getEstadoCruce());

        List<DetalleCruceInventarioDTO> detalleCruceDTOs = cruceInventario.getDetallesCruce().stream()
            .map(detalle -> {
                DetalleCruceInventarioDTO detalleDto = new DetalleCruceInventarioDTO();
                detalleDto.setId(detalle.getId());
                detalleDto.setIdCruce(detalle.getCruceInventario().getId());
                detalleDto.setIdInventario(detalle.getInventario().getId());
                detalleDto.setStockSistema(detalle.getStockSistema());
                detalleDto.setStockFisico(detalle.getStockFisico());
                detalleDto.setDiferencia(detalle.getDiferencia());
                detalleDto.setDescripcionProducto(detalle.getInventario().getProductoTalla().getProducto().getDescripcion());
                detalleDto.setIdProductoTalla(detalle.getInventario().getProductoTalla().getId());
                detalleDto.setIdCategoria(detalle.getInventario().getProductoTalla().getProducto().getCategoria().getId());
                detalleDto.setIdMarca(detalle.getInventario().getProductoTalla().getProducto().getMarca().getId());
                detalleDto.setIdTalla(detalle.getInventario().getProductoTalla().getTalla().getId());
                detalleDto.setNombreMarca(detalle.getInventario().getProductoTalla().getProducto().getMarca().getNombre());
                detalleDto.setNombreTalla(detalle.getInventario().getProductoTalla().getTalla().getNombre());
                return detalleDto;
            })
            .collect(Collectors.toList());
        dto.setDetalleCruces(detalleCruceDTOs);
        return dto;
    }

    @Override
    public CruceInventario crearCruce(CruceInventario cruceInventario) {
        cruceInventario.setFechaCruce(LocalDate.now());
        return cruceInventarioRepository.save(cruceInventario);
    }

    @Override
    public CruceInventario editarCruce(Long idCruce, String nuevoEstado, Usuario usuario) {
        CruceInventario cruce = cruceInventarioRepository.findByIdAndUsuario(idCruce, usuario);
        if (cruce == null) {
            throw new RuntimeException("No tienes permisos para editar este cruce o el cruce no existe.");
        }
        cruce.setEstadoCruce(nuevoEstado);
        return cruceInventarioRepository.save(cruce);
    }

    @Override
    public List<CruceInventario> obtenerCrucesPorUsuario(Usuario usuario) {
        return cruceInventarioRepository.findByUsuario(usuario);
    }

    @Override
    public CruceInventario obtenerCrucePorId(Long idCruce, Usuario usuario) {
        return cruceInventarioRepository.findByIdAndUsuario(idCruce, usuario);
    }
    
    @Override
    @Transactional
    public void guardarCruce(CruceInventarioDTO cruceInventarioDTO) {
        // Lógica para crear un nuevo cruce
        procesarCruce(cruceInventarioDTO, new CruceInventario(), true);
    }

    @Override
    @Transactional
    public void editarCruce(CruceInventarioDTO cruceInventarioDTO) {
        // Lógica para editar un cruce existente
        CruceInventario cruceExistente = cruceInventarioRepository.findById(cruceInventarioDTO.getIdCruce())
            .orElseThrow(() -> new RuntimeException("Cruce no encontrado"));

        procesarCruce(cruceInventarioDTO, cruceExistente, false);
    }

    private void procesarCruce(CruceInventarioDTO cruceInventarioDTO, CruceInventario cruce, boolean esNuevo) {
        // Obtener el usuario que está realizando el cruce
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) authentication.getPrincipal();

        // Asignar usuario, fecha y estado
        cruce.setUsuario(usuario);
        cruce.setFechaCruce(LocalDate.now());
        cruce.setEstadoCruce(cruceInventarioDTO.getEstadoCruce());  // Puede ser CONFORME o DISCREPANCIA

        // Guardar o actualizar el cruce en la base de datos
        cruceInventarioRepository.save(cruce);

        // Si no es un nuevo cruce (es una edición), eliminar los detalles anteriores
        if (!esNuevo) {
            detalleCruceInventarioRepository.deleteByCruceInventarioId(cruce.getId());
        }

        // Inicializar StringBuilder para el historial
        StringBuilder resultado = new StringBuilder();

        // Procesar los detalles del cruce
        for (DetalleCruceInventarioDTO detalleDTO : cruceInventarioDTO.getDetalleCruces()) {
            // Buscar el inventario correspondiente por ID
            Inventario inventario = inventarioRepository.findById(detalleDTO.getIdInventario())
                    .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));

            // Crear y asociar el detalle del cruce
            DetalleCruceInventario detalle = new DetalleCruceInventario();
            detalle.setCruceInventario(cruce);
            detalle.setInventario(inventario);
            detalle.setStockSistema(detalleDTO.getStockSistema());
            detalle.setStockFisico(detalleDTO.getStockFisico());
            detalle.setDiferencia(detalleDTO.getDiferencia());

            // Guardar el detalle en la base de datos
            detalleCruceInventarioRepository.save(detalle);

            // Construir el historial solo con faltantes y sobrantes
            if ("DISCREPANCIA".equals(cruce.getEstadoCruce())) {
                if (detalle.getDiferencia() > 0) {
                    resultado.append("Sobrante: ").append(detalleDTO.getDescripcionProducto())
                        .append(" - Cantidad: ").append(detalle.getDiferencia()).append("\n");
                } else if (detalle.getDiferencia() < 0) {
                    resultado.append("Faltante: ").append(detalleDTO.getDescripcionProducto())
                        .append(" - Cantidad: ").append(Math.abs(detalle.getDiferencia())).append("\n");
                }
            }
        }

        // Si el estado es CONFORME, agregar un mensaje de conformidad
        if ("CONFORME".equals(cruce.getEstadoCruce())) {
            resultado.append("Cruce conforme: Todos los productos están correctos.\n");
        }

        // Guardar el historial de cambios
        HistoricoCambios historico = new HistoricoCambios();
        historico.setCruceInventario(cruce);
        historico.setFechaCambio(LocalDate.now());
        historico.setDetalleResultado(resultado.toString());
        historico.setEstado(cruceInventarioDTO.getEstadoCruce());
        historico.setUsuario(usuario);

        // Guardar el registro del historial en la base de datos
        historicoCambiosRepository.save(historico);

        // Retornar el resultado del cruce para el controlador
        cruceInventarioDTO.setResultado(resultado.toString()); // Devolver el detalle del cruce con discrepancias
    }
}