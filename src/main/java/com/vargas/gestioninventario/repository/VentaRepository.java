package com.vargas.gestioninventario.repository;

import com.vargas.gestioninventario.entity.Venta;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    @Query("SELECT COALESCE(MAX(CAST(v.correlativo AS int)), 0) FROM Venta v")
    Integer obtenerUltimoCorrelativo();
    
    List<Venta> findAll();
    
    // Método para contar las ventas del día actual
    @Query("SELECT COUNT(v) FROM Venta v WHERE v.fecha = :fechaActual")
    long contarVentasDelDia(LocalDate fechaActual);
    
    // Método personalizado para obtener la suma de todos los importes totales de las ventas
    @Query("SELECT SUM(v.importeTotal) FROM Venta v WHERE MONTH(v.fecha) = MONTH(:fechaActual) AND YEAR(v.fecha) = YEAR(:fechaActual)")
    Double obtenerTotalVentasDelMes(LocalDate fechaActual);
    
    // Suma de importes totales agrupados por mes en el año actual
    @Query("SELECT MONTH(v.fecha), SUM(v.importeTotal) FROM Venta v WHERE YEAR(v.fecha) = YEAR(:fechaActual) GROUP BY MONTH(v.fecha) ORDER BY MONTH(v.fecha)")
    List<Object[]> obtenerVentasMensualesDelAnio(LocalDate fechaActual);
    
    @Query("SELECT p.categoria.nombre, SUM(dv.cantidad) as totalVentas "
         + "FROM DetalleVenta dv "
         + "JOIN dv.productoTalla pt "
         + "JOIN pt.producto p "
         + "JOIN dv.venta v "
         + "WHERE MONTH(v.fecha) = MONTH(CURRENT_DATE) "
         + "AND YEAR(v.fecha) = YEAR(CURRENT_DATE) "
         + "GROUP BY p.categoria.nombre "
         + "ORDER BY totalVentas DESC")
    List<Object[]> obtenerVentasPorCategoriaDelMes();
}
