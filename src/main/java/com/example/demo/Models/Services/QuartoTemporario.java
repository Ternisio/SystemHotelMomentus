package com.example.demo.Models.Services;

import com.example.demo.Models.Classes.Quarto;
import com.example.demo.Util.MensagemAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class QuartoTemporario {
MensagemAlert alertM = new MensagemAlert();
    private static final String FILE =
            System.getProperty("user.home") + "/.meuapp/QuartoTemporario.dat";

    public  boolean salvar(ObservableList<Quarto> quartos) throws IOException {

        if(!Files.exists(Path.of(FILE))) {
            Files.createDirectories(Path.of(FILE).getParent());
        }
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(Files.newOutputStream(Path.of(FILE), StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING))) {

            for (Quarto quarto : quartos){
                oos.writeObject(quarto);
            }
            return true;
        }catch (Exception e){
            alertM.MensagemError(e.getMessage());
            return false;
        }
    }

    public static ObservableList<Quarto> carregar() {
        ObservableList<Quarto> quartos = FXCollections.observableArrayList();
        if (!Files.exists(Path.of(FILE))) {
            return quartos ;
        }
        try (ObjectInputStream ois =
                     new ObjectInputStream(Files.newInputStream(Path.of(FILE)))) {
            while (true) {
                try {

                quartos.add((Quarto) ois.readObject());
                } catch (EOFException e) {
                    break;
                }
            }} catch (Exception e) {
            MensagemAlert alert = new MensagemAlert();
            alert.MensagemError(e.getMessage());
        }
        return quartos;
    }

    public static void limpar() {
        try {
            Files.deleteIfExists(Path.of(FILE));
        } catch (IOException ignored) {}
    }
}
