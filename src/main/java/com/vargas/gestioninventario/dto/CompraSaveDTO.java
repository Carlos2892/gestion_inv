package com.vargas.gestioninventario.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompraSaveDTO {
    private Long id;
    private Long proveedorId;
    private Long tipoComprobanteId;
    private String serie;
    private String correlativo;
    private LocalDate fecha;
    private double subtotal;
    private double impuesto;
    private double importeTotal;
    private List<DetalleCompraDTO> detalleCompras;
}
