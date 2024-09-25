
package com.vargas.gestioninventario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoricoCambiosDTO {
    private Long id;
    private String fechaCambio;  // En formato String "YYYY-MM-DD"
    private String usuarioNombre;
    private String estado;
    private String detalleResultado;
}
