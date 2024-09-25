package com.vargas.gestioninventario.controller;

import com.vargas.gestioninventario.dto.CompraDTO;
import com.vargas.gestioninventario.dto.CompraSaveDTO;
import com.vargas.gestioninventario.entity.Compra;
import com.vargas.gestioninventario.service.CompraService;
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
@RequestMapping("/api/compras")
public class CompraController {
    @Autowired
    private CompraService compraService;

    @GetMapping
    public ResponseEntity<List<CompraDTO>> listarCompras() {
        List<CompraDTO> compras = compraService.listarCompras();
        return ResponseEntity.ok(compras);
    }

    @GetMapping("/{compraId}")
    public ResponseEntity<CompraDTO> obtenerCompraPorId(@PathVariable Long compraId) {
        CompraDTO compraDTO = compraService.obtenerCompraPorId(compraId);
        return ResponseEntity.ok(compraDTO);
    }

    @PostMapping("/registrar")
    public ResponseEntity<Map<String, String>> registrarCompra(@RequestBody CompraSaveDTO compraSaveDTO) {
        Map<String, String> response = new HashMap<>();
        try {
            compraService.registrarCompra(compraSaveDTO);
            response.put("status", "success");
            response.put("mensaje", "Compra registrada con éxito");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("status", "error");
            response.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("mensaje", "Ocurrió un error al registrar la compra");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/editar")
    public ResponseEntity<Map<String, String>> editarCompra(@RequestBody CompraSaveDTO compraSaveDTO) {
        Map<String, String> response = new HashMap<>();
        try {
            compraService.editarCompra(compraSaveDTO);
            response.put("status", "success");
            response.put("mensaje", "Compra editada con éxito.");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("status", "error");
            response.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("mensaje", "Error al editar la compra.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @PostMapping("/confirmar")
    public ResponseEntity<Map<String, String>> confirmarCompra(@RequestBody Map<String, Long> request) {
        Long compraId = request.get("id");
        System.out.println(compraId);
        Map<String, String> response = new HashMap<>();
        try {
            compraService.confirmarCompra(compraId);
            response.put("status", "success");
            response.put("mensaje", "Compra confirmada y stock actualizado correctamente.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("mensaje", "Error al confirmar la compra.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @GetMapping("/imprimir/{compraId}")
    public ResponseEntity<InputStreamResource> imprimirCompra(@PathVariable Long compraId) {
        ByteArrayInputStream bis = compraService.generarPdfCompra(compraId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=compra_" + compraId + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @DeleteMapping("/{id}")
    public void deleteCompra(@PathVariable Long id) {
        compraService.deleteById(id);
    }
}
