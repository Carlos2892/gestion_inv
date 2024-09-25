
package com.vargas.gestioninventario.controller;

import com.vargas.gestioninventario.entity.Usuario;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("/api/user")
    public ResponseEntity<Map<String, Object>> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> userInfo = new HashMap<>();

        if (authentication != null && authentication.getPrincipal() instanceof Usuario) {
            Usuario usuario = (Usuario) authentication.getPrincipal();
            userInfo.put("username", usuario.getUsername());
            userInfo.put("nombre", usuario.getNombre());
            userInfo.put("roles", usuario.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet()));
            return ResponseEntity.ok(userInfo);
        } else {
            throw new RuntimeException("Usuario no autenticado o tipo de principal incorrecto");
        }
    }
}
