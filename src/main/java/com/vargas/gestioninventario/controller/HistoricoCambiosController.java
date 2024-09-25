
package com.vargas.gestioninventario.controller;

import com.vargas.gestioninventario.dto.HistoricoCambiosDTO;
import com.vargas.gestioninventario.service.HistoricoCambiosService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/historicoCambios")
public class HistoricoCambiosController {

    @Autowired
    private HistoricoCambiosService historicoCambiosService;

    @GetMapping("/{cruceId}")
    public ResponseEntity<List<HistoricoCambiosDTO>> obtenerHistoricoPorCruce(@PathVariable Long cruceId) {
        List<HistoricoCambiosDTO> historial = historicoCambiosService.obtenerHistoricoPorCruce(cruceId);

        if (historial.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(historial);
    }
}
