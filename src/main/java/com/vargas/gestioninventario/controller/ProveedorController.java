package com.vargas.gestioninventario.controller;

import com.vargas.gestioninventario.dto.ProveedorDTO;
import com.vargas.gestioninventario.entity.Proveedor;
import com.vargas.gestioninventario.service.ProveedorService;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {
    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    public List<Proveedor> getAllProveedores() {
        return proveedorService.findAll();
    }

    @GetMapping("/{id}")
    public Proveedor getProveedorById(@PathVariable Long id) {
        return proveedorService.findById(id);
    }

    @PostMapping("/crear")
    public ResponseEntity<Map<String, String>> crearProveedor(@RequestBody ProveedorDTO proveedorDTO) {
        Map<String, String> response = new HashMap<>();
        try {
            Proveedor nuevoProveedor = proveedorService.save(proveedorDTO);
            response.put("status", "success");
            response.put("id", nuevoProveedor.getId().toString());
            response.put("mensaje", "Proveedor registrado con éxito");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("status", "error");
            response.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("mensaje", "Ocurrió un error al registrar el proveedor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Map<String, String>> editarProveedor(@PathVariable Long id, @RequestBody ProveedorDTO proveedorDTO) {
        Map<String, String> response = new HashMap<>();
        try {
            proveedorService.update(id, proveedorDTO);
            response.put("status", "success");
            response.put("mensaje", "Proveedor actualizado con éxito");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("mensaje", "Ocurrió un error al actualizar el proveedor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteProveedor(@PathVariable Long id) {
        proveedorService.deleteById(id);
    }
}
