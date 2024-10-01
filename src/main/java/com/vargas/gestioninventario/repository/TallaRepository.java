package com.vargas.gestioninventario.repository;

import com.vargas.gestioninventario.entity.Talla;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TallaRepository extends JpaRepository<Talla, Long> {
    List<Talla> findByEstado(String estado);
}
