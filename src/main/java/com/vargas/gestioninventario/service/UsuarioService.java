package com.vargas.gestioninventario.service;

import com.vargas.gestioninventario.entity.Rol;
import com.vargas.gestioninventario.entity.Usuario;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UsuarioService {
    List<Usuario> findAll();
    Usuario findById(Long id);
    Usuario update(Usuario usuario);
    Usuario save(Usuario usuario);
    void deleteById(Long id);
    Set<Rol> getRolesByUsuarioId(Long id);
    void updateRolesForUsuario(Long id, Set<Long> rolIds);
    Usuario findByUsername(String username);
}
