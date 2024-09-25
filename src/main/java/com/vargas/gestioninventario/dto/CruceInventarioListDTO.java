
package com.vargas.gestioninventario.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CruceInventarioListDTO {
    private Long idCruce;
    private LocalDate fecha;  // Fecha como String en formato "YYYY-MM-DD"
    private String estadoCruce;
    private Long usuarioId;  // ID del usuario que realizó el cruce
    private String usuarioNombre;
    private List<DetalleCruceInventarioDTO> detalleCruces;
}
