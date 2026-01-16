package com.example.demo.Models.Services;

import com.example.demo.Models.Classes.Config;
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

public class ConfigTemporario {
MensagemAlert alertM = new MensagemAlert();
    private static final String FILE =
            System.getProperty("user.home") + "/.meuapp/ConsumoTemporario.dat";

    public  boolean salvar(Config configs) throws IOException {

        if(!Files.exists(Path.of(FILE))) {
            Files.createDirectories(Path.of(FILE).getParent());
        }
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(Files.newOutputStream(Path.of(FILE), StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING))) {

                oos.writeObject(configs);

            return true;
        }catch (Exception e){
            alertM.MensagemError(e.getMessage());
            return false;
        }
    }

    public Config carregar() {
        Config CONFIG = null;
        if (!Files.exists(Path.of(FILE))) {
            return CONFIG ;
        }
        try (ObjectInputStream ois =
                     new ObjectInputStream(Files.newInputStream(Path.of(FILE)))) {
            CONFIG = (Config) ois.readObject();

            } catch (Exception e) {
            MensagemAlert alert = new MensagemAlert();
            alert.MensagemError(e.getMessage());
        }
        return CONFIG;
    }

    public static void limpar() {
        try {
            Files.deleteIfExists(Path.of(FILE));
        } catch (IOException ignored) {}
    }
}
