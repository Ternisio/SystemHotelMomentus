package com.example.demo.Models.Services;

import com.example.demo.Controllers.ControllerDashboard;
import com.example.demo.Models.Classes.Despesa;
import com.example.demo.Models.Dao.DespesaDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AdminDespesaService {
    Despesa despesa = new Despesa();
    DespesaDao despesaDao = new DespesaDao();
    private ControllerDashboard controllerDashboard;
    public void inicial(ControllerDashboard controllerDashboard){
        this.controllerDashboard = controllerDashboard;
        controllerDashboard.Check_Novo_Cadastro_Despesa.setVisible(false);
        carregadoTabela();

    }
    void carregadoTabela(){
        ObservableList<Despesa> ListaDespesa = FXCollections.observableArrayList();
        controllerDashboard.Codigo_despesa.setCellValueFactory(new PropertyValueFactory<Despesa,String>("Codigo_Despesa"));
        controllerDashboard.Data_Despesa_Tb.setCellValueFactory(new PropertyValueFactory<Despesa,String>("Data_despesa_formatado"));
        controllerDashboard.Tipo_Despesa.setCellValueFactory(new PropertyValueFactory<Despesa,String>("Tipo_Despesa"));
        controllerDashboard.Nome_Despesa.setCellValueFactory(new PropertyValueFactory<Despesa,String>("Nome_Despesa"));
        controllerDashboard.Valor_Despesa.setCellValueFactory(new PropertyValueFactory<Despesa,String>("Valor_Formatado"));
        ListaDespesa = despesaDao.ListaDeDespesa();
        controllerDashboard.Tabela_despesa.setItems(ListaDespesa);
    }
    public void cadastrar(TextField Nome_despesa, DatePicker data_despesa, ComboBox tipo_despesa, TextField Valor){
    if(Valor.getText().equals("")||Nome_despesa.getText().equals("")|| data_despesa.getValue() == null|| tipo_despesa.getValue().equals("Selecione o tipo")){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");

        alert.setHeaderText("Erro:");
        alert.setContentText("Preencham todos os campos");
        alert.showAndWait();
    }else {
        despesa.setNome_Despesa(Nome_despesa.getText());
        despesa.setTipo_Despesa(String.valueOf(tipo_despesa.getValue()));
        despesa.setData_despesa(data_despesa.getValue());
        despesa.setValor(Double.valueOf(Valor.getText().replace(".","").replace(",",".")));
        despesaDao.cadastrar(despesa);
        carregadoTabela();
        Limpar(Nome_despesa,data_despesa,tipo_despesa,Valor);
    }
    }
    public void Editar(TextField Nome_despesa, DatePicker data_despesa, ComboBox tipo_despesa, TextField Valor){
        if(Valor.getText().equals("")||Nome_despesa.getText().equals("")|| data_despesa.getValue() == null|| tipo_despesa.getValue().equals("Selecione as opções")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");

            alert.setHeaderText("Erro:");
            alert.setContentText("Preencham todos os campos");
            alert.showAndWait();
        }else {
            despesa.setNome_Despesa(Nome_despesa.getText());
            despesa.setTipo_Despesa(String.valueOf(tipo_despesa.getValue()));
            despesa.setData_despesa(data_despesa.getValue());
            despesa.setValor(Double.valueOf(Valor.getText().replace(".","").replace(",",".")));
            despesaDao.Editar(despesa);
            carregadoTabela();
            Limpar(Nome_despesa,data_despesa,tipo_despesa,Valor);
            controllerDashboard.Btn_Editar_despesa.setDisable(true);
            controllerDashboard.Btn_Excluir_despesa.setDisable(true);
            controllerDashboard.Btn_Cadastrar_despesa.setDisable(false);
            controllerDashboard.Btn_Limpar_Tudo_despesa.setDisable(false);
            controllerDashboard.Check_Novo_Cadastro_Despesa.setVisible(false);
        }
    }
    public void formatado(TextField Nome_despesa, DatePicker data_despesa, ComboBox tipo_despesa, TextField Valor){
        controllerDashboard.Check_Novo_Cadastro_Despesa.setVisible(false);
        controllerDashboard.Check_Novo_Cadastro_Despesa.setSelected(false);
        controllerDashboard.Btn_Cadastrar_despesa.setDisable(false);
        controllerDashboard.Btn_Limpar_Tudo_despesa.setDisable(false);
        controllerDashboard.Btn_Excluir_despesa.setDisable(true);
        controllerDashboard.Btn_Editar_despesa.setDisable(true);
        controllerDashboard.DATA_FILTRADO_DESPESA.setValue(null);
        carregadoTabela();
        Limpar(Nome_despesa,data_despesa,tipo_despesa,Valor);
    }
     void Limpar(TextField Nome_despesa, DatePicker data_despesa, ComboBox tipo_despesa, TextField Valor){

        Nome_despesa.setText("");
        data_despesa.setValue(null);
        tipo_despesa.setValue("Selecione as opções");
        Valor.setText("");
    }
  public void tab_Sel(TextField Nome_despesa, DatePicker data_despesa, ComboBox tipo_despesa, TextField Valor){
        int index = controllerDashboard.Tabela_despesa.getSelectionModel().getSelectedIndex();
        if (index < -1 || controllerDashboard.Tabela_despesa.getSelectionModel().isEmpty() ){
            Limpar(Nome_despesa,data_despesa,tipo_despesa,Valor);
        }
        controllerDashboard.Check_Novo_Cadastro_Despesa.setVisible(true);
        controllerDashboard.Btn_Editar_despesa.setDisable(false);
        controllerDashboard.Btn_Excluir_despesa.setDisable(false);
        controllerDashboard.Btn_Cadastrar_despesa.setDisable(true);
        controllerDashboard.Btn_Limpar_Tudo_despesa.setDisable(true);

        String Codigo = controllerDashboard.Codigo_despesa.getCellData(index);
        String nome = controllerDashboard.Nome_Despesa.getCellData(index);
        String data = controllerDashboard.Data_Despesa_Tb.getCellData(index);
        String Tipo = controllerDashboard.Tipo_Despesa.getCellData(index);
        String valor = controllerDashboard.Valor_Despesa.getCellData(index);
        Nome_despesa.setText(nome);
        despesa.setCodigo_Despesa(Codigo);
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
      LocalDate ld = LocalDate.parse(data, formatter);
        data_despesa.setValue(ld);
        tipo_despesa.setValue(Tipo);
        Valor.setText(valor);
  }
public void clica_check(TextField Nome_despesa, DatePicker data_despesa, ComboBox tipo_despesa, TextField Valor){
        controllerDashboard.Check_Novo_Cadastro_Despesa.setVisible(false);
        controllerDashboard.Check_Novo_Cadastro_Despesa.setSelected(false);
        controllerDashboard.Btn_Cadastrar_despesa.setDisable(false);
        controllerDashboard.Btn_Limpar_Tudo_despesa.setDisable(false);
    controllerDashboard.Btn_Excluir_despesa.setDisable(true);
    controllerDashboard.Btn_Editar_despesa.setDisable(true);
        Limpar(Nome_despesa,data_despesa,tipo_despesa,Valor);
        data_despesa.requestFocus();

}
public void excluir(){
        despesaDao.Excluir(despesa);
        carregadoTabela();
}
}
