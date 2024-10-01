package com.vargas.gestioninventario.controller;

import com.vargas.gestioninventario.dto.VentaDTO;
import com.vargas.gestioninventario.dto.VentaSaveDTO;
import com.vargas.gestioninventario.entity.Venta;
import com.vargas.gestioninventario.service.VentaService;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {
    @Autowired
    private VentaService ventaService;

    //@GetMapping
    //public List<Venta> getAllVentas() {
     //   return ventaService.findAll();
    //}
    
    @GetMapping
    public ResponseEntity<List<VentaDTO>> listarVentas() {
        List<VentaDTO> ventas = ventaService.listarVentas();
        return ResponseEntity.ok(ventas);
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
            Venta nuevaVenta = ventaService.registrarVenta(ventaSaveDTO);
            response.put("status", "success");
            response.put("id", nuevaVenta.getId().toString());
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
    
    @GetMapping("/imprimir/{ventaId}")
    public ResponseEntity<InputStreamResource> imprimirVenta(@PathVariable Long ventaId) {
        ByteArrayInputStream bis = ventaService.generarPdfVenta(ventaId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=venta_" + ventaId + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
    
    @GetMapping("/ventas-hoy")
    public ResponseEntity<Long> contarVentasDelDia() {
        long totalVentasHoy = ventaService.contarVentasDelDia();
        return ResponseEntity.ok(totalVentasHoy);
    }
    
    @GetMapping("/total-dia")
    public ResponseEntity<Double> obtenerTotalVentasDelDia() {
        Double totalVentasDelDia = ventaService.obtenerTotalVentasDelMes();
        return ResponseEntity.ok(totalVentasDelDia);
    }
    
    @GetMapping("/mensuales")
    public ResponseEntity<List<Object[]>> obtenerVentasMensuales() {
        List<Object[]> ventasMensuales = ventaService.obtenerVentasMensualesDelAnio();
        return new ResponseEntity<>(ventasMensuales, HttpStatus.OK);
    }
    
    @GetMapping("/ventas-por-categoria")
    public ResponseEntity<List<Map<String, Object>>> obtenerVentasPorCategoria() {
        // Llamamos al servicio para obtener el resultado
        List<Map<String, Object>> ventasPorCategoria = ventaService.obtenerVentasPorCategoriaDelMes();
        return ResponseEntity.ok(ventasPorCategoria);
    }
}
