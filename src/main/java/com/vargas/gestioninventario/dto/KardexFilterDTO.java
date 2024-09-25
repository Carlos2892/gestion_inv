
package com.vargas.gestioninventario.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KardexFilterDTO {
    private Long productoTallaId; // ID del producto y talla
    private LocalDate fechaInicio; // Fecha de inicio del filtro
    private LocalDate fechaFin; // Fecha de fin del filtro
    private String concepto; // Concepto (COMPRA, VENTA, etc.)
}
