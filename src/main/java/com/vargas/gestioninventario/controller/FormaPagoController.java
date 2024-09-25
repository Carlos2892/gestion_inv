package com.vargas.gestioninventario.controller;

import com.vargas.gestioninventario.entity.FormaPago;
import com.vargas.gestioninventario.service.FormaPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/formaspago")
public class FormaPagoController {
    @Autowired
    private FormaPagoService formaPagoService;

    @GetMapping
    public List<FormaPago> getAllFormasPago() {
        return formaPagoService.findAll();
    }

    @GetMapping("/{id}")
    public FormaPago getFormaPagoById(@PathVariable Long id) {
        return formaPagoService.findById(id);
    }

    @PostMapping
    public FormaPago createFormaPago(@RequestBody FormaPago formaPago) {
        return formaPagoService.save(formaPago);
    }

    @PutMapping("/{id}")
    public FormaPago updateFormaPago(@PathVariable Long id, @RequestBody FormaPago formaPago) {
        FormaPago existingFormaPago = formaPagoService.findById(id);
        if (existingFormaPago != null) {
            formaPago.setId(id);
            return formaPagoService.save(formaPago);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteFormaPago(@PathVariable Long id) {
        formaPagoService.deleteById(id);
    }
}
