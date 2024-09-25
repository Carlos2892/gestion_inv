
package com.vargas.gestioninventario.repository;

import com.vargas.gestioninventario.entity.HistoricoCambios;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HistoricoCambiosRepository extends JpaRepository<HistoricoCambios, Long>{
    
    List<HistoricoCambios> findByCruceInventarioId(Long cruceId);
    
}
