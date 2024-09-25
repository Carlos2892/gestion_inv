
package com.vargas.gestioninventario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleCruceInventarioDTO {
    private Long id;  
    private Long idCruce;  
    private Long idInventario;  
    private int stockSistema;  
    private int stockFisico;  
    private int diferencia; 
    private String descripcionProducto;
    private Long idProductoTalla;
    private Long idCategoria;
    private Long idMarca;
    private Long idTalla;
    private String descripcionCompletaproducto;
    private String nombreMarca;
    private String nombreTalla;
    
}
