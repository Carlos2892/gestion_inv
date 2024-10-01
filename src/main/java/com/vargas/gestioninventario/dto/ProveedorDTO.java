
package com.vargas.gestioninventario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProveedorDTO {
    private Long tipoDocumentoId;
    private String numeroDocumento;
    private String razonSocial;
    private String direccion;
    private String telefono;
    private String estado; // Esto es opcional, por defecto será 'A'
}
