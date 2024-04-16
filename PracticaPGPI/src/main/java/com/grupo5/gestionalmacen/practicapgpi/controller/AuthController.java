package com.grupo5.gestionalmacen.practicapgpi.controller;

import com.grupo5.gestionalmacen.practicapgpi.model.User;
import com.grupo5.gestionalmacen.practicapgpi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService; // Inyecta la clase del Servicio.
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);


    @PostMapping("/registro")
    public ResponseEntity<?> registro(@RequestBody User user) {
        boolean result = authService.registerUser(user);
        if(result) {
            return ResponseEntity.ok().body("{\"message\": \"Usuario registrado con éxito\"}");
        } else {
            return ResponseEntity.badRequest().body("Error al registrar usuario");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginDetails) {
        boolean isAuthenticated = authService.authenticateUser(loginDetails.getCorreo(), loginDetails.getContrasena());
        if(isAuthenticated) {
            return ResponseEntity.ok().body("{\"message\": \"Usuario autenticado con éxito\"}");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"error\": \"Credenciales incorrectas\"}");
        }
    }


}
