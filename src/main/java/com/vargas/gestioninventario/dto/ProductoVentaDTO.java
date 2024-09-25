
package com.vargas.gestioninventario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoVentaDTO {
    private Long id;
    private String descripcionProducto;
    private double precioCompra;
    private double precioVenta;
    private int stockActual;
}

