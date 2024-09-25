
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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "detalle_cruce_inventario")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleCruceInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cruce_id", nullable = false)
    private CruceInventario cruceInventario;  // Relación con la entidad principal de CruceInventario

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventario_id", nullable = false)
    private Inventario inventario;  // Relación con Inventario, para obtener producto y stock actual
    
    @Column(name = "stock_sistema")
    private int stockSistema;

    @Column(name = "stock_fisico")
    private int stockFisico;  // Stock real en almacén (ingresado manualmente)

    @Column(name = "diferencia")
    private int diferencia;  // Diferencia entre stock en sistema (en Inventario) y stock físico
}
