package com.vargas.gestioninventario.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentaSaveDTO {
    private Long id;
    private Long ClienteId;
    private Long formaPagoId;
    private String correlativo;
    private LocalDate fecha;
    private double subtotal;
    private double descuento;
    private double importeTotal;
    private List<DetalleVentaDTO> detalleVentas;
}
