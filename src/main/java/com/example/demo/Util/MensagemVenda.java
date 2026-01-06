package com.example.demo.Util;

import javafx.scene.control.Alert;

public class MensagemVenda {
    public void Mensagem(String Tipo){
        if (Tipo.equals( "Realizando")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Mensagem:");
            alert.setContentText("Foi iniciado o quarto com sucesso ");
            alert.showAndWait();
        }else if(Tipo.equals("Finalizado")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Mensagem:");
            alert.setContentText("Foi finalizado o quarto com sucesso ");
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Mensagem:");
            alert.setContentText("Foi finalizado o quarto com sucesso ");
            alert.showAndWait();
        }
    }

}
