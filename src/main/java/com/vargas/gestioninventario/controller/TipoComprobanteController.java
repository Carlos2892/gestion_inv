package com.vargas.gestioninventario.controller;

import com.vargas.gestioninventario.entity.TipoComprobante;
import com.vargas.gestioninventario.service.TipoComprobanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipocomprobantes")
public class TipoComprobanteController {
    @Autowired
    private TipoComprobanteService tipoComprobanteService;

    @GetMapping
    public List<TipoComprobante> getAllTipoComprobantes() {
        return tipoComprobanteService.findAll();
    }

    @GetMapping("/{id}")
    public TipoComprobante getTipoComprobanteById(@PathVariable Long id) {
        return tipoComprobanteService.findById(id);
    }

    @PostMapping
    public TipoComprobante createTipoComprobante(@RequestBody TipoComprobante tipoComprobante) {
        return tipoComprobanteService.save(tipoComprobante);
    }

    @PutMapping("/{id}")
    public TipoComprobante updateTipoComprobante(@PathVariable Long id, @RequestBody TipoComprobante tipoComprobante) {
        TipoComprobante existingTipoComprobante = tipoComprobanteService.findById(id);
        if (existingTipoComprobante != null) {
            tipoComprobante.setId(id);
            return tipoComprobanteService.save(tipoComprobante);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteTipoComprobante(@PathVariable Long id) {
        tipoComprobanteService.deleteById(id);
    }
}
