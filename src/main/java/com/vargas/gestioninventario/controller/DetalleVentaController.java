package com.vargas.gestioninventario.controller;

import com.vargas.gestioninventario.dto.ProductoMasVendidoDTO;
import com.vargas.gestioninventario.entity.DetalleVenta;
import com.vargas.gestioninventario.service.DetalleVentaService;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;

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
    
    @GetMapping("/ganancias-mes")
    public ResponseEntity<Double> obtenerGananciasDelMes() {
        Double gananciasDelMes = detalleVentaService.obtenerGananciasDelMes();
        return ResponseEntity.ok(gananciasDelMes);
    }
    
    @GetMapping("/top10-productos-vendidos")
    public ResponseEntity<List<ProductoMasVendidoDTO>> obtenerTop10ProductosMasVendidos() {
        List<ProductoMasVendidoDTO> productosVendidos = detalleVentaService.obtenerTop10ProductosMasVendidos();
        return ResponseEntity.ok(productosVendidos);
    }
    
    @GetMapping("/mas-vendidos")
    public ResponseEntity<List<ProductoMasVendidoDTO>> obtenerProductosMasVendidosPorFechas(
        @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
        @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        List<ProductoMasVendidoDTO> productosVendidos = detalleVentaService.obtenerProductosMasVendidosPorFechas(fechaInicio, fechaFin);
        return ResponseEntity.ok(productosVendidos);
    }
    
    @GetMapping("/sin-ventas")
    public ResponseEntity<List<ProductoMasVendidoDTO>> obtenerProductosSinVentasPorFechas(
        @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
        @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        List<ProductoMasVendidoDTO> productosSinVentas = detalleVentaService.obtenerProductosSinVentasPorFechas(fechaInicio, fechaFin);
        return ResponseEntity.ok(productosSinVentas);
    }
}
