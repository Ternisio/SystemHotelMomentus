package com.example.demo.Controllers;

import com.example.demo.HelloApplication;
import com.example.demo.Models.Classes.*;
import com.example.demo.Models.Dao.ConfigDao;
import com.example.demo.Models.Dao.FuncionarioDao;
import com.example.demo.Models.Dao.QuartoDao;
import com.example.demo.Models.Interfaces.FuncionarioVendaInterface;
import com.example.demo.Models.Interfaces.LoginInterface;
import com.example.demo.Models.Interfaces.VendaInterface;
import com.example.demo.Models.Services.LoginService;
import com.example.demo.Util.MaskFieldUtil;
import io.github.gleidson28.GNAvatarView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class ControllerEditar_venda implements Initializable {

    @FXML
    public TableColumn<Consumo, String> Coluna_Cod_Produto;

    @FXML
    public TableColumn<Consumo, String> Coluna_Nome_Produto_venda;

    @FXML
    public TableColumn<Consumo, String> Coluna_Valor_Produto_venda;
    @FXML
    public TableColumn<Consumo,Integer> Coluna_QTS_Produto_Venda;

    @FXML
    public TableView<Consumo> Tabela_Consumo_venda;
    @FXML
    public VBox pane_Lista_do_Quarto;

    @FXML
    public Label lbl_Datatime_Saida;

    @FXML
    public Label lbl_Datatime_entrada;

    @FXML
    public Label lbl_Duracao_Venda;

    @FXML
    public Label lbl_Nome_fun_venda;

    @FXML
   public Label lbl_Num_quarto;

    @FXML
    private Button btn_Editar_consumo;

    @FXML
    private Button btn_Excluir_consumo;
    @FXML
    private GridPane Grid;
    @FXML
    private Label lbl_Hora;

    @FXML
    private AnchorPane dashboard_Inicio1;

    @FXML
public Label lbl_Total_Venda;
    @FXML
    private ScrollPane scroll;
   private Vendas vendas;
   private Funcionario funcionario;
   private FuncionarioVendaInterface funcionarioVendai;

    @FXML
    private Label lbl_Data;
    @FXML
    private Button btn_add_consumo;
    @FXML
    public HBox pane_Detalhes_Venda;
    @FXML
    private HBox Tela_Perfil;
    @FXML
    private HBox Form_inicio;
    @FXML
    private Label Btn_inicio;
Stage stage;
    @FXML
    private Label Btn_perfil;
    @FXML
private AnchorPane anchorPane;
    @FXML
    private ComboBox<String> ComboBox_Data_Hora_Estado;
    @FXML
    private GNAvatarView Foto_perfil;
    @FXML
    private Label Nome_fun_perfil;
    private Quarto quarto;
    @FXML
    private TextField Txt_Data_Hora_Entrada;

    @FXML
    private TextField Txt_Data_Hora_Saida;
    @FXML
    private ComboBox<String> ComboBox_Num_Quarto;
 private QuartoDao quartoDao;
 private VendaInterface vendaInterface;
 private ControllerDashboard controllerDashboard;
 Consumo_VendaController consumoController = new Consumo_VendaController();
 String id_produto,Nome_Produto;
 private Config config;
 int qtd_produto;
 String dt_anterior = "";
    @FXML
    private CheckBox check_add;
    String Num_quarto_inicial = "";
    public void inicial(QuartoDao quartoDao, Quarto quarto, Vendas vendas, FuncionarioVendaInterface funcionarioVendai,
                        Funcionario funcionario, Config config, ConfigDao configDao, VendaInterface vendaInterface, ControllerDashboard controllerDashboard){
        this.vendaInterface = vendaInterface;
        this.quartoDao = quartoDao;
       this.quarto = quarto;
       this.vendas = vendas;
       this.config = config;
       this.funcionarioVendai = funcionarioVendai;
       this.funcionario = funcionario;
       DesativarCheck();
       this.controllerDashboard = controllerDashboard;
       funcionarioVendai.atualizado(Grid,quartoDao,quarto,vendas,funcionario);
       funcionarioVendai.Lbl(this, config,configDao);
       Num_quarto_inicial = vendas.getNum_quarto();
        dt_anterior = vendas.getFormatado_Data_hora_Saida();
        CarregarEstado(dt_anterior);
        ComboBox_Num_Quarto.setValue(vendas.getNum_quarto());
       carregarNumQuarto(vendas.getNum_quarto());

    ComboBox_Data_Hora_Estado.setValue(vendas.getStatus());
    Txt_Data_Hora_Entrada.setText(vendas.getFormatado_Data_Entrada());
    Txt_Data_Hora_Saida.setText(vendas.getFormatado_Data_hora_Saida());

    funcionarioVendai.CarregarTabela(vendas);
    }
    ObservableList<Quarto> num = FXCollections.observableArrayList();
    @FXML
    void Finalizar(ActionEvent event) {
        vendaInterface.editar_Vendas(vendas,ComboBox_Data_Hora_Estado);
        double TotalConsumo = funcionarioVendai.TotalConsumo();
        LocalDateTime dt_entrada = LocalDateTime.parse(Txt_Data_Hora_Entrada.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        if(ComboBox_Data_Hora_Estado.getValue().equals("Finalizado")){
            LocalDateTime dt_saida = LocalDateTime.parse(dt_anterior, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            vendas.setData_hora_Saida(dt_saida);
            vendas.setData_hora_Entrada(dt_entrada);
            vendas.setStatus("Finalizado");
            double TotalDura = funcionarioVendai.total(vendas,config);
            double total = TotalDura + TotalConsumo;
            vendas.setTotal(total);
            vendaInterface.editar_Vendas(vendas,ComboBox_Data_Hora_Estado);
            LocalDate Data_entrada = LocalDate.of(dt_entrada.getYear(),dt_entrada.getMonth(),dt_entrada.getDayOfMonth());
            if(Num_quarto_inicial.equals(vendas.getNum_quarto())){
                vendas.setEstado("Sujo");
                quartoDao.Editar(vendas);
            }else{
                String Alteracao_Quarto = vendas.getNum_quarto();
                vendas.setEstado("Sujo");
                quartoDao.Editar(vendas);
                vendas.setNum_quarto(Num_quarto_inicial);
                vendas.setEstado("Disponível");
                quartoDao.Editar(vendas);
                Num_quarto_inicial = Alteracao_Quarto;

                ComboBox_Num_Quarto.getItems().clear();
                carregarNumQuarto(vendas.getNum_quarto());
                CarregarEstado(vendas.getFormatado_Data_hora_Saida());
            }
            controllerDashboard.PesquisarpordataAtualizado(Data_entrada,vendas,TotalConsumo);
        }else {
            vendas.setStatus("Realizando");
            LocalDate Data_entrada = LocalDate.of(dt_entrada.getYear(),dt_entrada.getMonth(),dt_entrada.getDayOfMonth());
            vendas.setTotal(0.00);
            vendaInterface.editar_Vendas(vendas, ComboBox_Data_Hora_Estado);
            if(Num_quarto_inicial.equals(vendas.getNum_quarto())){
                vendas.setEstado("Ocupado");
                quartoDao.Editar(vendas);

            }else{
                String Alteracao_Quarto = vendas.getNum_quarto();
                vendas.setEstado("Ocupado");
                quartoDao.Editar(vendas);
                vendas.setNum_quarto(Num_quarto_inicial);
                vendas.setEstado("Disponível");
                quartoDao.Editar(vendas);
                Num_quarto_inicial = Alteracao_Quarto;
                vendas.setNum_quarto(Num_quarto_inicial);
                System.out.println("\n\n\n Novo valor do quarto : " +vendas.getNum_quarto());


                carregarNumQuarto(Num_quarto_inicial);
                CarregarEstado(vendas.getFormatado_Data_hora_Saida());

            }
            controllerDashboard.PesquisarpordataAtualizado(Data_entrada,vendas,TotalConsumo);

        }


    }
    void  carregarNumQuarto(String Num){

        num.clear();
        num = quartoDao.ListaQuarto();
        System.out.println(num.get(0));
        if(Num == null || Num.isEmpty() || Num.isBlank()){
            System.out.println("vazia");
        }
        Optional<Integer> Codigo_quarto = num.stream().filter(p-> p.getNum_quarto().equals(Num)).map(Quarto::getCod_Quarto).findFirst();
        vendas.setCod_Quarto(Codigo_quarto.get());
        ComboBox_Num_Quarto.getItems().clear();

        for(int i = 0; i<num.size();i++){
        if (num.get(i).getEstado().equals("Disponível") || num.get(i).getEstado().equals("Sujo")|| num.get(i).getNum_quarto().equals(Num_quarto_inicial)){
            System.out.println(num.get(i).getNum_quarto());
            ComboBox_Num_Quarto.getItems().addAll(num.get(i).getNum_quarto());
        }
        }
        ComboBox_Num_Quarto.setValue(Num);

    }
    void CarregarEstado(String dt_anterior){
        if (dt_anterior.equals("")){
            ComboBox_Data_Hora_Estado.setItems(FXCollections.observableArrayList("Realizando"));
        }else {
            ComboBox_Data_Hora_Estado.setItems(FXCollections.observableArrayList("Realizando", "Finalizado"));
        }
    }
    @FXML
    void add_consumo(ActionEvent event) {
        try {
                Stage stage = new Stage();
                FXMLLoader loader =  new FXMLLoader(getClass().getResource("/com/example/demo/Views/Tela_Consumo_Venda.fxml"));
                Parent root = loader.load();
                consumoController = loader.getController();
                consumoController.setControllerTelaFuncionario( funcionarioVendai);
                consumoController.ativarBotaoAdicionar();
                Scene scene = new Scene(root);
                stage.setTitle("Sistema Do Motel");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(btn_add_consumo.getScene().getWindow());
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }
    public  void CheckboxAdd(ActionEvent event){
        check_add.setSelected(false);
        btn_add_consumo.setDisable(false);
        btn_Editar_consumo.setDisable(true);
        btn_Excluir_consumo.setDisable(true);
        DesativarCheck();
    }
    @FXML
    void Editar_consumo(ActionEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader =  new FXMLLoader(getClass().getResource("/com/example/demo/Views/Tela_Consumo_Venda.fxml"));
            Parent root = loader.load();
            consumoController = loader.getController();
            consumoController.setControllerTelaFuncionario(funcionarioVendai);
            consumoController.ativarBotaoEditar();
            consumoController.Editar_Selecionada_Produto(id_produto,Nome_Produto,qtd_produto);
            Scene scene = new Scene(root);
            stage.setTitle("Sistema Do Motel");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btn_add_consumo.getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    void AtivarCheck(){
        check_add.setVisible(true);
        check_add.managedProperty().bind(check_add.visibleProperty());
            }

    void DesativarCheck(){
        check_add.setVisible(false);
        check_add.managedProperty().bind(check_add.visibleProperty());
    }
    @FXML
    void Excluir(ActionEvent event){
        funcionarioVendai.excluir_consumo(id_produto,Nome_Produto,qtd_produto);
    }
    @FXML
    void SelecionadaTabela(MouseEvent event) {
        int index = Tabela_Consumo_venda.getSelectionModel().getSelectedIndex();
        if (index < -1) {
            return;
        } else {
            id_produto = Coluna_Cod_Produto.getCellData(index) ;
            Nome_Produto = Coluna_Nome_Produto_venda.getCellData(index);
            qtd_produto = Coluna_QTS_Produto_Venda.getCellData(index);
            btn_add_consumo.setDisable(true);
            btn_Editar_consumo.setDisable(false);
            btn_Excluir_consumo.setDisable(false);
            AtivarCheck();


        }
    }
    public void ComboboxNum(ActionEvent event){
        Optional<Integer> Codigo_quarto = num.stream().filter(p-> p.getNum_quarto().equals(ComboBox_Num_Quarto.getValue())).map(Quarto::getCod_Quarto).findFirst();

        vendas.setNum_quarto(ComboBox_Num_Quarto.getValue());
        if(vendas.getNum_quarto() == null || vendas.getNum_quarto().isEmpty() || vendas.getNum_quarto().isBlank()){
            return;
        }else{
            System.out.println(vendas.getNum_quarto());
            vendas.setCod_Quarto(Codigo_quarto.get());
        }


    }
    public void ComboboxEstado(ActionEvent event){
        System.out.println(ComboBox_Data_Hora_Estado.getValue());
        if (ComboBox_Data_Hora_Estado.getValue() == null || ComboBox_Data_Hora_Estado.getValue().isEmpty() || ComboBox_Data_Hora_Estado.getValue().isBlank()){
         return;
        }
       else if (ComboBox_Data_Hora_Estado.getValue().equals("Realizando")){
            Txt_Data_Hora_Saida.setText("");
            vendas.setFormatado_Data_hora_Saida("");
        }else{
            Txt_Data_Hora_Saida.setText(dt_anterior);
            vendas.setFormatado_Data_Entrada(dt_anterior);
        }
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lbl_Duracao_Venda.setVisible(false);
       lbl_Duracao_Venda.managedProperty().bind(lbl_Duracao_Venda.visibleProperty());

    }
}