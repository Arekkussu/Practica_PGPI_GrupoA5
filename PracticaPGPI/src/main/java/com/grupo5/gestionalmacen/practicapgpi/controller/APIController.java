package com.grupo5.gestionalmacen.practicapgpi.controller;

import com.grupo5.gestionalmacen.practicapgpi.model.Producto;
import com.grupo5.gestionalmacen.practicapgpi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api")
public class APIController {

    @Autowired
    private ProductService productService;


    @PostMapping("/productos")
    public ResponseEntity<?> registrarProducto(@RequestBody Producto producto) {
        boolean success = productService.saveOrUpdateProduct(producto);
        if (success) {
            return ResponseEntity.ok(new HashMap<String, Object>() {{
                put("success", true);
                put("message", "Producto registrado con éxito");
            }});
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new HashMap<String, Object>() {{
                put("success", false);
                put("message", "Error al guardar el producto");
            }});
        }
    }

    @PostMapping("/updateproductos")
    public ResponseEntity<?> reduceStock(@RequestParam String productName, @RequestParam int quantity) {
        boolean success = productService.reduceProductStock(productName, quantity);
        if (success) {
            return ResponseEntity.ok("Stock reducido correctamente para el producto: " + productName);
        } else {
            return ResponseEntity.badRequest().body("No se encontró el producto o error al actualizar el stock");
        }
    }

    @GetMapping("/productos")
    public ResponseEntity<List<Producto>> getAllProducts() {
        try {
            List<Producto> productos = productService.getAllProducts();
            return ResponseEntity.ok(productos); // Devuelve la lista de productos con un código de estado 200
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build(); // Devuelve un código de estado 500 en caso de error
        }
    }


}
