package com.vargas.gestioninventario.controller;

import com.vargas.gestioninventario.entity.Rol;
import com.vargas.gestioninventario.entity.Usuario;
import com.vargas.gestioninventario.service.UsuarioService;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioService.findAll();
    }

    @GetMapping("/{id}")
    public Usuario getUsuarioById(@PathVariable Long id) {
        return usuarioService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createUsuario(@RequestBody Usuario usuario) {
        System.out.println("Datos del usuario recibidos: " + usuario);
        usuarioService.save(usuario);
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Usuario creado exitosamente");
        return ResponseEntity.ok(response);
    }
    
    @PutMapping(value = "/edit/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario existingUsuario = usuarioService.findById(id);
        if (existingUsuario != null) {
            usuario.setId(id);
            usuarioService.update(usuario);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Se actualizó el Usuario");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @PutMapping(value = "/toggle/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> toggleUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario existingUsuario = usuarioService.findById(id);
        if (existingUsuario != null) {
            usuario.setId(id);
            usuarioService.update(usuario);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Se actualizó el estado del Usuario");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @GetMapping("/{id}/roles")
    public ResponseEntity<Set<Rol>> getRolesByUsuarioId(@PathVariable Long id) {
        try {
            Set<Rol> roles = usuarioService.getRolesByUsuarioId(id);
            return ResponseEntity.ok(roles);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/{id}/roles")
    public ResponseEntity<Map<String, String>> updateRolesForUsuario(@PathVariable Long id, @RequestBody Set<Long> rolIds) {
        try {
            usuarioService.updateRolesForUsuario(id, rolIds);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Se actualizó los perfiles del Usuario");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteById(id);
    }
}
