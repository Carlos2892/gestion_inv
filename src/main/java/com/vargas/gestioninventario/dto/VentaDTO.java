
package com.vargas.gestioninventario.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentaDTO {
    private Long id;
    private String correlativo;
    private LocalDate fecha;
    private double subtotal;
    private double descuento;
    private double importeTotal;
    private String estado;
    
    private Long clienteId;
    private String clienteNombre;
    private String clienteApellido;
    private String clienteNumeroDocumento;

    private Long formaPagoId;
    private String formaPagoDescripcion;

    private List<DetalleVentaDTO> detalleVentas;
}
