package com.vargas.gestioninventario.controller;

import com.vargas.gestioninventario.entity.TipoDocumento;
import com.vargas.gestioninventario.service.TipoDocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/tipodocumentos")
public class TipoDocumentoController {
    @Autowired
    private TipoDocumentoService tipoDocumentoService;

    @GetMapping
    public List<TipoDocumento> getAllTipoDocumentos() {
        return tipoDocumentoService.findAll();
    }
    
    @GetMapping("/listar")
    public ResponseEntity<List<TipoDocumento>> listarTipoDocumentos() {
        List<TipoDocumento> tiposDocumentos = tipoDocumentoService.listarTodosExceptoIdCero();
        return ResponseEntity.ok(tiposDocumentos);
    }

    @GetMapping("/{id}")
    public TipoDocumento getTipoDocumentoById(@PathVariable Long id) {
        return tipoDocumentoService.findById(id);
    }

    @PostMapping
    public TipoDocumento createTipoDocumento(@RequestBody TipoDocumento tipoDocumento) {
        return tipoDocumentoService.save(tipoDocumento);
    }

    @PutMapping("/{id}")
    public TipoDocumento updateTipoDocumento(@PathVariable Long id, @RequestBody TipoDocumento tipoDocumento) {
        TipoDocumento existingTipoDocumento = tipoDocumentoService.findById(id);
        if (existingTipoDocumento != null) {
            tipoDocumento.setId(id);
            return tipoDocumentoService.save(tipoDocumento);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteTipoDocumento(@PathVariable Long id) {
        tipoDocumentoService.deleteById(id);
    }
}
