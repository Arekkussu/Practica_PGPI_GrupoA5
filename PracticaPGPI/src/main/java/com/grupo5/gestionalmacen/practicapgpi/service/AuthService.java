package com.grupo5.gestionalmacen.practicapgpi.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.grupo5.gestionalmacen.practicapgpi.model.User;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    private final String jsonFilePath = "./User.json"; // Asegúrate de que la ruta sea accesible por la aplicación

    public boolean registerUser(User user) {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(jsonFilePath);

        try {
            // Lista para almacenar usuarios
            List<User> users;

            // Leer el archivo existente si existe, si no, crea una lista vacía
            if (file.exists()) {
                users = mapper.readValue(file, new TypeReference<List<User>>(){});
            } else {
                users = new ArrayList<>();
            }

            // Comprobar si el usuario ya existe basado en el correo electrónico
            if (users.stream().anyMatch(u -> u.getCorreo().equalsIgnoreCase(user.getCorreo()))) {
                // Si el usuario ya existe, no se registra y se devuelve false
                return false;
            }

            // Agregar el nuevo usuario a la lista
            users.add(user);

            // Escribir de nuevo en el archivo JSON la lista actualizada de usuarios
            mapper.writeValue(file, users);
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
            return false;
        }

        return true;
    }

    public boolean authenticateUser(String correo, String contrasena) {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(jsonFilePath);
        try {
            // Leer la lista de usuarios del archivo JSON
            List<User> users = mapper.readValue(file, new TypeReference<List<User>>(){});

            // Comprobar si existe un usuario con el correo y contraseña proporcionados
            Optional<User> user = users.stream()
                    .filter(u -> u.getCorreo().equalsIgnoreCase(correo) && u.getContrasena().equals(contrasena))
                    .findFirst();

            // Si el usuario es encontrado y la contraseña coincide, devuelve true
            return user.isPresent();
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            return false;
        }
    }
}