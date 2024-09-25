
package com.vargas.gestioninventario.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CruceInventarioDTO {
    private Long idCruce;
    private String fecha;  // Fecha como String en formato "YYYY-MM-DD"
    private String estadoCruce;
    private Long usuarioId;  // ID del usuario que realizó el cruce
    private List<DetalleCruceInventarioDTO> detalleCruces;
    private String resultado;
}
