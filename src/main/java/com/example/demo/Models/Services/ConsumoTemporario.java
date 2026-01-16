package com.example.demo.Models.Services;

import com.example.demo.Models.Classes.Consumo;

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

public class ConsumoTemporario {
MensagemAlert alertM = new MensagemAlert();
    private static final String FILE =
            System.getProperty("user.home") + "/.meuapp/ConsumoTemporario.dat";

    public  boolean salvar(ObservableList<Consumo> consumos) throws IOException {

        if(!Files.exists(Path.of(FILE))) {
            Files.createDirectories(Path.of(FILE).getParent());
        }
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(Files.newOutputStream(Path.of(FILE), StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING))) {

            for (Consumo consumo : consumos){
                oos.writeObject(consumo);
            }
            return true;
        }catch (Exception e){
            alertM.MensagemError(e.getMessage());
            return false;
        }
    }

    public static ObservableList<Consumo> carregar() {
        ObservableList<Consumo> consumos = FXCollections.observableArrayList();
        if (!Files.exists(Path.of(FILE))) {
            return consumos ;
        }
        try (ObjectInputStream ois =
                     new ObjectInputStream(Files.newInputStream(Path.of(FILE)))) {
            while (true) {
                try {

                consumos.add((Consumo) ois.readObject());
                } catch (EOFException e) {
                    break;
                }
            }} catch (Exception e) {
            MensagemAlert alert = new MensagemAlert();
            alert.MensagemError(e.getMessage());
        }
        return consumos  ;
    }

    public static void limpar() {
        try {
            Files.deleteIfExists(Path.of(FILE));
        } catch (IOException ignored) {}
    }
}
