
package com.vargas.gestioninventario.controller;

import com.vargas.gestioninventario.dto.CruceInventarioDTO;
import com.vargas.gestioninventario.dto.CruceInventarioListDTO;
import com.vargas.gestioninventario.service.CruceInventarioService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/crucesInventario")
public class CruceInventarioController {

    @Autowired
    private CruceInventarioService cruceInventarioService;
    
    @GetMapping
    public ResponseEntity<List<CruceInventarioListDTO>> listarCruces() {
        List<CruceInventarioListDTO> cruces = cruceInventarioService.listarCruces();
        return ResponseEntity.ok(cruces);
    }
    
    @GetMapping("/{cruceId}")
    public ResponseEntity<CruceInventarioListDTO> obtenerCompraPorId(@PathVariable Long cruceId) {
        CruceInventarioListDTO cruceInventarioListDTO = cruceInventarioService.obtenerCrucePorId(cruceId);
        return ResponseEntity.ok(cruceInventarioListDTO);
    }
    
    @PostMapping("/guardarCruce")
    public ResponseEntity<Map<String, String>> guardarCruce(@RequestBody CruceInventarioDTO cruceDTO) {
        Map<String, String> response = new HashMap<>();
        try {
            cruceInventarioService.guardarCruce(cruceDTO);

            if ("DISCREPANCIA".equals(cruceDTO.getEstadoCruce())) {
                response.put("status", "warning");
                response.put("mensaje", "El cruce presenta discrepancias.");
                response.put("detalle", cruceDTO.getResultado());  // Devolver el detalle del resultado
                return ResponseEntity.ok(response);
            }

            response.put("status", "success");
            response.put("mensaje", cruceDTO.getResultado());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("mensaje", "Ocurrió un error al registrar el cruce");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @PutMapping("/editarCruce")
    public ResponseEntity<Map<String, String>> editarCruce(@RequestBody CruceInventarioDTO cruceDTO) {
        Map<String, String> response = new HashMap<>();
        try {
            cruceInventarioService.editarCruce(cruceDTO);

            if ("DISCREPANCIA".equals(cruceDTO.getEstadoCruce())) {
                response.put("status", "warning");
                response.put("mensaje", "El cruce presenta discrepancias.");
                response.put("detalle", cruceDTO.getResultado());  // Devolver el detalle del resultado
                return ResponseEntity.ok(response);
            }

            response.put("status", "success");
            response.put("mensaje", cruceDTO.getResultado());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("mensaje", "Ocurrió un error al editar el cruce");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
