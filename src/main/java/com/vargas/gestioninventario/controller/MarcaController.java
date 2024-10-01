package com.vargas.gestioninventario.controller;

import com.vargas.gestioninventario.entity.Marca;
import com.vargas.gestioninventario.service.MarcaService;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/marcas")
public class MarcaController {
    @Autowired
    private MarcaService marcaService;

    @GetMapping
    public List<Marca> getAllMarcas() {
        return marcaService.findAll();
    }
    
    @GetMapping("/activas")
    public List<Marca> getMarcasActivas() {
        return marcaService.findByEstadoActivo();
    }

    @GetMapping("/{id}")
    public Marca getMarcaById(@PathVariable Long id) {
        return marcaService.findById(id);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> createMarca(@RequestBody Marca marca) {
        marca.setEstado("A");
        marcaService.save(marca);
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Se registro la Marca correctamente");
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/edit/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> updateMarca(@PathVariable Long id, @RequestBody Marca marca) {
        Marca existingMarca = marcaService.findById(id);
        if (existingMarca != null) {
            marca.setId(id);
            marcaService.save(marca);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Se actualizó la Categoría");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @PutMapping(value = "/toggle/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> toggleMarca(@PathVariable Long id, @RequestBody Marca marca) {
        Marca existingMarca = marcaService.findById(id);
        if (existingMarca != null) {
            marca.setId(id);
            marcaService.save(marca);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Se actualizó el estado de la Categoría");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteMarca(@PathVariable Long id) {
        marcaService.deleteById(id);
    }
}
