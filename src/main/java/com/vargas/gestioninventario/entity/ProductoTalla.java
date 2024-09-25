
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
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "producto_talla")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoTalla {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "talla_id")
    private Talla talla;
    
    private String estado;
    
    @Column(name = "precio_compra")
    private double precioCompra;
    
    @Column(name = "precio_venta")
    private double precioVenta;

    @OneToMany(mappedBy = "productoTalla")
    private List<DetalleCompra> detalleCompras;

    @OneToMany(mappedBy = "productoTalla")
    private List<DetalleVenta> detalleVentas;

    @OneToMany(mappedBy = "productoTalla")
    private Set<Inventario> inventarios;

    @OneToMany(mappedBy = "productoTalla")
    private Set<Kardex> kardex;

}
