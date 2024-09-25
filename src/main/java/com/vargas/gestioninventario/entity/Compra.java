
package com.vargas.gestioninventario.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="compra")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    @ManyToOne
    @JoinColumn(name = "tipoComprobante_id")
    private TipoComprobante tipoComprobante;
    
    private LocalDate fecha;
    private String correlativo;
    private String estado;
    private String serie;
    private double subtotal;
    private double impuesto;
    
    @Column(name = "importe_total_compra")
    private double importeTotal;

    @OneToMany(mappedBy = "compra")
    private List<DetalleCompra> detalleCompras;
    
}
