package com.grupo5.gestionalmacen.practicapgpi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class ViewController {

    @GetMapping("/")
    public String index() {
        return "login"; // Nombre del archivo HTML en la carpeta 'templates'
    }

    @GetMapping("/registro")
    public String registro() {
        return "registro"; // Nombre del archivo HTML en la carpeta 'templates'
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Nombre del archivo HTML en la carpeta 'templates'
    }

    @GetMapping("/inicio")
    public String inicio() {
        return "inicio"; // Nombre del archivo HTML en la carpeta 'templates'
    }

    @GetMapping("/producto")
    public String producto() {
        return "producto"; // Nombre del archivo HTML en la carpeta 'templates'
    }

    @GetMapping("/poblacion")
    public String poblacion() {
        return "poblacion"; // Nombre del archivo HTML en la carpeta 'templates'
    }

}
