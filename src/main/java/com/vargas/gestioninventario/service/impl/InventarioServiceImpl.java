package com.vargas.gestioninventario.service.impl;

import com.vargas.gestioninventario.dto.InventarioSaveDTO;
import com.vargas.gestioninventario.dto.ProductoInventarioDTO;
import com.vargas.gestioninventario.entity.Categoria;
import com.vargas.gestioninventario.entity.Inventario;
import com.vargas.gestioninventario.entity.Marca;
import com.vargas.gestioninventario.entity.Producto;
import com.vargas.gestioninventario.entity.ProductoTalla;
import com.vargas.gestioninventario.entity.Talla;
import com.vargas.gestioninventario.repository.InventarioRepository;
import com.vargas.gestioninventario.repository.ProductoRepository;
import com.vargas.gestioninventario.repository.ProductoTallaRepository;
import com.vargas.gestioninventario.service.InventarioService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventarioServiceImpl implements InventarioService {
    
    @Autowired
    private InventarioRepository inventarioRepository;
    
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoTallaRepository productoTallaRepository;

    @Override
    public List<Inventario> findAll() {
        return inventarioRepository.findAll();
    }

    @Override
    public Inventario findById(Long id) {
        return inventarioRepository.findById(id).orElse(null);
    }

    @Override
    public Inventario save(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }

    @Override
    public void deleteById(Long id) {
        inventarioRepository.deleteById(id);
    }
    
    // aca esta para listar los productos para el inventario, se usa un DTO para recoger los datos necesarios de las entidades relacionadas con el producto y su gestion de inventario
    @Override
    public List<ProductoInventarioDTO> listarInventario() {
        List<Inventario> inventarios = inventarioRepository.findAllWithProductoAndTalla();
        return inventarios.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    
    @Override
    public List<ProductoInventarioDTO> listarInventarioConStock() {
        List<Inventario> inventarios = inventarioRepository.findAllWithProductoAndTallaWhereStockActual();
        return inventarios.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private ProductoInventarioDTO convertToDTO(Inventario inventario) {
        ProductoInventarioDTO dto = new ProductoInventarioDTO();
        dto.setIdInventario(inventario.getId());
        dto.setIdProductoTalla(inventario.getProductoTalla().getId());
        dto.setDescripcionProducto(inventario.getProductoTalla().getProducto().getDescripcion());
        dto.setNombreMarca(inventario.getProductoTalla().getProducto().getMarca().getNombre());
        dto.setNombreCategoria(inventario.getProductoTalla().getProducto().getCategoria().getNombre());
        dto.setNombreTalla(inventario.getProductoTalla().getTalla().getNombre());
        dto.setPrecioVenta(inventario.getProductoTalla().getPrecioVenta());
        dto.setPrecioCompra(inventario.getProductoTalla().getPrecioCompra());
        dto.setStockActual(inventario.getStockActual());
        dto.setStockMinimo(inventario.getStockMinimo());
        dto.setFechaActualizacion(inventario.getFechaActualizacion());
        dto.setEstadoProductoTalla(inventario.getProductoTalla().getEstado());
        dto.setIdMarca(inventario.getProductoTalla().getProducto().getMarca().getId());
        dto.setIdCategoria(inventario.getProductoTalla().getProducto().getCategoria().getId());
        dto.setIdTalla(inventario.getProductoTalla().getTalla().getId());
        dto.setDescripcionCompletaproducto(inventario.getProductoTalla().getProducto().getDescripcion()+" - "+inventario.getProductoTalla().getProducto().getMarca().getNombre()+" - Talla: "+inventario.getProductoTalla().getTalla().getNombre());
        return dto;
    }
    
    @Override
    @Transactional
    public void crearProductoConTallasEInventarios(InventarioSaveDTO inventarioSaveDTO) {
        // Crear Producto
        Producto producto = new Producto();
        producto.setDescripcion(inventarioSaveDTO.getDescripcion());
        producto.setPrecioVenta(inventarioSaveDTO.getPrecioVenta());
        producto.setPrecioCompra(0.0); // O el valor que corresponda
        producto.setEstado("A");

        Marca marca = new Marca();
        marca.setId(inventarioSaveDTO.getIdMarca());
        producto.setMarca(marca);

        Categoria categoria = new Categoria();
        categoria.setId(inventarioSaveDTO.getIdCategoria());
        producto.setCategoria(categoria);

        producto = productoRepository.save(producto);

        // Crear ProductoTalla e Inventario
        for (Long idTalla : inventarioSaveDTO.getIdTallas()) {
            ProductoTalla productoTalla = new ProductoTalla();
            productoTalla.setProducto(producto);

            Talla talla = new Talla();
            talla.setId(idTalla);
            productoTalla.setTalla(talla);

            productoTalla.setEstado("A");
            productoTalla.setPrecioCompra(0.0); // O el valor que corresponda
            productoTalla.setPrecioVenta(inventarioSaveDTO.getPrecioVenta());

            productoTalla = productoTallaRepository.save(productoTalla);

            Inventario inventario = new Inventario();
            inventario.setProductoTalla(productoTalla);
            inventario.setStockMinimo(inventarioSaveDTO.getMinimoStock());
            inventario.setStockActual(0); // O el valor que corresponda
            inventario.setFechaActualizacion(LocalDateTime.now());

            inventarioRepository.save(inventario);
        }
    }
    
    @Override
    @Transactional
    public void actualizarProductoTallaEInventario(Long id, InventarioSaveDTO inventarioSaveDTO) {
        ProductoTalla productoTalla = productoTallaRepository.findById(id).orElseThrow(() -> new RuntimeException("ProductoTalla no encontrado"));
        productoTalla.setPrecioVenta(inventarioSaveDTO.getPrecioVenta());
        productoTallaRepository.save(productoTalla);

        Inventario inventario = inventarioRepository.findByProductoTalla(productoTalla);
        inventario.setStockMinimo(inventarioSaveDTO.getMinimoStock());
        inventario.setFechaActualizacion(LocalDateTime.now());
        inventarioRepository.save(inventario);
    }
    
    @Override
    @Transactional
    public void updateStock(Long id, InventarioSaveDTO inventarioSaveDTO) {
        ProductoTalla productoTalla = productoTallaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductoTalla no encontrado"));

        Inventario inventario = inventarioRepository.findByProductoTalla(productoTalla);
        inventario.setStockActual(inventarioSaveDTO.getStockActual());
        inventarioRepository.save(inventario);
    }
    
    @Override
    @Transactional
    public void toggleProductoEnInventario(Long id, String nuevoEstado) {
        ProductoTalla productoTalla = productoTallaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        productoTalla.setEstado(nuevoEstado);
        productoTallaRepository.save(productoTalla);
    }
}
