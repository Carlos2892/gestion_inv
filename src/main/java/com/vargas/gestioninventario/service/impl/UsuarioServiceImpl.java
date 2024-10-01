package com.vargas.gestioninventario.service.impl;

import com.vargas.gestioninventario.entity.Rol;
import com.vargas.gestioninventario.entity.Usuario;
import com.vargas.gestioninventario.repository.RolRepository;
import com.vargas.gestioninventario.repository.UsuarioRepository;
import com.vargas.gestioninventario.service.UsuarioService;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private RolRepository rolRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public Usuario update(Usuario usuario) {
        if (usuario.getId() != null) {
            Usuario existingUsuario = usuarioRepository.findById(usuario.getId()).orElse(null);
            if (existingUsuario != null) {
                usuario.setRoles(existingUsuario.getRoles());
            }
        }
        return usuarioRepository.save(usuario);
    }
    
    @Override
    public Usuario save(Usuario usuario) {
        // Encriptar la contraseña antes de guardar el usuario
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            String passwordEncriptada = passwordEncoder.encode(usuario.getPassword());
            usuario.setPassword(passwordEncriptada);
        }

        // Manejar los roles asociados
        if (usuario.getRoleIds() != null && !usuario.getRoleIds().isEmpty()) {
            Set<Rol> roles = new HashSet<>();
            for (Long roleId : usuario.getRoleIds()) {
                Rol rolEntity = rolRepository.findById(roleId)
                        .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
                roles.add(rolEntity);
            }
            usuario.setRoles(roles);
            usuario.setEstado("A"); // Se asume que el estado siempre es "A" al crear un nuevo usuario
        }

        // Guardar el usuario en la base de datos
        return usuarioRepository.save(usuario);
    }

    @Override
    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }
    
    @Override
    public Set<Rol> getRolesByUsuarioId(Long id) {
        return usuarioRepository.findRolesByUsuarioId(id);
    }

    @Override
    public void updateRolesForUsuario(Long id, Set<Long> rolIds) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Set<Rol> roles = new HashSet<>(rolRepository.findAllById(rolIds));
        usuario.setRoles(roles);
        usuarioRepository.save(usuario);
    }
    
    @Override
    public Usuario findByUsername(String username) {
        return usuarioRepository.getUserByUsername(username);
    }
}
