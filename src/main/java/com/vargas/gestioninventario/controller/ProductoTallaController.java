package com.vargas.gestioninventario.controller;

import com.vargas.gestioninventario.entity.ProductoTalla;
import com.vargas.gestioninventario.service.ProductoTallaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productotallas")
public class ProductoTallaController {
    @Autowired
    private ProductoTallaService productoTallaService;

    @GetMapping
    public List<ProductoTalla> getAllProductoTallas() {
        return productoTallaService.findAll();
    }

    @GetMapping("/{id}")
    public ProductoTalla getProductoTallaById(@PathVariable Long id) {
        return productoTallaService.findById(id);
    }

    @PostMapping
    public ProductoTalla createProductoTalla(@RequestBody ProductoTalla productoTalla) {
        return productoTallaService.save(productoTalla);
    }

    @PutMapping("/{id}")
    public ProductoTalla updateProductoTalla(@PathVariable Long id, @RequestBody ProductoTalla productoTalla) {
        ProductoTalla existingProductoTalla = productoTallaService.findById(id);
        if (existingProductoTalla != null) {
            productoTalla.setId(id);
            return productoTallaService.save(productoTalla);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteProductoTalla(@PathVariable Long id) {
        productoTallaService.deleteById(id);
    }
}
