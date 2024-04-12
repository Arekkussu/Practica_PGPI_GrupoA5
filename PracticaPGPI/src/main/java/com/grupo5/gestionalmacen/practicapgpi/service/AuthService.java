package com.grupo5.gestionalmacen.practicapgpi.service;

import com.grupo5.gestionalmacen.practicapgpi.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AuthService {
    private static final String JSON_FILE_PATH = "users.json";
    private ObjectMapper mapper = new ObjectMapper();

    //TODO: Hacer que cuando se registre un usuario, se guarde en el archivo users.json de resources no de la carpeta target

    public boolean registerUser(User user) {
        try {
            File resource = new ClassPathResource(JSON_FILE_PATH).getFile();
            List<User> users = new ArrayList<>(Arrays.asList(mapper.readValue(resource, User[].class)));
            users.add(user);
            mapper.writeValue(resource, users);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean authenticateUser(String email, String password) {
        try {
            File resource = new ClassPathResource(JSON_FILE_PATH).getFile();
            List<User> users = new ArrayList<>(Arrays.asList(mapper.readValue(resource, User[].class)));

            return users.stream().anyMatch(user ->
                    user.getCorreo().equalsIgnoreCase(email) && user.getContrasena().equals(password));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
