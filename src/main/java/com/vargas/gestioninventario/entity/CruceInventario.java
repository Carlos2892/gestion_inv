package com.vargas.gestioninventario.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cruces_inventario")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CruceInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;  // Relación con Usuario (quien hizo el cruce)

    private LocalDate fechaCruce;  // Fecha en que se hizo el cruce

    private String estadoCruce;  // CONFORME o DISCREPANCIA

    @OneToMany(mappedBy = "cruceInventario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistoricoCambios> historicoCambios = new ArrayList<>();  // Relación con el historial de cambios
    
    @OneToMany(mappedBy = "cruceInventario")
    private List<DetalleCruceInventario> detallesCruce;
}
