
package com.vargas.gestioninventario.service;

import com.vargas.gestioninventario.dto.CruceInventarioDTO;
import com.vargas.gestioninventario.dto.CruceInventarioListDTO;
import com.vargas.gestioninventario.entity.CruceInventario;
import com.vargas.gestioninventario.entity.Usuario;
import java.util.List;

public interface CruceInventarioService {

    CruceInventario crearCruce(CruceInventario cruceInventario);
    CruceInventario editarCruce(Long idCruce, String nuevoEstado, Usuario usuario);
    List<CruceInventario> obtenerCrucesPorUsuario(Usuario usuario);
    CruceInventario obtenerCrucePorId(Long idCruce, Usuario usuario);
    void guardarCruce(CruceInventarioDTO cruceInventarioDTO);
    void editarCruce(CruceInventarioDTO cruceInventarioDTO);
    List<CruceInventarioListDTO> listarCruces();
    CruceInventarioListDTO obtenerCrucePorId(Long cruceId);
}
