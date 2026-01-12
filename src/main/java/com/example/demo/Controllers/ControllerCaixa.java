package com.example.demo.Controllers;

import com.example.demo.Models.Classes.Despesa;
import com.example.demo.Models.Services.DespesaTemporario;
import com.example.demo.Util.MaskFieldUtil;
import com.example.demo.Util.MensagemAlert;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

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
@FXML
private ComboBox Combobox_Tipo;
Stage stage;
@FXML
private VBox vbox_main;
@FXML
    MensagemAlert alert = new MensagemAlert();
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
        Tabela_Despesa.setItems(Listadespesas);
        CarregarDespesas();
        MaskFieldUtil.monetaryField(valor_despesa);
        Combobox_Tipo.setItems(FXCollections.observableArrayList("Selecione","Conta a pagar","Manutenção","Produtos"));
        Combobox_Tipo.setValue("Selecione");

    }

    @FXML
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
        Coluna_Tipo.setCellValueFactory(new PropertyValueFactory<>("Tipo_Despesa"));

        Listadespesas.addAll( despesaTemporario.carregar());


    }
    Despesa Sel;
    @FXML
    public void Selecionou_Tabela(){
        Sel = Tabela_Despesa.getSelectionModel().getSelectedItem();
        if(Sel == null) return;
        Txt_Despesa.setText(Sel.getNome_Despesa());
        valor_despesa.setText(Sel.getValor_Formatado());
        Combobox_Tipo.setValue(Sel.getTipo_Despesa());
    }
    void Limpar_Campos(){
        Txt_Despesa.setText("");
        valor_despesa.setText("");
        Combobox_Tipo.setValue("Selecione");
        Sel = null;
    }

    @FXML
    public void Excluir() throws IOException {
        if(Sel == null){
            alert.MensagemError("Selecione uma despesa na tabela");
            return;
        }
        Listadespesas.remove(Sel);
        if(despesaTemporario.salvar(Listadespesas)){

            alert.MensagemSucess("Foi excluido com sucesso");
            Limpar_Campos();
            Tabela_Despesa.getSelectionModel().clearSelection();

        }

    }

    @FXML
    public void Editar( ) throws IOException {
        if(Sel == null){
            alert.MensagemError("Selecione uma despesa na tabela");
            return;
        }

        if(!Txt_Despesa.getText().equals("") && !valor_despesa.getText().equals("") && !Combobox_Tipo.getValue().equals("Selecione")) {

            Sel.setNome_Despesa(Txt_Despesa.getText());
            Sel.setData_despesa(LocalDate.now());
            Sel.setValor(Double.parseDouble(valor_despesa.getText().replace(".", "").replace(",", ".")));
            Sel.setValor_Formatado(Sel.getValor());
            Sel.setTipo_Despesa(Combobox_Tipo.getValue().toString());
            Tabela_Despesa.refresh();
            boolean Ok = despesaTemporario.salvar(Listadespesas);
            if (Ok) {
                alert.MensagemSucess("Foi atualizado com sucesso");
                Limpar_Campos();
                Tabela_Despesa.getSelectionModel().clearSelection();
            }
        }
    }
   @FXML
       public void Adicionar() throws IOException {
        boolean Existe_Nome = Listadespesas.stream().anyMatch(despesa -> despesa.getNome_Despesa().equalsIgnoreCase(Txt_Despesa.getText()));
        if(!Existe_Nome){
if(!Txt_Despesa.getText().equals("") && !valor_despesa.getText().equals("") && !Combobox_Tipo.getValue().equals("Selecione")) {
          Despesa despesa = new Despesa();
          despesa.setNome_Despesa(Txt_Despesa.getText());
          despesa.setData_despesa(LocalDate.now());
          despesa.setValor(Double.parseDouble(valor_despesa.getText().replace(".", "").replace(",", ".")));
          despesa.setValor_Formatado(despesa.getValor());
          despesa.setTipo_Despesa(Combobox_Tipo.getValue().toString());
          Listadespesas.add(despesa);
         boolean Ok = despesaTemporario.salvar(Listadespesas);
         if(Ok){ alert.MensagemSucess("Foi salvo com sucesso"); Limpar_Campos();}

      }else {
          alert.MensagemError("Preencham os todos campos");
      }}else {
            alert.MensagemError("Essa despesa já existe");
        }

   }

}
