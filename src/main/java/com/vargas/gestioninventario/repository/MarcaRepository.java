package com.vargas.gestioninventario.repository;

import com.vargas.gestioninventario.entity.Marca;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {
    List<Marca> findByEstado(String estado);
}
