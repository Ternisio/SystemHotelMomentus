package com.example.demo.Models.Services;

import com.example.demo.Controllers.BtnBotoesQuartos;
import com.example.demo.Controllers.ControllerEditar_venda;
import com.example.demo.Controllers.ControllerTelaFuncionario;
import com.example.demo.Models.Classes.*;
import com.example.demo.Models.Dao.*;
import com.example.demo.Models.Interfaces.Calcular;
import com.example.demo.Models.Interfaces.FuncionarioVendaInterface;
import com.example.demo.Models.Interfaces.MeuClick;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ConsumoService implements FuncionarioVendaInterface {
    VendaDao vendaDao = new VendaDao();

    private  GridPane Grid;
     private MeuClick meuClick;
     private QuartoDao quartoDao;
     private Vendas vendas;
     private Funcionario funcionario;
     private Quarto quarto;
     private Config config;
     private ConfigDao configDao;

    ConsumoDao consumoDao = new ConsumoDao();
     private ControllerEditar_venda controllerEditar_venda;
@Override
     public void Lbl(ControllerEditar_venda controllerEditar_venda, Config config, ConfigDao configDao){
         this.controllerEditar_venda= controllerEditar_venda;
         this.config = config;
         this.configDao = configDao;
         configDao.ListaConfig(this.config);
     }
 private  ObservableList<Consumo> ListaConsumo = FXCollections.observableArrayList();
    private ObservableList<Quarto> ListaQuarto = FXCollections.observableArrayList();

    @Override
    public void Lbl(ControllerTelaFuncionario controllerTelaFuncionario, Config config, ConfigDao configDao) {

    }

    public void atualizado(GridPane Grid, QuartoDao quartoDao, Quarto quarto, Vendas vendas, Funcionario funcionario){}
    public String CodVenda() {return "";
    }
    @Override
    public void limparTabela() {}

    public void CarregarTabela(Vendas vendas){
        this.vendas = vendas;
        ListaConsumo = consumoDao.ListaConsumo(vendas);
        controllerEditar_venda.Coluna_Cod_Produto.setCellValueFactory(new PropertyValueFactory<Consumo,String>("idProduto"));
        controllerEditar_venda.Coluna_Nome_Produto_venda.setCellValueFactory(new PropertyValueFactory<Consumo,String>("Nome_prod"));
        controllerEditar_venda.Coluna_Valor_Produto_venda.setCellValueFactory(new PropertyValueFactory<Consumo,String>("ExibirValor"));
        controllerEditar_venda.Coluna_QTS_Produto_Venda.setCellValueFactory(new PropertyValueFactory<Consumo,Integer>("Qtd"));
        controllerEditar_venda.Tabela_Consumo_venda.setItems(ListaConsumo);

    }
    public  void Adicionar_Consumo(String Cod_Produto, int qtd, int TotalEstoques){
        boolean Verificarexistir = VerificarExistir(Cod_Produto);
        if (Verificarexistir){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");

            alert.setHeaderText("Erro:");
            alert.setContentText("Já existe na tabela");
            alert.showAndWait();

        }else {
            int restantes = ADDcalcularEstoques(qtd,TotalEstoques);
            consumoDao.cadastrar(vendas,Cod_Produto,qtd);
            consumoDao.editarProduto(Cod_Produto,restantes);
            CarregarTabela(vendas);
        }
    }

    @Override
    public boolean finalizar() {
        return false;
    }

    @Override
    public void Cadastro(Vendas vendas, Quarto quarto, Funcionario funcionario) {
     //   vendaDao.cadastrar(funcionario,vendas,quarto);

    }


    public boolean VerificarExistir(String Cod_produto){
        boolean verificarex = ListaConsumo.stream().anyMatch(Cod -> Cod.getIdProduto().equals(Cod_produto));
        return verificarex;

    }
    public void Editar_consumo(String Id_Produto, int qtd, int estoques){
consumoDao.editarConsumo(Id_Produto,qtd,vendas);
consumoDao.editarProduto(Id_Produto,estoques);
        CarregarTabela(vendas);


    }

    @Override
    public void excluir_consumo(String Id_produto, int qtd) {

    }

    public void excluir_consumo(String Id_produto,String Nome, int qtd){
        Object[] opc = {"Sim","Não"};

        int resultDialog =  JOptionPane.showOptionDialog(null,"Você quer excluir esse consumo: "+Nome,"Pergunta",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,opc,opc[0]);
        if(resultDialog == JOptionPane.YES_OPTION) {
            int qtd_estoques = consumoDao.Estoques(Id_produto);
            int totalEstoques = qtd_estoques + qtd;
            consumoDao.editarProduto(Id_produto, totalEstoques);
            consumoDao.Excluir(vendas, Id_produto);
            CarregarTabela(vendas);
        }


    }

    public int ADDcalcularEstoques(int qts, int Totalestoques){
        int restantes = Totalestoques - qts;
        return  restantes;
    }


public double TotalConsumo(){
   double ResultadoTotal =  ListaConsumo.stream().mapToDouble(C -> Double.parseDouble(C.getExibirValor().replace(",",".")) * C.getQtd()).sum();
   return ResultadoTotal;
}

    @Override
    public void pararThread() {

    }

    @Override
    public void Cancelado() {

    }

    public double total(Vendas vendas, Config config){
        double Total =0.00;
        LocalTime tpi = LocalTime.parse(config.getPernoite_inicio());
        LocalTime tpf = LocalTime.parse(config.getPernoite_fim());
        LocalDateTime DataPI = LocalDateTime.of(vendas.getData_hora_Entrada().getYear(),vendas.getData_hora_Entrada().getMonth(),vendas.getData_hora_Entrada().getDayOfMonth(),tpi.getHour(),tpi.getMinute());
        if (vendas.getData_hora_Entrada().getHour() == 0 || tpf.getHour()> vendas.getData_hora_Entrada().getHour()){
            DataPI = DataPI.minusDays(1);
        }
        if (vendas.getData_hora_Entrada().isBefore(DataPI)){
            Calcular calcular = new CalcularPorHoraServiceEditar();
            Total = calcular.calcular(config,vendas,controllerEditar_venda.lbl_Duracao_Venda);
        }else {
            System.out.println("enrtrou");
            Calcular calcular = new CalcularPorPernoiteServiceEditar();
            Total = Total + calcular.calcular(config,vendas,controllerEditar_venda.lbl_Duracao_Venda);
        }
        return  Total;
    }

    @Override
    public void Editar(FuncionarioDao funcionariodao, Funcionario funcionario, File file, TextField txt_cpf, PasswordField senha) {

    }
}