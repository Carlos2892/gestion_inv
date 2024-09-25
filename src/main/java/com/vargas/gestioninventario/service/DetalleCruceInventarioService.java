
package com.vargas.gestioninventario.service;

import com.vargas.gestioninventario.entity.CruceInventario;
import com.vargas.gestioninventario.entity.DetalleCruceInventario;
import java.util.List;

public interface DetalleCruceInventarioService {

    // Guardar los detalles del cruce
    List<DetalleCruceInventario> guardarDetalles(List<DetalleCruceInventario> detalles);

    // Obtener detalles por cruce
    List<DetalleCruceInventario> obtenerDetallesPorCruce(CruceInventario cruceInventario);

    // Calcular diferencias entre stock físico y en sistema
    void calcularDiferencias(List<DetalleCruceInventario> detalles);
}
