package com.example.demo.Controllers;

import com.example.demo.Models.Classes.Despesa;
import com.example.demo.Models.Services.DespesaTemporario;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;

public class ControllerCaixa {
    @FXML
    private TextField Txt_Despesa;
    @FXML
    private TextField valor_despesa;
@FXML
private Button Btn_Add_Despesa;
@FXML
private Button Btn_Excluir_Despesa;
@FXML
private Button Btn_Editar_Despesa;
@FXML
private TableView<Despesa> Tabela_Despesa;
@FXML
private TableColumn<Despesa, String> Coluna_Nome;
@FXML
private TableColumn<Despesa, String> Coluna_Valor;
@FXML
private TableColumn<Despesa, String> Coluna_Tipo;
@FXML
private Label Lbl_valorDinheiro;
@FXML
private Label Lbl_valorCartao;
Stage stage;
@FXML
private VBox vbox_main;
@FXML

    private ObservableList<Despesa> Listadespesas = FXCollections.observableArrayList();
    @FXML
    private Label Lbl_valorPix;
    @FXML
    private DespesaTemporario despesaTemporario = new DespesaTemporario();
    
    private ControllerTelaFuncionario controllerTelaFuncionario;
    
    public void setControllerTelaFuncionario(ControllerTelaFuncionario controllerTelaFuncionario) {
        this.controllerTelaFuncionario = controllerTelaFuncionario;
    }

    public void CarregarCaixa(double valorDinheiro, double valorCartao, double valorPix){
        Lbl_valorDinheiro.setText(String.format("%.2f", valorDinheiro));
        Lbl_valorCartao.setText(String.format("%.2f", valorCartao));
        Lbl_valorPix.setText(String.format("%.2f", valorPix));
    }
    
    @FXML
    public void initialize() {
        // Initialization code here if needed
        CarregarDespesas();
    }
    void Sair(MouseEvent event){
       despesaTemporario.limpar();
        stage = (Stage) vbox_main.getScene().getWindow();
        stage.close();

     controllerTelaFuncionario.aovoltarLogin();
    }
    void CarregarDespesas() {
        Listadespesas.clear();
        Coluna_Nome.setCellValueFactory(new PropertyValueFactory<>("Nome_Despesa"));
        Coluna_Valor.setCellValueFactory(new PropertyValueFactory<>("Valor_Formatado"));
        Coluna_Tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        
        ObservableList<Despesa> despesas = despesaTemporario.carregar();
        Listadespesas.addAll(despesas);
        Tabela_Despesa.setItems(Listadespesas);

    }

}
