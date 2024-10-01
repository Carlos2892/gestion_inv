package com.vargas.gestioninventario.repository;

import com.vargas.gestioninventario.entity.ProductoTalla;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoTallaRepository extends JpaRepository<ProductoTalla, Long> {
    @Query("SELECT pt FROM ProductoTalla pt " +
       "JOIN pt.inventarios inv " +
       "JOIN pt.producto p " +
       "JOIN pt.talla t " +
       "JOIN p.marca m " +
       "WHERE pt.estado = 'A' AND inv.stockActual > 0 " +
       "AND (p.descripcion LIKE %:keyword% OR t.nombre LIKE %:keyword% OR m.nombre LIKE %:keyword%)")
    List<ProductoTalla> findAvailableProductsByKeyword(@Param("keyword") String keyword);
    
    // Método para contar los productos cuyo precioCompra es mayor a 0
    @Query("SELECT COUNT(pt) FROM ProductoTalla pt WHERE pt.precioCompra > 0")
    long contarTotalProductos();
}
