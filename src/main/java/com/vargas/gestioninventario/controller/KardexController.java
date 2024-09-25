package com.vargas.gestioninventario.controller;

import com.vargas.gestioninventario.dto.KardexDTO;
import com.vargas.gestioninventario.dto.KardexFilterDTO;
import com.vargas.gestioninventario.entity.Kardex;
import com.vargas.gestioninventario.service.KardexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/kardex")
public class KardexController {
    @Autowired
    private KardexService kardexService;

    @GetMapping
    public List<Kardex> getAllKardexes() {
        return kardexService.findAll();
    }

    @GetMapping("/buscarporid/{id}")
    public Kardex getKardexById(@PathVariable Long id) {
        return kardexService.findById(id);
    }

    @PostMapping
    public Kardex createKardex(@RequestBody Kardex kardex) {
        return kardexService.save(kardex);
    }

    @PutMapping("/{id}")
    public Kardex updateKardex(@PathVariable Long id, @RequestBody Kardex kardex) {
        Kardex existingKardex = kardexService.findById(id);
        if (existingKardex != null) {
            kardex.setId(id);
            return kardexService.save(kardex);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteKardex(@PathVariable Long id) {
        kardexService.deleteById(id);
    }
    
    @GetMapping("/{idProductoTalla}")
    public ResponseEntity<List<KardexDTO>> obtenerKardexPorProductoTalla(@PathVariable Long idProductoTalla) {
        List<KardexDTO> kardexList = kardexService.obtenerKardexPorProductoTalla(idProductoTalla);
        return ResponseEntity.ok(kardexList);
    }
    
    @PostMapping("/filtrar")
    public ResponseEntity<List<KardexDTO>> filtrarKardex(@RequestBody KardexFilterDTO filtro) {
        List<KardexDTO> resultados = kardexService.filtrarKardex(filtro);
        return ResponseEntity.ok(resultados);
    }
}
