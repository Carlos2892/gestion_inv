package com.vargas.gestioninventario.repository;

import com.vargas.gestioninventario.entity.Rol;
import com.vargas.gestioninventario.entity.Usuario;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
@Query("SELECT u.roles FROM Usuario u WHERE u.id = :userId")
    Set<Rol> findRolesByUsuarioId(@Param("userId") Long userId);
    
    @Query("SELECT u FROM Usuario u JOIN FETCH u.roles WHERE u.username = :username")
    Usuario getUserByUsername(@Param("username") String username);
}
