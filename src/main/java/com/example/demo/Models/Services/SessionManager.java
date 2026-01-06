package com.example.demo.Models.Services;

import com.example.demo.Models.Classes.LoginResponse;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class SessionManager {

    private static final String FILE =
            System.getProperty("user.home") + "/.meuapp/session.dat";

    public static void salvar(LoginResponse res) throws IOException {
        Files.createDirectories(Path.of(FILE).getParent());

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE))) {

            oos.writeObject(res);
        }
    }

    public static LoginResponse carregar() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE))) {

            return (LoginResponse) ois.readObject();
        } catch (Exception e) {
            return null;
        }
    }

    public static void limpar() {
        try {
            Files.deleteIfExists(Path.of(FILE));
        } catch (IOException ignored) {}
    }
}
