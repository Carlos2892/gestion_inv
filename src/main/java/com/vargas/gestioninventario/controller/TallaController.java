package com.vargas.gestioninventario.controller;

import com.vargas.gestioninventario.entity.Talla;
import com.vargas.gestioninventario.service.TallaService;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/tallas")
public class TallaController {
    @Autowired
    private TallaService tallaService;

    @GetMapping
    public List<Talla> getAllTallas() {
        return tallaService.findAll();
    }
    
    @GetMapping("/activas")
    public List<Talla> getCategoriasActivas() {
        return tallaService.findByEstadoActivo();
    }

    @GetMapping("/{id}")
    public Talla getTallaById(@PathVariable Long id) {
        return tallaService.findById(id);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> createCategoria(@RequestBody Talla talla) {
        talla.setEstado("A");
        tallaService.save(talla);
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Se registro la Talla correctamente");
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/edit/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> updateCategoria(@PathVariable Long id, @RequestBody Talla talla) {
        Talla existingCategoria = tallaService.findById(id);
        if (existingCategoria != null) {
            talla.setId(id);
            tallaService.save(talla);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Se actualizó la Talla");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @PutMapping(value = "/toggle/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> toggleCategoria(@PathVariable Long id, @RequestBody Talla talla) {
        Talla existingCategoria = tallaService.findById(id);
        if (existingCategoria != null) {
            talla.setId(id);
            tallaService.save(talla);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Se actualizó el estado de la Talla");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteTalla(@PathVariable Long id) {
        tallaService.deleteById(id);
    }
}
