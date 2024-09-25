
package com.vargas.gestioninventario.repository;

import com.vargas.gestioninventario.entity.CruceInventario;
import com.vargas.gestioninventario.entity.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CruceInventarioRepository extends JpaRepository<CruceInventario, Long> {
    
    // Encontrar cruces por usuario
    List<CruceInventario> findByUsuario(Usuario usuario);
    
    // Encontrar cruce por ID y usuario (para verificar permisos de edición)
    CruceInventario findByIdAndUsuario(Long id, Usuario usuario);
}
