package com.example.demo.Models.Services;

import com.example.demo.Models.Classes.Despesa;
import com.example.demo.Models.Classes.LoginResponse;
import com.example.demo.Util.MensagemAlert;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class DespesaTemporario {
MensagemAlert alertM = new MensagemAlert();
    private static final String FILE =
            System.getProperty("user.home") + "/.meuapp/DespesaTemporario.dat";

    public  boolean salvar(ObservableList<Despesa> despesas) throws IOException {

        if(!Files.exists(Path.of(FILE))) {
            Files.createDirectories(Path.of(FILE).getParent());
        }
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(Files.newOutputStream(Path.of(FILE), StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING))) {

            for (Despesa despesa : despesas){
                oos.writeObject(despesa);
            }
            return true;
        }catch (Exception e){
            alertM.MensagemError(e.getMessage());
            return false;
        }
    }

    public static ObservableList<Despesa> carregar() {
        ObservableList<Despesa> despesas = FXCollections.observableArrayList();
        if (!Files.exists(Path.of(FILE))) {
            return despesas ;
        }
        try (ObjectInputStream ois =
                     new ObjectInputStream(Files.newInputStream(Path.of(FILE)))) {
            while (true) {
                try {

                despesas.add((Despesa) ois.readObject());
                } catch (EOFException e) {
                    break;
                }
            }} catch (Exception e) {
            MensagemAlert alert = new MensagemAlert();
            alert.MensagemError(e.getMessage());
        }
        return despesas;
    }

    public static void limpar() {
        try {
            Files.deleteIfExists(Path.of(FILE));
        } catch (IOException ignored) {}
    }
}
