package com.vargas.gestioninventario.controller;

import com.vargas.gestioninventario.dto.VentaSaveDTO;
import com.vargas.gestioninventario.entity.Venta;
import com.vargas.gestioninventario.service.VentaService;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {
    @Autowired
    private VentaService ventaService;

    @GetMapping
    public List<Venta> getAllVentas() {
        return ventaService.findAll();
    }

    @GetMapping("/{id}")
    public Venta getVentaById(@PathVariable Long id) {
        return ventaService.findById(id);
    }

    @PostMapping
    public Venta createVenta(@RequestBody Venta venta) {
        return ventaService.save(venta);
    }

    @PutMapping("/{id}")
    public Venta updateVenta(@PathVariable Long id, @RequestBody Venta venta) {
        Venta existingVenta = ventaService.findById(id);
        if (existingVenta != null) {
            venta.setId(id);
            return ventaService.save(venta);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteVenta(@PathVariable Long id) {
        ventaService.deleteById(id);
    }
    
    @GetMapping("/correlativo")
    public ResponseEntity<Map<String, Integer>> obtenerCorrelativo() {
        Integer ultimoCorrelativo = ventaService.obtenerUltimoCorrelativo();
        Map<String, Integer> response = new HashMap<>();
        response.put("ultimoCorrelativo", ultimoCorrelativo);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/registrar")
    public ResponseEntity<Map<String, String>> registrarVenta(@RequestBody VentaSaveDTO ventaSaveDTO) {
        Map<String, String> response = new HashMap<>();
        try {
            ventaService.registrarVenta(ventaSaveDTO);
            response.put("status", "success");
            response.put("mensaje", "Venta registrada con éxito");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("status", "error");
            response.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("mensaje", "Ocurrió un error al registrar la venta");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
