package com.vargas.gestioninventario.controller;

import com.vargas.gestioninventario.entity.DetalleVenta;
import com.vargas.gestioninventario.service.DetalleVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalleventas")
public class DetalleVentaController {
    @Autowired
    private DetalleVentaService detalleVentaService;

    @GetMapping
    public List<DetalleVenta> getAllDetalleVentas() {
        return detalleVentaService.findAll();
    }

    @GetMapping("/{id}")
    public DetalleVenta getDetalleVentaById(@PathVariable Long id) {
        return detalleVentaService.findById(id);
    }

    @PostMapping
    public DetalleVenta createDetalleVenta(@RequestBody DetalleVenta detalleVenta) {
        return detalleVentaService.save(detalleVenta);
    }

    @PutMapping("/{id}")
    public DetalleVenta updateDetalleVenta(@PathVariable Long id, @RequestBody DetalleVenta detalleVenta) {
        DetalleVenta existingDetalleVenta = detalleVentaService.findById(id);
        if (existingDetalleVenta != null) {
            detalleVenta.setId(id);
            return detalleVentaService.save(detalleVenta);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteDetalleVenta(@PathVariable Long id) {
        detalleVentaService.deleteById(id);
    }
}
