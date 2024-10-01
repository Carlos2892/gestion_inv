package com.vargas.gestioninventario.repository;

import com.vargas.gestioninventario.entity.TipoDocumento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Long> {
    List<TipoDocumento> findByIdNot(Long id);
}
