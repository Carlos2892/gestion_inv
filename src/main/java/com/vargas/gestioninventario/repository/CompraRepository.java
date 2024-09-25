
package com.vargas.gestioninventario.repository;

import com.vargas.gestioninventario.entity.Compra;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {
    @Query("SELECT c FROM Compra c " +
           "JOIN FETCH c.proveedor pr " +
           "JOIN FETCH c.tipoComprobante tc " +
           "LEFT JOIN FETCH c.detalleCompras dc " +
           "LEFT JOIN FETCH dc.productoTalla pt " +
           "LEFT JOIN FETCH pt.producto p " +
           "LEFT JOIN FETCH pt.talla t")
    List<Compra> findAllComprasConDetalles();
    
    // Verificar si existe una compra con la misma combinación de tipoComprobante, serie, correlativo y proveedor
    boolean existsByTipoComprobante_IdAndSerieAndCorrelativoAndProveedor_Id(Long tipoComprobanteId, String serie, String correlativo, Long proveedorId);

    // Verificar si existe una compra con la misma combinación de tipoComprobante, serie, correlativo, proveedor y un ID diferente (usado para editar)
    boolean existsByTipoComprobante_IdAndSerieAndCorrelativoAndProveedor_IdAndIdNot(Long tipoComprobanteId, String serie, String correlativo, Long proveedorId, Long id);
}
