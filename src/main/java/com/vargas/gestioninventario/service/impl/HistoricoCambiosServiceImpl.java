
package com.vargas.gestioninventario.service.impl;

import com.vargas.gestioninventario.dto.HistoricoCambiosDTO;
import com.vargas.gestioninventario.entity.HistoricoCambios;
import com.vargas.gestioninventario.repository.HistoricoCambiosRepository;
import com.vargas.gestioninventario.service.HistoricoCambiosService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoricoCambiosServiceImpl implements HistoricoCambiosService{
    
    @Autowired
    private HistoricoCambiosRepository historicoCambiosRepository;
    
    @Override
    public List<HistoricoCambiosDTO> obtenerHistoricoPorCruce(Long cruceId) {
        List<HistoricoCambios> historicoCambios = historicoCambiosRepository.findByCruceInventarioId(cruceId);

        return historicoCambios.stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    private HistoricoCambiosDTO convertirADTO(HistoricoCambios historicoCambios) {
        HistoricoCambiosDTO dto = new HistoricoCambiosDTO();
        dto.setId(historicoCambios.getId());
        dto.setFechaCambio(historicoCambios.getFechaCambio().toString());
        dto.setUsuarioNombre(historicoCambios.getUsuario().getNombre());
        dto.setEstado(historicoCambios.getEstado());
        dto.setDetalleResultado(historicoCambios.getDetalleResultado());

        return dto;
    }
}
