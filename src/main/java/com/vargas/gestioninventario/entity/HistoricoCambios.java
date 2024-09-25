
package com.vargas.gestioninventario.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "historico_cambios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoricoCambios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cruce_id", nullable = false)
    private CruceInventario cruceInventario;  // Relación con el cruce de inventario

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;  // Usuario que hizo el cambio

    private LocalDate fechaCambio;  // Fecha del cambio
    
    @Column(name = "detalle_resultado", columnDefinition = "TEXT")
    private String detalleResultado;  // Descripción del cambio realizado
    
    private String  estado;
}