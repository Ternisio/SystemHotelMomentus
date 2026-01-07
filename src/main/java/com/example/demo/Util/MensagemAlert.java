package com.example.demo.Util;

import javafx.scene.control.Alert;

public class MensagemAlert {
    public void MensagemSucess(String Mensagem){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText("Mensagem:");
        alert.setContentText(Mensagem);
        alert.showAndWait();
    }
    public void MensagemError(String Mensagem){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Sucesso");
        alert.setHeaderText("Mensagem:");
        alert.setContentText(Mensagem);
        alert.showAndWait();
    }
}
