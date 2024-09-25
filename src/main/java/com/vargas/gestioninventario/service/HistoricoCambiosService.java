
package com.vargas.gestioninventario.service;

import com.vargas.gestioninventario.dto.HistoricoCambiosDTO;
import java.util.List;


public interface HistoricoCambiosService {
    List<HistoricoCambiosDTO> obtenerHistoricoPorCruce(Long cruceId);
}
