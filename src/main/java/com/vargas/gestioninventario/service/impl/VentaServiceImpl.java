package com.vargas.gestioninventario.service.impl;

import com.vargas.gestioninventario.dto.DetalleVentaDTO;
import com.vargas.gestioninventario.dto.VentaSaveDTO;
import com.vargas.gestioninventario.entity.DetalleVenta;
import com.vargas.gestioninventario.entity.Inventario;
import com.vargas.gestioninventario.entity.Kardex;
import com.vargas.gestioninventario.entity.ProductoTalla;
import com.vargas.gestioninventario.entity.Venta;
import com.vargas.gestioninventario.repository.ClienteRepository;
import com.vargas.gestioninventario.repository.DetalleVentaRepository;
import com.vargas.gestioninventario.repository.FormaPagoRepository;
import com.vargas.gestioninventario.repository.InventarioRepository;
import com.vargas.gestioninventario.repository.KardexRepository;
import com.vargas.gestioninventario.repository.ProductoTallaRepository;
import com.vargas.gestioninventario.repository.VentaRepository;
import com.vargas.gestioninventario.service.VentaService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VentaServiceImpl implements VentaService {
    @Autowired
    private VentaRepository ventaRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private ProductoTallaRepository productoTallaRepository;
    
    @Autowired
    private FormaPagoRepository formaPagoRepository;
    
    @Autowired
    private DetalleVentaRepository detalleVentaRepository;
    
    @Autowired
    private InventarioRepository inventarioRepository;
    
    @Autowired
    private KardexRepository kardexRepository;

    @Override
    public List<Venta> findAll() {
        return ventaRepository.findAll();
    }

    @Override
    public Venta findById(Long id) {
        return ventaRepository.findById(id).orElse(null);
    }

    @Override
    public Venta save(Venta venta) {
        return ventaRepository.save(venta);
    }

    @Override
    public void deleteById(Long id) {
        ventaRepository.deleteById(id);
    }
    
    @Override
    public Integer obtenerUltimoCorrelativo() {
        Integer ultimoCorrelativo = ventaRepository.obtenerUltimoCorrelativo();
        return (ultimoCorrelativo != null) ? ultimoCorrelativo : 0;
    }
    
    @Override
    @Transactional
    public void registrarVenta(VentaSaveDTO ventaSaveDTO) {
        // Crear una nueva instancia de Venta
        Venta venta = new Venta();

        // Asignar los valores del DTO a la entidad Venta
        venta.setCliente(clienteRepository.findById(ventaSaveDTO.getClienteId())
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado")));
        venta.setFormaPago(formaPagoRepository.findById(ventaSaveDTO.getFormaPagoId())
            .orElseThrow(() -> new RuntimeException("Forma de pago no encontrado")));
        venta.setFecha(ventaSaveDTO.getFecha());
        venta.setCorrelativo(ventaSaveDTO.getCorrelativo());
        venta.setSubtotal(ventaSaveDTO.getSubtotal());
        venta.setDescuento(ventaSaveDTO.getDescuento());
        venta.setImporteTotal(ventaSaveDTO.getImporteTotal());
        venta.setEstado("REGISTRADO");

        // Guardar la venta en la base de datos
        ventaRepository.save(venta);

        // Guardar los detalles de la venta
        for (DetalleVentaDTO detalleVentaDTO : ventaSaveDTO.getDetalleVentas()) {
            DetalleVenta detalleVenta = new DetalleVenta();
            ProductoTalla productoTalla = productoTallaRepository.findById(detalleVentaDTO.getProductoTallaId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            detalleVenta.setProductoTalla(productoTalla);
            detalleVenta.setCantidad(detalleVentaDTO.getCantidad());
            detalleVenta.setPrecioCompra(detalleVentaDTO.getPrecioCompra());
            detalleVenta.setPrecioVenta(detalleVentaDTO.getPrecioUnitario());
            detalleVenta.setDescuentoAplicado(detalleVentaDTO.getDescuento());
            detalleVenta.setSubtotal(detalleVentaDTO.getSubtotal());
            detalleVenta.setImporteTotal(detalleVentaDTO.getImporteTotal());
            detalleVenta.setVenta(venta);
            // Guardar cada detalle de la venta en la base de datos
            detalleVentaRepository.save(detalleVenta);
            
            // Obtener el inventario correspondiente
            Inventario inventario = inventarioRepository.findByProductoTalla(productoTalla);
            
            // Actualizar stock actual en el inventario
            int nuevoStock = inventario.getStockActual() - detalleVenta.getCantidad();
            inventario.setStockActual(nuevoStock);
            inventario.setFechaActualizacion(LocalDateTime.now());
            inventarioRepository.save(inventario);
            
            // Registrar el movimiento en Kardex
            Kardex kardex = new Kardex();
            kardex.setProductoTalla(productoTalla);
            kardex.setFechaMovimiento(venta.getFecha());
            kardex.setConcepto("VENTA");
            kardex.setCantidad(detalleVenta.getCantidad());
            kardex.setPrecioUnitario(detalleVenta.getPrecioVenta());
            kardex.setImporteTotal(detalleVenta.getImporteTotal());
            kardex.setStockAnterior(inventario.getStockActual() + detalleVenta.getCantidad());
            kardex.setStockActual(nuevoStock);
            kardex.setReferencia("Venta ID: " + venta.getId());
            kardexRepository.save(kardex);
        }
    }
}
