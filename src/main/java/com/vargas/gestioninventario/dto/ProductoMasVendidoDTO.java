
package com.vargas.gestioninventario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoMasVendidoDTO {
    private Long idProductoTalla;
    private String producto;
    private Long totalCantidadVendida;
    private Long totalVentas;
    private Double totalImporteVentas;
    private int stockActual;
}
