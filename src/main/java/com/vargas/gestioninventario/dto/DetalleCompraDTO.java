package com.vargas.gestioninventario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleCompraDTO {
    private Long id;
    private Long productoTallaId;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;
    private double impuesto;
    private double importeTotal;
    private String descripcionProducto;
}
