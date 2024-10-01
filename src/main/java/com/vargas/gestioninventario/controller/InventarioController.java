package com.vargas.gestioninventario.controller;

import com.vargas.gestioninventario.dto.InventarioSaveDTO;
import com.vargas.gestioninventario.dto.ProductoInventarioDTO;
import com.vargas.gestioninventario.entity.Inventario;
import com.vargas.gestioninventario.service.InventarioService;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/inventarios")
public class InventarioController {
    @Autowired
    private InventarioService inventarioService;

    //@GetMapping
    //public List<Inventario> getAllInventarios() {
    //    return inventarioService.findAll();
    //}

    @GetMapping("/{id}")
    public Inventario getInventarioById(@PathVariable Long id) {
        return inventarioService.findById(id);
    }

    //@PostMapping
    //public Inventario createInventario(@RequestBody Inventario inventario) {
    //    return inventarioService.save(inventario);
    //}

    //@PutMapping("/{id}")
    //public Inventario updateInventario(@PathVariable Long id, @RequestBody Inventario inventario) {
    //    Inventario existingInventario = inventarioService.findById(id);
    //    if (existingInventario != null) {
    //        inventario.setId(id);
    //        return inventarioService.save(inventario);
    //    }
    //    return null;
    //}

    @DeleteMapping("/{id}")
    public void deleteInventario(@PathVariable Long id) {
        inventarioService.deleteById(id);
    }
    
    // controladores para gestionar el inventario con DTO
    
    @GetMapping
    public ResponseEntity<List<ProductoInventarioDTO>> listarInventarios() {
        List<ProductoInventarioDTO> inventarios = inventarioService.listarInventario();
        return ResponseEntity.ok(inventarios);
    }
    
    @GetMapping("/stockDisponible")
    public ResponseEntity<List<ProductoInventarioDTO>> listarInventariosActivos() {
        List<ProductoInventarioDTO> inventarios = inventarioService.listarInventarioConStock();
        return ResponseEntity.ok(inventarios);
    }
    
    @PostMapping
    public ResponseEntity<Map<String, String>> crearInventarioProductoTalla(@RequestBody InventarioSaveDTO inventarioSaveDTO) {
        try {
            inventarioService.crearProductoConTallasEInventarios(inventarioSaveDTO);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Producto creado con éxito");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> actualizarProductoTallaEInventario(@PathVariable Long id, @RequestBody InventarioSaveDTO inventarioSaveDTO) {
        inventarioService.actualizarProductoTallaEInventario(id, inventarioSaveDTO);
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Producto actualizado con éxito");
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/updateStock/{id}")
    public ResponseEntity<Map<String, String>> updateStock(@PathVariable Long id, @RequestBody InventarioSaveDTO inventarioSaveDTO) {
        inventarioService.updateStock(id, inventarioSaveDTO);
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Stock actualizado con éxito");
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/toggle/{id}")
    public ResponseEntity<Map<String, String>> toggleInventario(@PathVariable Long id, @RequestBody InventarioSaveDTO inventarioSaveDTO) {
        inventarioService.toggleProductoEnInventario(id, inventarioSaveDTO.getEstadoProductoTalla());
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Se actualizó el estado del producto en Inventario");
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/poco-stock")
    public ResponseEntity<Long> contarProductosConPocoStock() {
        long totalProductosPocoStock = inventarioService.contarProductosConPocoStock();
        return ResponseEntity.ok(totalProductosPocoStock);
    }
    
    @GetMapping("/poco-stock-lista")
    public ResponseEntity<List<ProductoInventarioDTO>> obtenerProductosPocoStock() {
        List<ProductoInventarioDTO> productosPocoStock = inventarioService.obtenerProductosPocoStock();
        return ResponseEntity.ok(productosPocoStock);
    }
    
    @GetMapping("/valor-total")
    public ResponseEntity<Double> obtenerValorTotalInventario() {
        Double valorTotalInventario = inventarioService.obtenerValorTotalInventario();
        return ResponseEntity.ok(valorTotalInventario);
    }
}
