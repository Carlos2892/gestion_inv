
package com.vargas.gestioninventario.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KardexDTO {
    private LocalDate fechaMovimiento;
    private String concepto;
    private String referencia;
    private int cantidad;
    private double precioUnitario;
    private double importeTotal;
    private int stockAnterior;
    private int stockActual;
}
