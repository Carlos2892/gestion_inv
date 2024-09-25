
package com.vargas.gestioninventario.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoInventarioDTO {
    private Long idInventario;
    private Long idProductoTalla;
    private String descripcionProducto;
    private String nombreMarca;
    private String nombreCategoria; 
    private String nombreTalla;
    private double precioVenta;
    private double precioCompra;
    private int stockActual;
    private int stockFisico;
    private int stockMinimo;
    private LocalDateTime fechaActualizacion;
    private String estadoProductoTalla;
    private Long idMarca;
    private Long idCategoria;
    private Long idTalla;
    private String descripcionCompletaproducto;
}
