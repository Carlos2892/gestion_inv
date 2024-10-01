package com.vargas.gestioninventario.repository;

import com.vargas.gestioninventario.entity.Inventario;
import com.vargas.gestioninventario.entity.ProductoTalla;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    
    @Query("SELECT i FROM Inventario i " +
           "JOIN FETCH i.productoTalla pt " +
           "JOIN FETCH pt.producto p " +
           "JOIN FETCH pt.talla t " +
           "JOIN FETCH p.marca m " +
           "JOIN FETCH p.categoria c")
    List<Inventario> findAllWithProductoAndTalla();
    
    @Query("SELECT i FROM Inventario i " +
            "JOIN FETCH i.productoTalla pt " +
            "JOIN FETCH pt.producto p " +
            "JOIN FETCH pt.talla t " +
            "JOIN FETCH p.marca m " +
            "JOIN FETCH p.categoria c " +
            "WHERE i.stockActual > 0")
    List<Inventario> findAllWithProductoAndTallaWhereStockActual();
    
    // Método para encontrar Inventario por ProductoTalla
    @Query("SELECT i FROM Inventario i WHERE i.productoTalla = :productoTalla")
    Inventario findByProductoTalla(@Param("productoTalla") ProductoTalla productoTalla);
    
    // Método para contar productos con precioCompra > 0 y stockActual < stockMinimo
    @Query("SELECT COUNT(i) FROM Inventario i WHERE i.productoTalla.precioCompra > 0 AND i.stockActual <= i.stockMinimo")
    long contarProductosConPocoStock();
    
    @Query("SELECT i FROM Inventario i WHERE i.stockActual <= i.stockMinimo AND i.productoTalla.precioCompra > 0")
    List<Inventario> obtenerProductosPocoStock();
    
    // Consulta para obtener el valor total del inventario (solo productos con precio de compra mayor a 0)
    @Query("SELECT SUM(pt.precioCompra * i.stockActual) " +
           "FROM Inventario i " +
           "JOIN i.productoTalla pt " +
           "WHERE pt.precioCompra > 0")
    Double obtenerValorTotalInventario();
    
}
