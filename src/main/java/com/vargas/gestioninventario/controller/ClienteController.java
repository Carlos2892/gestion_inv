package com.vargas.gestioninventario.controller;

import com.vargas.gestioninventario.dto.ClienteDTO;
import com.vargas.gestioninventario.entity.Cliente;
import com.vargas.gestioninventario.service.ClienteService;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;
   

    @GetMapping
    public List<Cliente> getAllClientes() {
        return clienteService.findAll();
    }

    @GetMapping("/{id}")
    public Cliente getClienteById(@PathVariable Long id) {
        return clienteService.findById(id);
    }

    @PostMapping("/crear")
    public ResponseEntity<Map<String, String>> crearCliente(@RequestBody ClienteDTO clienteDTO) {
        Map<String, String> response = new HashMap<>();
        try {
            Cliente nuevoCliente = clienteService.save(clienteDTO); // El servicio ahora se encarga de la conversión

            response.put("status", "success");
            response.put("id", nuevoCliente.getId().toString());
            response.put("mensaje", "Cliente registrado con éxito");
            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            response.put("status", "error");
            response.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("mensaje", "Ocurrió un error al registrar el cliente");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    //@PutMapping("/{id}")
    //public Cliente updateCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
    //    Cliente existingCliente = clienteService.findById(id);
    //    if (existingCliente != null) {
    //        cliente.setId(id);
    //        return clienteService.save(cliente);
    //    }
    //    return null;
    //}

    @DeleteMapping("/{id}")
    public void deleteCliente(@PathVariable Long id) {
        clienteService.deleteById(id);
    }
    
    @GetMapping("/buscar")
    public ResponseEntity<Cliente> buscarCliente(
            @RequestParam Long tipoDocumentoId,
            @RequestParam String numeroDocumento) {

        return clienteService.buscarPorTipoYNumeroDocumento(tipoDocumentoId, numeroDocumento)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
