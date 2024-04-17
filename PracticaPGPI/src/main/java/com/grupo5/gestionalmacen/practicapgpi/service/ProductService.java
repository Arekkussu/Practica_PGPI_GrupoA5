package com.grupo5.gestionalmacen.practicapgpi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo5.gestionalmacen.practicapgpi.model.Producto;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private static final String FILE_PATH = "./Productos.json"; // Ubicación del archivo JSON
    private ObjectMapper mapper = new ObjectMapper();

    public List<Producto> loadProducts() throws IOException {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            // Lee los productos existentes desde el archivo JSON
            return mapper.readValue(file, new TypeReference<List<Producto>>(){});
        } else {
            return new ArrayList<>(); // Retorna una lista vacía si el archivo no existe
        }
    }

    public boolean saveOrUpdateProduct(Producto newProduct) {
        try {
            List<Producto> productos = loadProducts();
            Optional<Producto> existingProduct = productos.stream()
                    .filter(p -> p.getProductName().equals(newProduct.getProductName()))
                    .findFirst();

            if (existingProduct.isPresent()) {
                // Producto existe, actualiza los datos
                Producto product = existingProduct.get();
                product.setCampaignName(newProduct.getCampaignName()); // Actualiza el nombre de la campaña
                product.setQuantityInStock(product.getQuantityInStock() + newProduct.getQuantityInStock()); // Suma la nueva cantidad al stock existente

                // Solo actualiza el tiempo de entrega si el nuevo tiempo es mayor
                if (newProduct.getDeliveryTime() > product.getDeliveryTime()) {
                    product.setDeliveryTime(newProduct.getDeliveryTime());
                }
            } else {
                // Producto nuevo, asigna un nuevo ID
                long newId = productos.stream()
                        .mapToLong(Producto::getId)
                        .max()
                        .orElse(0) + 1;
                newProduct.setId(newId);
                productos.add(newProduct);
            }

            mapper.writeValue(new File(FILE_PATH), productos);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean reduceProductStock(String productName, int quantityToReduce) {
        try {
            List<Producto> productos = loadProducts();
            Optional<Producto> productOptional = productos.stream()
                    .filter(p -> p.getProductName().equals(productName))
                    .findFirst();

            if (productOptional.isPresent()) {
                Producto product = productOptional.get();
                // Asegura de no reducir el stock a menos de cero
                int newStock = Math.max(0, product.getQuantityInStock() - quantityToReduce);
                product.setQuantityInStock(newStock);

                mapper.writeValue(new File(FILE_PATH), productos);
                return true;
            }
            return false; // Retorna falso si no se encuentra el producto
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Producto> getAllProducts() throws IOException {
        return loadProducts(); // Reutiliza el método existente que carga productos desde el archivo JSON
    }



}
