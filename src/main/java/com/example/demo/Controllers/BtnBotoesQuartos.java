package com.example.demo.Controllers;

import com.example.demo.Models.Classes.Quarto;
import com.example.demo.Models.Interfaces.MeuClick;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class BtnBotoesQuartos {

    @FXML
    private ImageView ImageView_btn_quartos;

    @FXML
    private Label Lbl_status;

    @FXML
    private Label Num_quartos;
    @FXML
    private void selecionadoquarto(MouseEvent event){
        try {
            meuClick.onClickQuarto(quarto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private MeuClick meuClick;
    private Quarto quarto;
    public void Botoes(Quarto quarto, MeuClick meuClick){
        this.quarto = quarto;
        this.meuClick = meuClick;
        Lbl_status.setText(quarto.getEstado());
        Num_quartos.setText(quarto.getNum_quarto());
        Image imagem = new Image(getClass().getResourceAsStream("/Imagens/"+quarto.getEstado()+".png"));
        ImageView_btn_quartos.setImage(imagem);

    }
}
