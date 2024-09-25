package com.vargas.gestioninventario.service.impl;

import com.vargas.gestioninventario.dto.ProductoVentaDTO;
import com.vargas.gestioninventario.entity.Inventario;
import com.vargas.gestioninventario.entity.ProductoTalla;
import com.vargas.gestioninventario.repository.InventarioRepository;
import com.vargas.gestioninventario.repository.ProductoTallaRepository;
import com.vargas.gestioninventario.service.ProductoTallaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoTallaServiceImpl implements ProductoTallaService {
    @Autowired
    private ProductoTallaRepository productoTallaRepository;
    
    @Autowired
    private InventarioRepository inventarioRepository;

    @Override
    public List<ProductoTalla> findAll() {
        return productoTallaRepository.findAll();
    }

    @Override
    public ProductoTalla findById(Long id) {
        return productoTallaRepository.findById(id).orElse(null);
    }

    @Override
    public ProductoTalla save(ProductoTalla productoTalla) {
        return productoTallaRepository.save(productoTalla);
    }

    @Override
    public void deleteById(Long id) {
        productoTallaRepository.deleteById(id);
    }
    
    @Override
    public List<ProductoVentaDTO> buscarProductosDisponibles(String keyword) {
        List<ProductoTalla> productos = productoTallaRepository.findAvailableProductsByKeyword(keyword);
        return productos.stream()
            .map(this::convertirToDTO)
            .collect(Collectors.toList());
    }

    private ProductoVentaDTO convertirToDTO(ProductoTalla productoTalla) {
        ProductoVentaDTO dto = new ProductoVentaDTO();
        Inventario inventario = inventarioRepository.findByProductoTalla(productoTalla);
        
        dto.setId(productoTalla.getId());
        dto.setDescripcionProducto(productoTalla.getProducto().getDescripcion()+" "+productoTalla.getProducto().getMarca().getNombre()+" "+productoTalla.getTalla().getNombre());
        dto.setPrecioVenta(productoTalla.getPrecioVenta());
        dto.setPrecioCompra(productoTalla.getPrecioCompra());
        dto.setStockActual(inventario.getStockActual());
        return dto;
    }
}
