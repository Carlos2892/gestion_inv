package com.vargas.gestioninventario.repository;

import com.vargas.gestioninventario.entity.Kardex;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KardexRepository extends JpaRepository<Kardex, Long> {
    @Query("SELECT SUM(k.cantidad * k.precioUnitario) / SUM(k.cantidad) " +
        "FROM Kardex k " +
        "WHERE k.productoTalla.id = :productoTallaId AND k.concepto = 'COMPRA'")
    Double findPrecioPromedioPonderado(@Param("productoTallaId") Long productoTallaId);
    
    // Método para buscar todos los registros de Kardex por productoTallaId
    List<Kardex> findByProductoTallaId(Long productoTallaId);

    // Método para filtrar los registros de Kardex por fechas, concepto y productoTallaId
    @Query("SELECT k FROM Kardex k WHERE (:productoTallaId IS NULL OR k.productoTalla.id = :productoTallaId) " +
       "AND (:concepto IS NULL OR :concepto = '' OR k.concepto = :concepto) " +
       "AND (k.fechaMovimiento BETWEEN :fechaInicio AND :fechaFin)")
    List<Kardex> filtrarKardex(@Param("productoTallaId") Long productoTallaId,
                               @Param("fechaInicio") LocalDate fechaInicio,
                               @Param("fechaFin") LocalDate fechaFin,
                               @Param("concepto") String concepto);

}
