package com.vargas.gestioninventario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleVentaDTO {
    private Long id;
    private Long productoTallaId;
    private int cantidad;
    private double precioUnitario;
    private double precioCompra;
    private double subtotal;
    private double descuento;
    private double importeTotal;
    private String descripcionProducto;
}