package com.vargas.gestioninventario.controller;

import com.vargas.gestioninventario.entity.Talla;
import com.vargas.gestioninventario.service.TallaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tallas")
public class TallaController {
    @Autowired
    private TallaService tallaService;

    @GetMapping
    public List<Talla> getAllTallas() {
        return tallaService.findAll();
    }

    @GetMapping("/{id}")
    public Talla getTallaById(@PathVariable Long id) {
        return tallaService.findById(id);
    }

    @PostMapping
    public Talla createTalla(@RequestBody Talla talla) {
        return tallaService.save(talla);
    }

    @PutMapping("/{id}")
    public Talla updateTalla(@PathVariable Long id, @RequestBody Talla talla) {
        Talla existingTalla = tallaService.findById(id);
        if (existingTalla != null) {
            talla.setId(id);
            return tallaService.save(talla);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteTalla(@PathVariable Long id) {
        tallaService.deleteById(id);
    }
}
