package com.vargas.gestioninventario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/403")
    public String accessDenied() {
        return "403"; // Retorna el nombre del archivo HTML (403.html)
    }
}