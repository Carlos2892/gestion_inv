
package com.vargas.gestioninventario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {
    private Long tipoDocumentoId;
    private String numeroDocumento;
    private String nombre;
    private String apellido;
}
