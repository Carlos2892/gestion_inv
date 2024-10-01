package com.vargas.gestioninventario.repository;

import com.vargas.gestioninventario.entity.DetalleVenta;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
    // Consulta para obtener las ganancias del mes actual
    @Query("SELECT SUM(dv.importeTotal - (dv.precioCompra * dv.cantidad)) " +
           "FROM DetalleVenta dv " +
           "JOIN dv.venta v " +
           "WHERE MONTH(v.fecha) = MONTH(CURRENT_DATE) " +
           "AND YEAR(v.fecha) = YEAR(CURRENT_DATE)")
    Double obtenerGananciasDelMes();
    
    // Consulta para obtener los productos más vendidos del mes
    @Query("SELECT dv.productoTalla, SUM(dv.cantidad) as totalCantidadVendida, COUNT(dv) as totalVentas " +
           "FROM DetalleVenta dv " +
           "JOIN dv.venta v " +
           "WHERE MONTH(v.fecha) = MONTH(CURRENT_DATE) " +
           "AND YEAR(v.fecha) = YEAR(CURRENT_DATE) " +
           "GROUP BY dv.productoTalla " +
           "ORDER BY totalCantidadVendida DESC")
    Page<Object[]> obtenerTop10ProductosMasVendidos(Pageable pageable);
    
    // consulta para obtener los productos mas vendidos por fechas
    @Query("SELECT dv.productoTalla, SUM(dv.cantidad) as totalCantidadVendida, COUNT(dv) as totalVentas, SUM(dv.importeTotal) as totalImporte " +
        "FROM DetalleVenta dv " +
        "JOIN dv.venta v " +
        "WHERE v.fecha BETWEEN :fechaInicio AND :fechaFin " +
        "GROUP BY dv.productoTalla " +
        "ORDER BY totalCantidadVendida DESC")
    List<Object[]> obtenerProductosMasVendidosPorFechas(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);
    
    @Query("SELECT i.productoTalla, 0L as totalCantidadVendida, 0L as totalVentas, 0.0 as totalImporte " +
           "FROM Inventario i " +
           "WHERE i.productoTalla.precioCompra > 0 " +
           "AND i.productoTalla NOT IN ( " +
           "    SELECT dv.productoTalla FROM DetalleVenta dv JOIN dv.venta v " +
           "    WHERE v.fecha BETWEEN :fechaInicio AND :fechaFin " +
           ")")
    List<Object[]> obtenerProductosSinVentas(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

}
