package com.vargas.gestioninventario.controller;

import com.vargas.gestioninventario.entity.Categoria;
import com.vargas.gestioninventario.service.CategoriaService;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categorias2")
public class CategoriaController2 {

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping(value = "/acciones", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> manejarAcciones(@RequestBody Map<String, String> payload) {
        String action = payload.get("action");
        Long id = Long.parseLong(payload.get("id"));
        String nombre = payload.get("nombre");

        switch (action) {
            case "edit":
                return updateCategoria(id, nombre);
            case "delete":
                return toggleCategoria(id);
            default:
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    private ResponseEntity<?> updateCategoria(Long id, String nombre) {
        Categoria existingCategoria = categoriaService.findById(id);
        if (existingCategoria != null) {
            existingCategoria.setNombre(nombre);
            categoriaService.save(existingCategoria);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Se actualizó la Categoría");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    private ResponseEntity<?> toggleCategoria(Long id) {
        Categoria existingCategoria = categoriaService.findById(id);
        if (existingCategoria != null) {
            existingCategoria.setEstado(existingCategoria.getEstado().equals("A") ? "I" : "A");
            categoriaService.save(existingCategoria);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Se actualizó el estado de la Categoría");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

