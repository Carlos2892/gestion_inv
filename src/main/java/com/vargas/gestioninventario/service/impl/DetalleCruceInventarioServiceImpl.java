
package com.vargas.gestioninventario.service.impl;

import com.vargas.gestioninventario.entity.CruceInventario;
import com.vargas.gestioninventario.entity.DetalleCruceInventario;
import com.vargas.gestioninventario.entity.Inventario;
import com.vargas.gestioninventario.repository.DetalleCruceInventarioRepository;
import com.vargas.gestioninventario.repository.InventarioRepository;
import com.vargas.gestioninventario.service.DetalleCruceInventarioService;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalleCruceInventarioServiceImpl implements DetalleCruceInventarioService {

    @Autowired
    private DetalleCruceInventarioRepository detalleCruceInventarioRepository;

    @Autowired
    private InventarioRepository inventarioRepository;

    @Override
    public List<DetalleCruceInventario> guardarDetalles(List<DetalleCruceInventario> detalles) {
        return detalleCruceInventarioRepository.saveAll(detalles);
    }

    @Override
    public List<DetalleCruceInventario> obtenerDetallesPorCruce(CruceInventario cruceInventario) {
        return detalleCruceInventarioRepository.findByCruceInventario(cruceInventario);
    }

    @Override
    @Transactional
    public void calcularDiferencias(List<DetalleCruceInventario> detalles) {
        for (DetalleCruceInventario detalle : detalles) {
            Inventario inventario = detalle.getInventario();
            int stockSistema = inventario.getStockActual();
            int stockFisico = detalle.getStockFisico();
            detalle.setDiferencia(stockFisico - stockSistema);
        }
        detalleCruceInventarioRepository.saveAll(detalles);
    }
}