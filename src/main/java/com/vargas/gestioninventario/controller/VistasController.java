
package com.vargas.gestioninventario.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping ("/vistas")
public class VistasController {
    
    @GetMapping
    public String home(){
        return "demo";
    }
    
    @GetMapping ("/kardex")
    public String mostrarKardex(HttpServletRequest request){
        String referer = request.getHeader("Referer");
        if (referer == null || !referer.contains("/vistas")) {
            // Si no viene de la página principal, redirige al home o muestra un error
            return "redirect:/vistas"; // O una vista de error personalizada
        }
        return "vistas/kardex"; 
    }
    
    
    @GetMapping("/dashboard")
    public String mostrarDashboard(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        if (referer == null || !referer.contains("/vistas")) {
            // Si no viene de la página principal, redirige al home o muestra un error
            return "redirect:/vistas"; // O una vista de error personalizada
        }
        return "vistas/dashboard"; 
    }
    
    @GetMapping("/venta_boleta")
    public String mostrarVentaBoleta(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        if (referer == null || !referer.contains("/vistas")) {
            // Si no viene de la página principal, redirige al home o muestra un error
            return "redirect:/vistas"; // O una vista de error personalizada
        }
        return "vistas/venta_boleta"; // Esto devuelve el nombre de la plantilla Thymeleaf
    }
    
    @GetMapping("/venta_resumen_boletas")
    public String mostrarVentaResumenBoletas(HttpServletRequest request){
        String referer = request.getHeader("Referer");
        if (referer == null || !referer.contains("/vistas")) {
            // Si no viene de la página principal, redirige al home o muestra un error
            return "redirect:/vistas"; // O una vista de error personalizada
        }
        return "vistas/venta_resumen_boletas"; // Esto devuelve el nombre de la plantilla Thymeleaf
    }
    
    @GetMapping("/productos")
    public String mostrarProductos(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        if (referer == null || !referer.contains("/vistas")) {
            // Si no viene de la página principal, redirige al home o muestra un error
            return "redirect:/vistas"; // O una vista de error personalizada
        }
        return "vistas/productos"; // Esto devuelve el nombre de la plantilla Thymeleaf
    }
    
    @GetMapping("/cruce_inventario")
    public String mostrarCruceInventario(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        if (referer == null || !referer.contains("/vistas")) {
            // Si no viene de la página principal, redirige al home o muestra un error
            return "redirect:/vistas"; // O una vista de error personalizada
        }
        return "vistas/cruce_inventario"; // Esto devuelve el nombre de la plantilla Thymeleaf
    }
    
    @GetMapping("/categorias")
    public String mostrarCategorias(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        if (referer == null || !referer.contains("/vistas")) {
            // Si no viene de la página principal, redirige al home o muestra un error
            return "redirect:/vistas"; // O una vista de error personalizada
        }
        return "vistas/categorias"; // Esto devuelve el nombre de la plantilla Thymeleaf
    }
    
    @GetMapping("/compras")
    public String mostrarCompras(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        if (referer == null || !referer.contains("/vistas")) {
            // Si no viene de la página principal, redirige al home o muestra un error
            return "redirect:/vistas"; // O una vista de error personalizada
        }
        return "vistas/compras"; // Esto devuelve el nombre de la plantilla Thymeleaf
    }
    
    @GetMapping("/reportes")
    public String mostrarReportes(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        if (referer == null || !referer.contains("/vistas")) {
            // Si no viene de la página principal, redirige al home o muestra un error
            return "redirect:/vistas"; // O una vista de error personalizada
        }
        return "vistas/reportes"; // Esto devuelve el nombre de la plantilla Thymeleaf
    }
    
    @GetMapping("/administrar_clientes")
    public String mostrarAdministrar_clientes(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        if (referer == null || !referer.contains("/vistas")) {
            // Si no viene de la página principal, redirige al home o muestra un error
            return "redirect:/vistas"; // O una vista de error personalizada
        }
        return "vistas/administrar_clientes"; // Esto devuelve el nombre de la plantilla Thymeleaf
    }
    
    @GetMapping("/administrar_proveedores")
    public String mostrarAdministrar_proveedores(HttpServletRequest request){
        String referer = request.getHeader("Referer");
        if (referer == null || !referer.contains("/vistas")) {
            // Si no viene de la página principal, redirige al home o muestra un error
            return "redirect:/vistas"; // O una vista de error personalizada
        }
        return "vistas/administrar_proveedores"; // Esto devuelve el nombre de la plantilla Thymeleaf
    }
    
    @GetMapping("/seguridad_perfiles")
    public String mostrarSeguridad_perfiles(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        if (referer == null || !referer.contains("/vistas")) {
            // Si no viene de la página principal, redirige al home o muestra un error
            return "redirect:/vistas"; // O una vista de error personalizada
        }
        return "vistas/seguridad_perfiles"; // Esto devuelve el nombre de la plantilla Thymeleaf
    }
    
    @GetMapping("/seguridad_usuarios")
    public String mostrarSeguridad_usuarios(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        if (referer == null || !referer.contains("/vistas")) {
            // Si no viene de la página principal, redirige al home o muestra un error
            return "redirect:/vistas"; // O una vista de error personalizada
        }
        return "vistas/seguridad_usuarios"; // Esto devuelve el nombre de la plantilla Thymeleaf
    }
    
}
