
package com.vargas.gestioninventario.repository;

import com.vargas.gestioninventario.entity.CruceInventario;
import com.vargas.gestioninventario.entity.DetalleCruceInventario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleCruceInventarioRepository extends JpaRepository<DetalleCruceInventario, Long> {

    // Encontrar detalles de un cruce específico
    List<DetalleCruceInventario> findByCruceInventario(CruceInventario cruceInventario);
    
    void deleteByCruceInventarioId(Long cruceInventarioId);
}
