package com.vargas.gestioninventario.repository;

import com.vargas.gestioninventario.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    @Query("SELECT COALESCE(MAX(CAST(v.correlativo AS int)), 0) FROM Venta v")
    Integer obtenerUltimoCorrelativo();
}
