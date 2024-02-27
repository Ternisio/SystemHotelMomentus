package com.example.demo.Models.Services;

import com.example.demo.Models.Classes.Quarto;
import com.example.demo.Models.Dao.QuartoDao;
import com.example.demo.Models.Interfaces.AdminQuartoInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AdminQuartoService implements AdminQuartoInterface {
    public boolean VerificarCampos(String Num, String Estado) {
        if (Num.equals("") || Estado.equals("Selecione o estado")) {
            return true;
        } else {

            return false;
        }


    }

    public boolean VerificarNumero(String Num, QuartoDao quartoDao) {
        boolean verificar = false;
        ObservableList<Quarto> NumQ = FXCollections.observableArrayList();
        NumQ = quartoDao.ListaQuarto();
        for (Quarto quarto : NumQ) {
            if (Num.equals(quarto.getNum_quarto())) {
                verificar = true;
            }
        }
        if (verificar) {
            return true;
        }
        else{
            return false;
        }


    }

    public void Excluir_e_Editar(boolean Excluir, boolean Editar, Quarto quarto, QuartoDao quartoDao){
        if(Editar == true && Excluir == false){
            if (quarto.getNum_quarto().equals("") || quarto.getEstado().equals("Selecione o estado")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");

                alert.setHeaderText("Erro:");
                alert.setContentText("Preencham os campos");
            }else {
                quartoDao.Editar(quarto);
            }

        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mensagem");

            alert.setHeaderText("Pergunta:");
            alert.setContentText("Tem certeza que você quer excluir o quarto "+quarto.getNum_quarto()+" ?\n"+
                    "Para confirmar Sim, clique OK\n" +
                    "Para confirmar Não, clique CANCEL");
            Optional<ButtonType> confirma = alert.showAndWait();

            if(confirma.get() == ButtonType.OK){
                quartoDao.Excluir(quarto);
            }

        }
    }
    public int cod_quarto(QuartoDao quartoDao){
        ObservableList<Quarto> Cod_quarto = FXCollections.observableArrayList();
        Cod_quarto = quartoDao.ListaQuarto();
        int cod = 1;

            for (Quarto quarto : Cod_quarto){
                if (cod == quarto.getCod_Quarto()){
                    cod++;
                }

            }

            return cod;


    }
    public void Excluir_Tudo(QuartoDao quartoDao){

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mensagem");

            alert.setHeaderText("Pergunta:");
            alert.setContentText("Tem certeza que você quer excluir tudo na tabela do quarto ?\n" +
                    "Para confirmar Sim, clique OK\n" +
                    "Para confirmar Não, clique CANCEL");
            Optional<ButtonType> confirma = alert.showAndWait();

            if(confirma.get() == ButtonType.OK){
                quartoDao.Excluir_tudo();
            }
        }
    }
