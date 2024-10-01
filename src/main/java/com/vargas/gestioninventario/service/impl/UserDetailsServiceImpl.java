
package com.vargas.gestioninventario.service.impl;

import com.vargas.gestioninventario.entity.Usuario;
import com.vargas.gestioninventario.service.UsuarioService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioService usuarioService;

    public UserDetailsServiceImpl(@Lazy UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Encuentra al usuario en la base de datos
        Usuario usuario = usuarioService.findByUsername(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        // Aquí simplemente devolvemos el usuario tal como es, ya que implementa UserDetails
        return usuario;
    }
}