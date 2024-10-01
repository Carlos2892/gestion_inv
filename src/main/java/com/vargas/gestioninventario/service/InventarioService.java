package com.vargas.gestioninventario.service;

import com.vargas.gestioninventario.dto.InventarioSaveDTO;
import com.vargas.gestioninventario.dto.ProductoInventarioDTO;
import com.vargas.gestioninventario.entity.Inventario;
import java.util.List;

public interface InventarioService {
    List<Inventario> findAll();
    Inventario findById(Long id);
    Inventario save(Inventario inventario);
    void deleteById(Long id);
    List<ProductoInventarioDTO> listarInventario();
    List<ProductoInventarioDTO> listarInventarioConStock();
    void crearProductoConTallasEInventarios(InventarioSaveDTO inventarioSaveDTO);
    void actualizarProductoTallaEInventario(Long id, InventarioSaveDTO inventarioSaveDTO);
    void updateStock(Long id, InventarioSaveDTO inventarioSaveDTO);
    void toggleProductoEnInventario(Long id, String nuevoEstado);
    long contarProductosConPocoStock();
    List<ProductoInventarioDTO> obtenerProductosPocoStock();
    Double obtenerValorTotalInventario();
}
