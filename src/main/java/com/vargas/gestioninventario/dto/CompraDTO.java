
package com.vargas.gestioninventario.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompraDTO {
    private Long id;
    private Long proveedorId;
    private String proveedor;
    private String proveedorRuc;
    private LocalDate fechaCompra;
    private Long tipoComprobanteId;
    private String comprobante;
    private String serie;
    private String correlativo;
    private double totalCompra;
    private String estado;
    private List<DetalleCompraDTO> detalleCompras;
}
