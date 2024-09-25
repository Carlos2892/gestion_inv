package com.vargas.gestioninventario.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name="kardex")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Kardex {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "producto_talla_id")
    private ProductoTalla productoTalla;

    @Column(name = "fecha_movimiento")
    private LocalDate  fechaMovimiento;
    
    private String concepto;
    private int cantidad;
    
    @Column(name = "precio_unitario")
    private double precioUnitario;
    
    @Column(name = "importe_total")
    private double importeTotal;
    
    
    @Column(name = "stock_anterior")
    private int stockAnterior;
    
    @Column(name = "stock_actual")
    private int stockActual;
    
    private String referencia;

}
