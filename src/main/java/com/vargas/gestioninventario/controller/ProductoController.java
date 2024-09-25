package com.vargas.gestioninventario.controller;

import com.vargas.gestioninventario.dto.ProductoVentaDTO;
import com.vargas.gestioninventario.entity.Producto;
import com.vargas.gestioninventario.service.ProductoService;
import com.vargas.gestioninventario.service.ProductoTallaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;
    
    @Autowired
    private ProductoTallaService productoTallaService;

    @GetMapping
    public List<Producto> getAllProductos() {
        return productoService.findAll();
    }

    @GetMapping("/{id}")
    public Producto getProductoById(@PathVariable Long id) {
        return productoService.findById(id);
    }

    @PostMapping
    public Producto createProducto(@RequestBody Producto producto) {
        return productoService.save(producto);
    }

    @PutMapping("/{id}")
    public Producto updateProducto(@PathVariable Long id, @RequestBody Producto producto) {
        Producto existingProducto = productoService.findById(id);
        if (existingProducto != null) {
            producto.setId(id);
            return productoService.save(producto);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteProducto(@PathVariable Long id) {
        productoService.deleteById(id);
    }
    
    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoVentaDTO>> buscarProductos(@RequestParam("q") String keyword) {
        List<ProductoVentaDTO> productos = productoTallaService.buscarProductosDisponibles(keyword);
        return ResponseEntity.ok(productos);
    }
}
