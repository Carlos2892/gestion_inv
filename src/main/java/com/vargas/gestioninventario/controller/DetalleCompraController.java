package com.vargas.gestioninventario.controller;

import com.vargas.gestioninventario.entity.DetalleCompra;
import com.vargas.gestioninventario.service.DetalleCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detallecompras")
public class DetalleCompraController {
    @Autowired
    private DetalleCompraService detalleCompraService;

    @GetMapping
    public List<DetalleCompra> getAllDetalleCompras() {
        return detalleCompraService.findAll();
    }

    @GetMapping("/{id}")
    public DetalleCompra getDetalleCompraById(@PathVariable Long id) {
        return detalleCompraService.findById(id);
    }

    @PostMapping
    public DetalleCompra createDetalleCompra(@RequestBody DetalleCompra detalleCompra) {
        return detalleCompraService.save(detalleCompra);
    }

    @PutMapping("/{id}")
    public DetalleCompra updateDetalleCompra(@PathVariable Long id, @RequestBody DetalleCompra detalleCompra) {
        DetalleCompra existingDetalleCompra = detalleCompraService.findById(id);
        if (existingDetalleCompra != null) {
            detalleCompra.setId(id);
            return detalleCompraService.save(detalleCompra);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteDetalleCompra(@PathVariable Long id) {
        detalleCompraService.deleteById(id);
    }
}
