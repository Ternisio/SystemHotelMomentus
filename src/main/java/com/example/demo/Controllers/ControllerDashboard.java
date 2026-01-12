package com.example.demo.Controllers;

import com.example.demo.HelloApplication;
import com.example.demo.Models.Classes.*;
import com.example.demo.Models.Dao.*;
import com.example.demo.Models.Interfaces.*;
import com.example.demo.Models.Services.*;
import com.example.demo.Util.MaskFieldUtil;
import io.github.gleidson28.GNAvatarView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class ControllerDashboard extends MaskFieldUtil implements  Initializable {

    @FXML
    private HBox Col_dtpicker_fun_venda;
    @FXML
    private Label Erro_Rel;
    @FXML
    private Label error_venda_por_produto;
    @FXML
    private TextField Textfield_Cpf_funcionario;

    @FXML
    private TextField Textfield_Nome_funcionario;
    @FXML
    private TextField Textfield_Pesquisar_funcionario;
    @FXML
    public TextField Txt_nome_despesa;
    @FXML
    public TextField Txt_Valor_despesa;
    @FXML
    private TextField Textfield_Senha_funcionario;
    @FXML
    private ComboBox<String> Combobox_Tipo_funcionario;
    @FXML
    public ComboBox<String> ComboBox_grafico_venda_Por_Quarto;
    @FXML
    public ComboBox<String> ComboBox_Qtd_produto_por_venda;
    @FXML
    public ComboBox<String> ComboBox_Filtrar_produto_por_venda;
    @FXML
    public HBox Hbox_error_Produto_label;
    @FXML
    public HBox Hbox_produto_por_vend_datas;
    @FXML
    public ComboBox<String> Combobox_Tipo_Despesa;
    @FXML
    private Label Label_De_Grafico_por_venda;
    @FXML
    private Label Ganhador_Pesquisar_Produto;
    @FXML
    private Label Valor_Total_Venda_Produto;
    @FXML
    public Label Total_Nome_Fun1;
    @FXML
    public Label Total_Valor_Fun1;
    @FXML
    public Label Total_Valor_Fun2;
    @FXML
    public Label Total_Nome_Fun2;
    @FXML
    public Label Valor_diferencas_Fun;
    @FXML
    public Label Ganhador_Pesquisar_fun;
    @FXML
    public Label Valor_Total_Venda_Fun;
    @FXML
    public Label Lbl_Total_Venda_Fun;
    @FXML
    private Label Label_erro_Grafico_venda_por_Fun;
    @FXML
    private Label Label_erro_Grafico_venda_por_quarto;
    @FXML
    private Label Label_Para_Grafico_por_venda;

public CategoryAxis xAxis = new CategoryAxis();
public NumberAxis yAxis = new NumberAxis();
    public CategoryAxis xAxisF = new CategoryAxis();
    public NumberAxis yAxisF = new NumberAxis();
    public CategoryAxis xAxisP = new CategoryAxis();
    public NumberAxis yAxisP = new NumberAxis();
    @FXML
    public BarChart<String,Number> Grafico_venda_por_fun = new BarChart<>(xAxisF,yAxisF) ;

    @FXML
    public BarChart<String,Number> grafico_venda = new BarChart<>(xAxis,yAxis) ;
    @FXML
    public BarChart<String,Number> Grafico_venda_por_produto = new BarChart<>(xAxisP,yAxisP) ;
    @FXML
    public DatePicker Data_Personalizada_Inicio_Grafico_Venda_Por_Quarto;
    @FXML
    public DatePicker Data_Inicial_Rel;
    @FXML
    public DatePicker DATA_FILTRADO_DESPESA;
    @FXML
    public DatePicker Data_Final_Rel;

    @FXML
    public DatePicker Data_Personalizada_Fim_Grafico_Venda_Por_Quarto;
    @FXML
    public DatePicker Data_Personalizada_Fim_Grafico_Venda_Por_Fun;
    @FXML
    public DatePicker Data_despesa;
    @FXML
    public DatePicker Data_Personalizada_Inicio_Grafico_Venda_Por_Fun;

    @FXML
    private TextField txt_cada_hora;

    @FXML
    private DatePicker TXT_DATEPICKER;
    @FXML
    private DatePicker DTP_Inicial_VPP;
    @FXML
    private DatePicker DTP_Fim_VPP;
    @FXML
    private TextField txt_duas_hora;

    @FXML
    private TextField txt_pernoite_Incial;
    @FXML
    private TextField txt_pernoite_meio;

    @FXML
    private TextField txt_pernoite_fim;

    @FXML
    private TextField txt_pernoite_valor;

    @FXML
    private TextField txt_uma_hora;

    @FXML
    private Button btn_excluir_fun;
    @FXML
    public Button Btn_Cadastrar_despesa;
    @FXML
    public Button Btn_Editar_despesa;
    @FXML
    public Button Btn_Excluir_despesa;
    @FXML
    public Button Btn_Limpar_Tudo_despesa;
    @FXML
    private Button btn_editar_venda;
    @FXML
    private Button btn_excluir_venda;

    @FXML
    private Button btn_Editar_fun;
    @FXML
    private Button btn_Limpar_tudo_fun;
    @FXML
    private Button btn_cadastrar_fun;
@FXML
private HBox DT_Inicial_DT_Final_HBox_RELATORIO;
    @FXML
    private CheckBox CheckBox_Funcionario;
    @FXML
    public CheckBox Check_Novo_Cadastro_Despesa;
    @FXML
    private TableView<Funcionario> Tabela_funcionario;
    @FXML
    private TableColumn<Funcionario, String> Coluna_Codigo_fun;
    @FXML
    private TableColumn<Funcionario, String> Coluna_Nome_fun;
    @FXML
    private TableColumn<Funcionario, String> Coluna_Senha_fun;
    @FXML
    private TableColumn<Funcionario, String> Coluna_Tipo_fun;
    @FXML
    private TableColumn<Funcionario, String> Coluna_Cpf_fun;
    @FXML
    private TableColumn<Funcionario, String> Coluna_Foto_fun;
    @FXML
    public TableView<Despesa> Tabela_despesa;
    @FXML
    public TableColumn<Despesa, String> Codigo_despesa;
    @FXML
    public TableColumn<Despesa, String> Tipo_Despesa;
    @FXML
    public TableColumn<Despesa, String> Data_Despesa_Tb;
    @FXML
    public TableColumn<Despesa, String> Nome_Despesa;
    @FXML
    public TableColumn<Despesa, String> Valor_Despesa;

    @FXML
    private TextField Valor_prod;
    @FXML
    private CheckBox Checkbox_Novo_Cadastro_Quarto;

    @FXML
    private TextField Pesquisar_Produto;
    ConsumoDao consumoDao =  new ConsumoDao();


    @FXML
    private CheckBox CheckBox_Novo_Cadastro_Produto;
    @FXML
    private ComboBox<String> Combox_Estado_quarto;

    @FXML
    private TextField TextField_Num_quarto;

    @FXML
    private TextField TextField_pesquisar;

    @FXML
    private Button btn_cadastrar_quarto;

    @FXML
    private Button btn_editar_quarto;

    @FXML
    private Button btn_cadastrar_produto;

    @FXML
    private Button btn_editar_produto;
    @FXML
    private Button btn_excluir_produto;

    @FXML
    private Button btn_limpar_tudo_produto;
    @FXML
    private TableView<Quarto> Tabela_Quarto;
    @FXML
    private TableColumn<Quarto, Integer> Coluna_Codigo_Quarto;

    @FXML
    private TableColumn<Quarto, String> Coluna_Estado_Quarto;

    @FXML
    private TableColumn<Quarto, String> Coluna_Numero_Quarto;
    @FXML
    private Button btn_excluir_quarto;

    @FXML
    private Button btn_limpar_tudo_quarto;

    @FXML
    private AnchorPane dashboard_Inicio;

    @FXML
    private AnchorPane Tela_Venda;
    @FXML
    private AnchorPane Tela_despesa;
    @FXML
    private Label btn_despesa;


    @FXML
    private AnchorPane Tela_Venda_Menu;

    @FXML
    private AnchorPane Tela_Venda_Fun;

    @FXML
    private AnchorPane  TelaVendaporQuarto;

    @FXML
    private AnchorPane  tela_relatorio;

    @FXML
    private AnchorPane Tela_Venda_Por_Quarto;

    @FXML
    private AnchorPane AdminTelaSettings;
    public ObservableList<String> Lista_n2 = FXCollections.observableArrayList();

    @FXML
    private AnchorPane AdminTelaFun;

    @FXML
    private AnchorPane TelaGraficoVendaPorQuarto;
    @FXML
    private AnchorPane AdminTelaQuarto;
    @FXML
    private AnchorPane AdminTelaProd;
    @FXML
    private HBox Componente_De_Para;


ControllerEditar_venda controllerEditar_venda = new ControllerEditar_venda();
FuncionarioVendaInterface funcionarioVendaInterface = new ConsumoService();
    @FXML
    private TableView<Vendas> Tabela_Venda_Hoje_Inicio;
    @FXML
    private TableColumn<Vendas, String> Col_Data_Hora_Entrada_Inicio;

    @FXML
    private TableColumn<Vendas, String> Col_Data_Hora_Saida_Inicio;

    @FXML
    private TableColumn<Vendas, String> Col_Num_quarto_Inicio;

    @FXML
    private TableColumn<Vendas, String> Col_Status_inicio;
    @FXML
    private Label Btn_Inicio;
    @FXML
    private Label Btn_relatorio;
    @FXML
    private Label Btn_fun;
    @FXML
    private Label Btn_Setting;

    @FXML
    private Label Btn_Quarto;
    @FXML
    private Label Btn_produto;
    @FXML
    private Label Btn_Vendas;
    @FXML
    private Label Lbl_Total_venda_Entrada_por_quarto;
    @FXML
    public Label txt_Valor_grafico;
    @FXML
    private Label Lbl_Total_venda_Saida_por_quarto;
    @FXML
    private Label Lbl_Total_venda_Consumo_por_quarto;
    @FXML
    public StackPane stackpaneVendaPorgrafico;
    @FXML
    private Label Lbl_Total_venda_por_quarto;
    @FXML
    private Label Por_hora_saida;

    @FXML
    private Label Lbl_txt_Total_Entrada_Venda_por_Quarto;

    @FXML
    private Label Lbl_txt_Total_Saida_Venda_por_Quarto;


    @FXML
    private TableView<Produto> Tabela_Produto;
    @FXML
    private TableColumn<Produto, String> Coluna_Codigo_Produto;
    @FXML
    private TableColumn<Produto, Integer> Coluna_Estoques_Produto;
    @FXML
    private TableColumn<Produto, String> Coluna_Foto_Produto;

    @FXML
    private TableColumn<Produto, String> Coluna_Nome_Produto;
    @FXML
    private TableColumn<Produto, String> Coluna_Tipo_Produto;

    @FXML
    private TableColumn<Produto, String> Coluna_Valor_Produto;
    @FXML
    private TableView<Vendas> Tabela_Venda_Por_Quarto;
    @FXML
    private TableColumn<Vendas, String> Col_Codigo_Venda_Por_Quarto;
    @FXML
    private TableColumn<Vendas, String> Col_Nome_Fun_Venda_Por_Quarto;
    @FXML
    private TableColumn<Vendas, String> Col_Num_Venda_Por_Quarto;
    String Combobox_fil_Dur_Rel = "";
    String Combobox_graficoVendaPorQuarto = "";
    String Combobox_fil_graficoVendaporfun = "";
 private Vendas vendas = new Vendas();
    @FXML
    private TableColumn<Vendas, String> Col_Data_Hora_Entrada_Venda_Por_Quarto;

    @FXML
    private TableColumn<Vendas, String> Col_Data_Hora_Saida_Venda_Por_Quarto;

    @FXML
    private TableColumn<Vendas, String> Col_Total_Venda_Por_Quarto;
    @FXML
    private TableColumn<Vendas, String> Col_Status_Venda_Por_Quarto;

    @FXML
    private TableView<Consumo> Tabela_Consumo_Venda_Por_Quarto;
    @FXML
    private TableColumn<Consumo, String> Col_Nome_Consumo_Venda_Por_Quarto;
    @FXML
    private TableColumn<Consumo, String> Col_Valor_Consumo_Venda_Por_Quarto;
    @FXML
    private TableColumn<Consumo, Integer> Col_QTD_Consumo_Venda_Por_Quarto;
    @FXML
    private TextField TextfliedEstoques_prod;
    @FXML
    private ComboBox<String> Combobox_Produto_Situacao;
    @FXML
    private ComboBox<String> Combobox_Duracao_Rel;
    @FXML
    private ComboBox<String> Combobox_Tipo_Rel;
    @FXML
    public ComboBox<String> ComboBox_N1_grafico_venda_Por_Fun;
    @FXML
    public ComboBox<String> ComboBox_N2_grafico_venda_Por_Fun;
    @FXML
    private ComboBox<String> ComboBox_Filtrar_grafico_venda_Por_Fun;

    @FXML
    private TextField TextfliedNome_prod;
    @FXML
    private ComboBox<String> Combobox_tipo_produto;
    File fileProduto;
    @FXML
    private ImageView ImagemProduto;
    @FXML
    private GNAvatarView Foto_perfil;
    File FileFuncionario;

    @FXML
    private GNAvatarView ImageFun;
    @FXML
    private Label lbl_valor_cada_hora;
    AdminDespesaService adminDespesaService = new AdminDespesaService();
    @FXML
    private Label lbl_valor_diaria_hora;

    @FXML
    private Label lbl_valor_pernoite_meio;

    @FXML
    private Label lbl_valor_duas_hora;

    @FXML
    private Label lbl_valor_pernoite_diaria;

    @FXML
    private Label Lbl_Total_Venda;
    @FXML
    private Label lbl_valor_pernoite_fim;

    @FXML
    private Label lbl_valor_pernoite_inicio;
@FXML
private AnchorPane Tela_Venda_Prod;
    @FXML
    private Label lbl_valor_pernoite_valor;

    @FXML
    private Label lbl_valor_uma_hora;


    @FXML
    private Label Lbl_hora;

    @FXML
    private Label Lbl_Data;
    @FXML
    public Label Lbl_Total_Venda_Prod1;
    @FXML
    public Label Valor_Total_Venda_Produto1;
    @FXML
    private Label Quartos_Ocupados;

    @FXML
    private Label Nome_admin;

    @FXML
    private Label Quartos_disponiveis;
    private VendaInterface vendaInterface;
    private VendaInterface vendaInterfaceFun;
    private AdminProdutoInterface adminprodutoi;

    private AdminQuartoInterface adminquartoi;
    private AdminFuncionarioInterface adminfuncionarioi;
    private AdminConfigInterface adminconfigi;
    @FXML
    private TableView<Consumo> Tabela_Consumo_inicio;
    @FXML
    private TableColumn<Consumo,String> Col_Num_quarto_consumo_inicio;
    @FXML
    private TableColumn<Consumo,String> Col_Total_consumo_inicio;
AdminVendaPorProdutos adminVendaPorProdutos = new AdminVendaPorProdutos();
    private Quarto quarto;
    private QuartoDao quartodao ;
    private ProdutoDao produtoDao ;
    private Produto produto;
    private Funcionario funcionario;
    private FuncionarioDao funcionarioDao ;
    private Config config;
    private  ConfigDao configDao ;
    public boolean Pare = false;
    Stage stage;
public ObservableList<String> Lista_Nomes_Fun = FXCollections.observableArrayList();

Relatorio relatorio = new Relatorio();
    @FXML
    private AnchorPane anchorPane;

    void CarregarComboboxTodas() {
        Combox_Estado_quarto.setItems(FXCollections.observableArrayList("Selecione o estado", "Disponível", "Ocupado", "Sujo", "Manutenção"));

        Combox_Estado_quarto.setValue("Selecione o estado");
        Combobox_tipo_produto.setItems(FXCollections.observableArrayList("Selecione o tipo", "Bebidas", "Diversos", "Itens", "Pratos",
                "Produtos eroticos"));
        Combobox_tipo_produto.setValue("Selecione o tipo");
        Combobox_Produto_Situacao.setItems(FXCollections.observableArrayList("Selecione a situação", "Com estoques", "Sem estoques"));
        Combobox_Produto_Situacao.setValue("Selecione a situação");
        Combobox_Tipo_funcionario.setItems(FXCollections.observableArrayList("Selecione o tipo", "Administrador", "Recepcionista"));
        Combobox_Tipo_funcionario.setValue("Selecione o tipo");
        ComboBox_grafico_venda_Por_Quarto.setItems(FXCollections.observableArrayList("Selecione as opções", "Por 30 dias", "Por 15 dias","Por 7 dias","Por datas personalizadas"));
        ComboBox_grafico_venda_Por_Quarto.setValue("Selecione as opções");
        Combobox_Duracao_Rel.setItems(FXCollections.observableArrayList("Selecione as opções", "Por 30 dias", "Por 15 dias","Por 7 dias","Por datas personalizadas"));
        Combobox_Duracao_Rel.setValue("Selecione as opções");
        Combobox_Tipo_Rel.setItems(FXCollections.observableArrayList("Selecione as opções", "Lista de vendas", "Lista de produtos"));
        Combobox_Tipo_Rel.setValue("Selecione as opções");
        Combobox_graficoVendaPorQuarto = "Selecione as opções";
        ComboBox_N1_grafico_venda_Por_Fun.setItems(Lista_Nomes_Fun);
        ComboBox_N1_grafico_venda_Por_Fun.setValue("Slecione o funcionário");
        Combobox_Tipo_Despesa.setItems(FXCollections.observableArrayList("Selecione as opções","Reforma","Manutenção","Produtos","Funcionário","Conta a pagar"));
        Combobox_Tipo_Despesa.setValue("Selecione as opções");
        ComboBox_Qtd_produto_por_venda.setItems(FXCollections.observableArrayList("Selecione as opções","Por 10 produtos","Por 20 produtos","Por 30 produtos","Por 40 produtos","Por 50 produtos"));
        ComboBox_Qtd_produto_por_venda.setValue("Selecione as opções");
        ComboBox_Filtrar_produto_por_venda.setItems(FXCollections.observableArrayList("Selecione as opções", "Por 30 dias", "Por 15 dias","Por 7 dias","Por datas personalizadas"));
        ComboBox_Filtrar_produto_por_venda.setValue("Selecione as opções");
    }

    void telasVendasDesativar(){
        Tela_Venda.setVisible(false);
        Tela_Venda_Menu.setVisible(false);
        Tela_Venda_Por_Quarto.setVisible(false);
        TelaGraficoVendaPorQuarto.setVisible(false);


    }
    void telasVendasAtivar(){
        Tela_Venda.setVisible(true);
        Tela_Venda_Menu.setVisible(true);
    }
    void CarregarTabelasTodas() {
        CarregarTabelaQuarto();
        CarregarTabelaProduto();
        CarregarTabelaFuncionario();
        CarregarTabelaVendadeHojeInicio();
        CarregarTabelaConsumodeHojeInicio();
        CarregarTabelaVenda_Por_Quarto();

    }
    public void TrocarFormulario(MouseEvent event){
        if(event.getSource() == Btn_Inicio ){
            dashboard_Inicio.setVisible(true);
           AdminTelaFun.setVisible(false);
           AdminTelaProd.setVisible(false);
            AdminTelaSettings.setVisible(false);
            AdminTelaQuarto.setVisible(false);
            Tela_despesa.setVisible(false);
            tela_relatorio.setVisible(false);
            telasVendasDesativar();
            Btn_Inicio.setStyle("-fx-background-color:  linear-gradient(to right, #0f2027, #203a43, #2c5364);" +
                    "-fx-background-radius: 10px");
            Btn_produto.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_Quarto.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_Setting.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_fun.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_Vendas.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            btn_despesa.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_relatorio.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
        } else if(event.getSource() == Btn_fun ){
            dashboard_Inicio.setVisible(false);
            AdminTelaFun.setVisible(true);
            AdminTelaProd.setVisible(false);
            AdminTelaSettings.setVisible(false);
            AdminTelaQuarto.setVisible(false);
            Tela_despesa.setVisible(false);
            tela_relatorio.setVisible(false);
            telasVendasDesativar();
            Btn_fun.setStyle("-fx-background-color:  linear-gradient(to right, #0f2027, #203a43, #2c5364);" +
                    "-fx-background-radius: 10px");
            Btn_produto.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_Quarto.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_Setting.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_Inicio.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_Vendas.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            btn_despesa.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_relatorio.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");

        } else if(event.getSource() == Btn_produto ){
            dashboard_Inicio.setVisible(false);
            AdminTelaFun.setVisible(false);
            AdminTelaProd.setVisible(true);
            AdminTelaSettings.setVisible(false);
            AdminTelaQuarto.setVisible(false);
            Tela_despesa.setVisible(false);
            tela_relatorio.setVisible(false);
            telasVendasDesativar();

           Btn_produto.setStyle("-fx-background-color:  linear-gradient(to right, #0f2027, #203a43, #2c5364);" +
                    "-fx-background-radius: 10px");
            Btn_fun.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_Quarto.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_Setting.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_Inicio.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_Vendas.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            btn_despesa.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_relatorio.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");


        }
        else if(event.getSource() == Btn_Quarto ){
            dashboard_Inicio.setVisible(false);
            AdminTelaFun.setVisible(false);
            AdminTelaProd.setVisible(false);
            AdminTelaSettings.setVisible(false);
            AdminTelaQuarto.setVisible(true);
            Tela_despesa.setVisible(false);
            tela_relatorio.setVisible(false);
            telasVendasDesativar();

            Btn_Quarto.setStyle("-fx-background-color:  linear-gradient(to right, #0f2027, #203a43, #2c5364);" +
                    "-fx-background-radius: 10px");
            Btn_fun.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_produto.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_Setting.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_Inicio.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_Vendas.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            btn_despesa.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_relatorio.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");

        }

        else if(event.getSource() == Btn_Setting ){
            dashboard_Inicio.setVisible(false);
            AdminTelaFun.setVisible(false);
            AdminTelaProd.setVisible(false);
            AdminTelaSettings.setVisible(true);
            AdminTelaQuarto.setVisible(false);
            Tela_despesa.setVisible(false);
            tela_relatorio.setVisible(false);
            telasVendasDesativar();

            Btn_Setting.setStyle("-fx-background-color:  linear-gradient(to right, #0f2027, #203a43, #2c5364);" +
                    "-fx-background-radius: 10px");
            Btn_fun.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_produto.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_Quarto.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_Inicio.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_Vendas.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            btn_despesa.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_relatorio.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");

        }
        else if(event.getSource() == Btn_Vendas ){
            dashboard_Inicio.setVisible(false);
            AdminTelaFun.setVisible(false);
            AdminTelaProd.setVisible(false);
            AdminTelaSettings.setVisible(false);
            AdminTelaQuarto.setVisible(false);
            Tela_despesa.setVisible(false);
            tela_relatorio.setVisible(false);
            telasVendasAtivar();

            Btn_Vendas.setStyle("-fx-background-color:  linear-gradient(to right, #0f2027, #203a43, #2c5364);" +
                    "-fx-background-radius: 10px");
            Btn_fun.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_produto.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_Quarto.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_Inicio.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_Setting.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            btn_despesa.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_relatorio.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");

        }
        else if(event.getSource() == btn_despesa ){
            dashboard_Inicio.setVisible(false);
            AdminTelaFun.setVisible(false);
            AdminTelaProd.setVisible(false);
            AdminTelaSettings.setVisible(false);
            AdminTelaQuarto.setVisible(false);
            Tela_despesa.setVisible(true);
            tela_relatorio.setVisible(false);
            telasVendasDesativar();
            adminDespesaService.formatado(Txt_nome_despesa,Data_despesa,Combobox_Tipo_Despesa,Txt_Valor_despesa);
            btn_despesa.setStyle("-fx-background-color:  linear-gradient(to right, #0f2027, #203a43, #2c5364);" +
                    "-fx-background-radius: 10px");
            Btn_fun.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_produto.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_Quarto.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_Inicio.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_Setting.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_Vendas.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_relatorio.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");

        }
        else if(event.getSource() == Btn_relatorio){
            dashboard_Inicio.setVisible(false);
            AdminTelaFun.setVisible(false);
            AdminTelaProd.setVisible(false);
            AdminTelaSettings.setVisible(false);
            AdminTelaQuarto.setVisible(false);
            Tela_despesa.setVisible(false);
            telasVendasDesativar();
            Combobox_Duracao_Rel.setValue("Selecione as opções");
            Combobox_Tipo_Rel.setValue("Selecione as opções");
            DesativarCompDTPersonalzidasRel();
            tela_relatorio.setVisible(true);
            Btn_relatorio.setStyle("-fx-background-color:  linear-gradient(to right, #0f2027, #203a43, #2c5364);" +
                    "-fx-background-radius: 10px");
            Btn_fun.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_produto.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_Quarto.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_Inicio.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_Setting.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            Btn_Vendas.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");
            btn_despesa.setStyle("-fx-background-color: transparent; -fx-background-radius: 0px;");

        }


    }
    void mask(){
        MaskFieldUtil.monetaryField(Valor_prod);
        MaskFieldUtil.monetaryField(txt_cada_hora);
        MaskFieldUtil.monetaryField(txt_uma_hora);
        MaskFieldUtil.monetaryField(txt_duas_hora);
        MaskFieldUtil.monetaryField(txt_pernoite_valor);
        MaskFieldUtil.hora(txt_pernoite_Incial);
        MaskFieldUtil.hora(txt_pernoite_fim);
        MaskFieldUtil.cpfCnpjField(Textfield_Cpf_funcionario);
        MaskFieldUtil.hora(txt_pernoite_meio);
        MaskFieldUtil.monetaryField(Txt_Valor_despesa);

    }

    public void inicial(AdminQuartoInterface adminquartoi, AdminProdutoInterface adminprodutoi, AdminFuncionarioInterface adminfuncionarioi, AdminConfigInterface adminconfigi, ConfigDao configDao,
    Quarto quarto, QuartoDao quartoDao, Produto produto, ProdutoDao produtoDao, Config config, Funcionario funcionario, FuncionarioDao funcionarioDao, VendaInterface vendaInterface, VendaInterface vendaInterfaceFun) {
        this.adminquartoi = adminquartoi;
        this.vendaInterfaceFun = vendaInterfaceFun;
        this.adminprodutoi = adminprodutoi;
        this.adminfuncionarioi = adminfuncionarioi;
        this.adminconfigi =  adminconfigi;
        this.configDao = configDao;
        this.quartodao = quartoDao;
        this.produto = produto;
        this.produtoDao = produtoDao;
        this.quarto = quarto;
        this.config = config;
        this.funcionario = funcionario;
        this.funcionarioDao = funcionarioDao;
        this.vendaInterface = vendaInterface;
        adminVendaPorProdutos.inicial(this);
        Lista_Nomes_Fun = funcionarioDao.Lista_Nome_Funcionario();
        Lista_Nomes_Fun.add(0,"Selecione o funcionário");
        CarregarComboboxTodas();
        CarregarTabelasTodas();
        CarregarDadosConfig();
        DesativarCompHboxPersonalzidasVenda_por_produtos();
        DesativarCompDTPersonalzidasRel();
        Carregardados_Tela_inicio();
        File file = new File("Fotos_Fun/");
        String Atp = file.getAbsolutePath();
        if(funcionario.getNome_Foto()!=null) {
            Image imagem = new Image(String.valueOf(new File(Atp + "/" + funcionario.getNome_Foto())));
            Foto_perfil.setImage(imagem);
            Nome_admin.setText(funcionario.getNome_Fun());
        }else {
            Image imagem = new Image("/Imagens/user.png");
            Foto_perfil.setImage(imagem);
        }
adminDespesaService.inicial(this);
    vendaInterface.inicial(this);
vendaInterfaceFun.inicial(this
);
    }
//  tela de inicio

    void Carregardados_Tela_inicio(){
        int QuartosDisponiveis = quartodao.QuartoDisponiveis();
        int QuartosOcupados = quartodao.QuartoOcupados();
        Quartos_disponiveis.setText(Integer.toString(QuartosDisponiveis));
        Quartos_Ocupados.setText(Integer.toString(QuartosOcupados));
        Double totalVendaHoje = vendaInterface.TotalVendadeHoje();
        Lbl_Total_Venda.setText(String.format("%.2f",totalVendaHoje));

    }
    @FXML
    void Sair(MouseEvent event){
        stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
        Pare = true;
        try {
            Stage stage = new Stage();
            HelloApplication main = new HelloApplication();
            FuncionarioDao funcionarioDao = new FuncionarioDao();
            LoginInterface loginInterface = new LoginService();
            FXMLLoader loader =  new FXMLLoader(getClass().getResource("/com/example/demo/Views/Login.fxml"));
            Parent root = loader.load();
            ControllerLogin controllerLogin = loader.getController();
            controllerLogin.inicial(main,funcionarioDao,loginInterface);
            Scene scene = new Scene(root);
            stage.setTitle("Sistema Do Motel");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    void DesativarLabelseDatepicker(){
        Label_De_Grafico_por_venda.setVisible(false);
        Label_De_Grafico_por_venda.managedProperty().bind(Label_De_Grafico_por_venda.visibleProperty());
        Label_Para_Grafico_por_venda.setVisible(false);
        Label_Para_Grafico_por_venda.managedProperty().bind(Label_Para_Grafico_por_venda.visibleProperty());
        Data_Personalizada_Inicio_Grafico_Venda_Por_Quarto.setVisible(false);
        Data_Personalizada_Inicio_Grafico_Venda_Por_Quarto.managedProperty().bind(Data_Personalizada_Inicio_Grafico_Venda_Por_Quarto.visibleProperty());
        Data_Personalizada_Fim_Grafico_Venda_Por_Quarto.setVisible(false);
        Data_Personalizada_Fim_Grafico_Venda_Por_Quarto.managedProperty().bind(Data_Personalizada_Fim_Grafico_Venda_Por_Quarto.visibleProperty());
    }
    void DesativarLabelseDatepickerFun(){
       Componente_De_Para.setVisible(false);
        Componente_De_Para.managedProperty().bind(Componente_De_Para.visibleProperty());
        Lbl_Total_Venda_Fun.setVisible(false);
        Lbl_Total_Venda_Fun.managedProperty().bind(Lbl_Total_Venda_Fun.visibleProperty());
        Valor_Total_Venda_Fun.setVisible(false);
        Valor_Total_Venda_Fun.managedProperty().bind(Valor_Total_Venda_Fun.visibleProperty());
    }
    public void DesativarLabelTotalVendaProduto(){
        Valor_Total_Venda_Produto1.setVisible(false);
        Valor_Total_Venda_Produto1.managedProperty().bind(Valor_Total_Venda_Produto1.visibleProperty());
        Lbl_Total_Venda_Prod1.setVisible(false);
        Lbl_Total_Venda_Prod1.managedProperty().bind(Lbl_Total_Venda_Prod1.visibleProperty());
    }
    void DesativarCompDTPersonalzidasRel(){
        Data_Final_Rel.setValue(null);
        Data_Inicial_Rel.setValue(null);
        DT_Inicial_DT_Final_HBox_RELATORIO.setVisible(false);
        DT_Inicial_DT_Final_HBox_RELATORIO.managedProperty().bind(DT_Inicial_DT_Final_HBox_RELATORIO.visibleProperty());
    }
    void DesativarCompDTPersonalzidasVenda_por_produtos(){
        DTP_Inicial_VPP.setValue(null);
        DTP_Fim_VPP.setValue(null);
        Hbox_produto_por_vend_datas.setVisible(false);
        Hbox_produto_por_vend_datas.managedProperty().bind(Hbox_produto_por_vend_datas.visibleProperty());
    }
    void DesativarCompHboxPersonalzidasVenda_por_produtos(){
        Hbox_error_Produto_label.setVisible(false);
        Hbox_error_Produto_label.managedProperty().bind( Hbox_error_Produto_label.visibleProperty());
    }
    void AtivarLabelseDatepicker(){
        Label_De_Grafico_por_venda.setVisible(true);
        Label_Para_Grafico_por_venda.setVisible(true);
        Data_Personalizada_Inicio_Grafico_Venda_Por_Quarto.setVisible(true);

        Data_Personalizada_Fim_Grafico_Venda_Por_Quarto.setVisible(true);

    }
    void HoraeDataAgora(){
        Thread thread = new Thread(()->{
            SimpleDateFormat FormatadoHora = new SimpleDateFormat("HH:mm");
            SimpleDateFormat FormatadoData = new SimpleDateFormat("dd/MM/yyyy");
            while (!Pare){
                try {
                    Thread.sleep(1000);
                }catch (Exception e){

                }
                final String HoraAgora = FormatadoHora.format(new Date());
                final String DataAgora = FormatadoData.format(new Date());
                Platform.runLater(()->{
                    Lbl_hora.setText(HoraAgora);
                    Lbl_Data.setText(DataAgora);

                });
            }


        });
        thread.start();
    }
    void CarregarTabelaVendadeHojeInicio() {
        ObservableList<Vendas> vendas = FXCollections.observableArrayList();
        Col_Num_quarto_Inicio.setCellValueFactory(new PropertyValueFactory<Vendas, String>("Num_quarto"));
        Col_Data_Hora_Entrada_Inicio.setCellValueFactory(new PropertyValueFactory<Vendas, String>("formatado_Data_Entrada"));
        Col_Data_Hora_Saida_Inicio.setCellValueFactory(new PropertyValueFactory<Vendas, String>("Formatado_Data_hora_Saida"));
        Col_Status_inicio.setCellValueFactory(new PropertyValueFactory<Vendas, String>("status"));
        vendas = vendaInterface.ListaVendadeHoje();
        Tabela_Venda_Hoje_Inicio.setItems(vendas);
    }
    void CarregarTabelaConsumodeHojeInicio() {
        ObservableList<Consumo> Consumo = FXCollections.observableArrayList();
        Col_Num_quarto_consumo_inicio.setCellValueFactory(new PropertyValueFactory<Consumo, String>("num_Quarto"));
        Col_Total_consumo_inicio.setCellValueFactory(new PropertyValueFactory<Consumo, String>("ExibirValor"));

        Consumo = vendaInterface.ListaConsumodeHoje();
        Tabela_Consumo_inicio.setItems(Consumo);
    }
    // fim da tela de inicio
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    DesativarLabelseDatepickerFun();
        DesativarLabelseDatepicker();
        DesativarLabelTotalVendaProduto();
        DesativarCompDTPersonalzidasVenda_por_produtos();

HoraeDataAgora();
        botoesPadraoQuarto();
    mask();

    }

    // Inicio do Tela do Quarto

    void limparcamposQuarto() {
        TextField_Num_quarto.setText("");
        Combox_Estado_quarto.setValue("Selecione o estado");
    }

    @FXML
    void Cadastrar_quarto(ActionEvent event) {

        quarto.setNum_quarto(TextField_Num_quarto.getText());
        quarto.setEstado(Combox_Estado_quarto.getValue());

        boolean verificarcampos = adminquartoi.VerificarCampos(quarto.getNum_quarto(), quarto.getEstado());
        boolean verificarnum = adminquartoi.VerificarNumero(quarto.getNum_quarto(), quartodao);
        int cod = adminquartoi.cod_quarto(quartodao);
        quarto.setCod_Quarto(cod);
        if (verificarcampos) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");

            alert.setHeaderText("Erro:");
            alert.setContentText("Preencham os campos");

            alert.showAndWait();
        } else if (verificarnum) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");

            alert.setHeaderText("Erro:");
            alert.setContentText("O numero do quarto " + quarto.getNum_quarto() + " já existe na tabela");
            alert.showAndWait();
        } else {
            quartodao.cadastrar(quarto);
            limparcamposQuarto();
            CarregarTabelaQuarto();
            ;
        }

    }

    void CarregarTabelaQuarto() {
        ObservableList<Quarto> quartos = FXCollections.observableArrayList();
        Coluna_Codigo_Quarto.setCellValueFactory(new PropertyValueFactory<Quarto, Integer>("cod_Quarto"));
        Coluna_Numero_Quarto.setCellValueFactory(new PropertyValueFactory<Quarto, String>("Num_quarto"));
        Coluna_Estado_Quarto.setCellValueFactory(new PropertyValueFactory<Quarto, String>("Estado"));
        quartos = quartodao.ListaQuarto();
        Tabela_Quarto.setItems(quartos);
    }

    @FXML
    void Pesquisar_Numero_quarto(KeyEvent event) {
        ObservableList<Quarto> quartos = FXCollections.observableArrayList();
        Coluna_Codigo_Quarto.setCellValueFactory(new PropertyValueFactory<Quarto, Integer>("cod_Quarto"));
        Coluna_Numero_Quarto.setCellValueFactory(new PropertyValueFactory<Quarto, String>("Num_quarto"));
        Coluna_Estado_Quarto.setCellValueFactory(new PropertyValueFactory<Quarto, String>("Estado"));
        quartos = quartodao.ConsultaPorNumerodoQuarto(TextField_pesquisar.getText());
        Tabela_Quarto.setItems(quartos);

    }

    @FXML
    void Clica_novo_cadastro(ActionEvent event) {
        TextField_Num_quarto.requestFocus();
        Checkbox_Novo_Cadastro_Quarto.setVisible(false);
        btn_cadastrar_quarto.setDisable(false);
        btn_limpar_tudo_quarto.setDisable(false);
        btn_editar_quarto.setDisable(true);
        btn_excluir_quarto.setDisable(true);
    }

    void botoesPadraoQuarto() {
        TextField_Num_quarto.setFocusTraversable(true);
        Checkbox_Novo_Cadastro_Quarto.setVisible(false);
        btn_cadastrar_quarto.setDisable(false);
        btn_limpar_tudo_quarto.setDisable(false);
        btn_editar_quarto.setDisable(true);
        btn_excluir_quarto.setDisable(true);
        Btn_Editar_despesa.setDisable(true);
        Btn_Excluir_despesa.setDisable(true);
    }

    @FXML
    void Tabela_Quarto_Selecionada(MouseEvent event) {
        int index = Tabela_Quarto.getSelectionModel().getSelectedIndex();
        if (index < -1) {

            return;
        }
        Checkbox_Novo_Cadastro_Quarto.setSelected(false);
        Checkbox_Novo_Cadastro_Quarto.setVisible(true);
        btn_cadastrar_quarto.setDisable(true);
        btn_limpar_tudo_quarto.setDisable(true);
        btn_editar_quarto.setDisable(false);
        btn_excluir_quarto.setDisable(false);
        quarto.setCod_Quarto(Coluna_Codigo_Quarto.getCellData(index));
        quarto.setNum_quarto(Coluna_Numero_Quarto.getCellData(index));
        quarto.setEstado(Coluna_Estado_Quarto.getCellData(index));
        TextField_Num_quarto.setText(quarto.getNum_quarto());
        Combox_Estado_quarto.setValue(quarto.getEstado());

    }

    @FXML
    void SetaTabelaQuartoSelecionada(KeyEvent event) {
        if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT
                || event.getCode() == KeyCode.UP) {
            int index = Tabela_Quarto.getSelectionModel().getSelectedIndex();
            if (index < -1) {
                return;
            }
            Checkbox_Novo_Cadastro_Quarto.setSelected(false);
            Checkbox_Novo_Cadastro_Quarto.setVisible(true);
            btn_cadastrar_quarto.setDisable(true);
            btn_limpar_tudo_quarto.setDisable(true);
            btn_editar_quarto.setDisable(false);
            btn_excluir_quarto.setDisable(false);
            quarto.setCod_Quarto(Coluna_Codigo_Quarto.getCellData(index));
            quarto.setNum_quarto(Coluna_Numero_Quarto.getCellData(index));
            quarto.setEstado(Coluna_Estado_Quarto.getCellData(index));
            TextField_Num_quarto.setText(quarto.getNum_quarto());
            Combox_Estado_quarto.setValue(quarto.getEstado());

        }
    }

    @FXML
    void Editar_Quarto(ActionEvent event) {
        boolean verificarcampos = adminquartoi.VerificarCampos(TextField_Num_quarto.getText(), Combox_Estado_quarto.getValue());
        if (verificarcampos) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");

            alert.setHeaderText("Erro:");
            alert.setContentText("Preencham os campos");

            alert.showAndWait();
        } else {

            quarto.setNum_quarto(TextField_Num_quarto.getText());
            quarto.setEstado(Combox_Estado_quarto.getValue());
            adminquartoi.Excluir_e_Editar(false, true, quarto, quartodao);
            CarregarTabelaQuarto();
            botoesPadraoQuarto();
            limparcamposQuarto();
            Carregardados_Tela_inicio();
        }

    }

    @FXML
    void Excluir_Quarto(ActionEvent event) {
        adminquartoi.Excluir_e_Editar(true, false, quarto, quartodao);
        CarregarTabelaQuarto();
        botoesPadraoQuarto();
        limparcamposQuarto();
    }

    @FXML
    void Limpar_tudo_Quarto(ActionEvent event) {
        adminquartoi.Excluir_Tudo(quartodao);
        CarregarTabelaQuarto();
    }

    // Fim do Tela do Quarto

// Inicio Tela do Produtos
    void limparcamposProdutos() {
        TextfliedEstoques_prod.setText("");
        Valor_prod.setText("");
        Image imagem = new Image("/Imagens/products.png");
        ImagemProduto.setImage(imagem);
        Combobox_tipo_produto.setValue("Selecione o tipo");
        TextfliedNome_prod.setText("");

    }

    @FXML
    void AbrirImagem(ActionEvent event) {
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");

        FileChooser fileChooser = new FileChooser();
        fileProduto = fileChooser.showOpenDialog(null);
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
        try {if (fileProduto != null){

            BufferedImage bufferedImage = ImageIO.read(fileProduto);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            ImagemProduto.setImage(image);

        }
        } catch (IOException e) {

        }


    }

    @FXML
    void CheckBox_Novo_Cadastro_Produto_verificar(ActionEvent event) {
        TextfliedNome_prod.requestFocus();
        CheckBox_Novo_Cadastro_Produto.setVisible(false);
        btn_cadastrar_produto.setDisable(false);
        btn_limpar_tudo_produto.setDisable(false);
        btn_editar_produto.setDisable(true);
        btn_excluir_produto.setDisable(true);
        limparcamposProdutos();


    }

    @FXML
    void Limpar_tudo_Produto(ActionEvent event) {
        adminprodutoi.Excluir_Tudo(produtoDao);
        CarregarTabelaProduto();
    }

    @FXML
    void Editar_Produto(ActionEvent event) {
        boolean VerficarCampo = adminprodutoi.VerificarCampos(TextfliedNome_prod.getText(), Combobox_tipo_produto.getValue(), Valor_prod.getText(),
                TextfliedEstoques_prod.getText());
        if (VerficarCampo) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");

            alert.setHeaderText("Erro:");
            alert.setContentText("Preencham os campos");
            alert.showAndWait();

        } else {
            produto.setNome_prod(TextfliedNome_prod.getText());
            produto.setValor(Float.valueOf(Valor_prod.getText().replaceAll(",", ".")));
            produto.setTipo_Produto(Combobox_tipo_produto.getValue());
            produto.setEstoques(Integer.parseInt(TextfliedEstoques_prod.getText()));
            adminprodutoi.Excluir_e_Editar(false, true, produtoDao, produto, fileProduto);
            CarregarTabelaProduto();
            limparcamposProdutos();
            fileProduto = null;

        }

    }

    @FXML
    void Excluir_Produto(ActionEvent event){


        adminprodutoi.Excluir_e_Editar(true, false, produtoDao, produto, fileProduto);
        CarregarTabelaProduto();
        limparcamposProdutos();


    }
    void SelProd_tabela(){
        int index = Tabela_Produto.getSelectionModel().getSelectedIndex();
        if (index < -1) {

        } else {
            CheckBox_Novo_Cadastro_Produto.setSelected(false);
            CheckBox_Novo_Cadastro_Produto.setVisible(true);
            btn_cadastrar_produto.setDisable(true);
            btn_limpar_tudo_produto.setDisable(true);
            btn_editar_produto.setDisable(false);
            btn_excluir_produto.setDisable(false);
            produto.setIdProduto(Coluna_Codigo_Produto.getCellData(index));
            produto.setNome_prod(Coluna_Nome_Produto.getCellData(index));
            produto.setTipo_Produto(Coluna_Tipo_Produto.getCellData(index));
            produto.setValor(Float.parseFloat(Coluna_Valor_Produto.getCellData(index).replace(",", ".")));
            produto.setEstoques(Coluna_Estoques_Produto.getCellData(index));
            produto.setFoto(Coluna_Foto_Produto.getCellData(index));
            TextfliedNome_prod.setText(produto.getNome_prod());
            Valor_prod.setText(String.format("%.2f", produto.getValor()).replace(".", ","));
            TextfliedEstoques_prod.setText(Integer.toString(produto.getEstoques()));

            if (produto.getFoto() != null) {

                File file = new File("Fotos_prod/");
                String Atp = file.getAbsolutePath();

                Image imagem = new Image(String.valueOf(new File(Atp+"/"+produto.getFoto())));
                ImagemProduto.setImage(imagem);


            } else {
                Image imagem = new Image("/Imagens/products.png");
                ImagemProduto.setImage(imagem);
            }

            Combobox_tipo_produto.setValue(produto.getTipo_Produto());
        }


    }


    @FXML
    void TabelaPRoduto_SelecioandaLinha(MouseEvent event) {
        SelProd_tabela();

            }

    @FXML
    void SetaTabelaProdutoSelecionada(KeyEvent event) {
        if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.UP) {
           SelProd_tabela();
    }}


    void CarregarTabelaProduto() {
    	produtoDao.foto();
        ObservableList<Produto> produtos = FXCollections.observableArrayList();
        Coluna_Codigo_Produto.setCellValueFactory(new PropertyValueFactory<Produto, String>("idProduto"));
        Coluna_Nome_Produto.setCellValueFactory(new PropertyValueFactory<Produto, String>("Nome_prod"));
        Coluna_Tipo_Produto.setCellValueFactory(new PropertyValueFactory<Produto, String>("Tipo_Produto"));
        Coluna_Valor_Produto.setCellValueFactory(new PropertyValueFactory<Produto, String>("ExibirValor"));
        Coluna_Estoques_Produto.setCellValueFactory(new PropertyValueFactory<Produto, Integer>("estoques"));
        Coluna_Foto_Produto.setCellValueFactory(new PropertyValueFactory<Produto, String>("Foto"));
        produtos = produtoDao.ListaProduto();
        Tabela_Produto.setItems(produtos);
    }

    @FXML
    void Cadastrar_Produto(ActionEvent event) {
        boolean VerficarCampo = adminprodutoi.VerificarCampos(TextfliedNome_prod.getText(), Combobox_tipo_produto.getValue(), Valor_prod.getText(),
                TextfliedEstoques_prod.getText());
        if (VerficarCampo) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");

            alert.setHeaderText("Erro:");
            alert.setContentText("Preencham os campos");
            alert.showAndWait();

        } else {
            String Cod_Produto = adminprodutoi.cod_Produto(Combobox_tipo_produto.getValue(), produtoDao);
            produto.setIdProduto(Cod_Produto);
            produto.setNome_prod(TextfliedNome_prod.getText());
            produto.setValor(Float.valueOf(Valor_prod.getText().replaceAll(",", ".")));
            produto.setTipo_Produto(Combobox_tipo_produto.getValue());
            produto.setEstoques(Integer.parseInt(TextfliedEstoques_prod.getText()));
            if (fileProduto != null) {
                produtoDao.cadastrar(produto, fileProduto);
                fileProduto = null;
                CarregarTabelaProduto();
                limparcamposProdutos();
            } else {
                produtoDao.cadastrar(produto);
                CarregarTabelaProduto();
                limparcamposProdutos();
            }


        }


    }

    @FXML
    void Pesquisar_Nome_ou_Codigo_Produto(KeyEvent event) {
        ObservableList<Produto> produtos = FXCollections.observableArrayList();
        produtos = produtoDao.ConsultaPorNomeOuCodigodoProduto(Pesquisar_Produto.getText());
        Tabela_Produto.setItems(produtos);

    }

    @FXML
    void Filtrar_a_Situacao_produto(ActionEvent event) {
        if (Combobox_Produto_Situacao.getValue().equals("Com estoques")) {
            ObservableList<Produto> produtos = FXCollections.observableArrayList();
            produtos = produtoDao.ConsultaPorComEstoques();
            Tabela_Produto.setItems(produtos);
            produtoDao.ConsultaPorComEstoques();
        } else if (Combobox_Produto_Situacao.getValue().equals("Sem estoques")) {
            ObservableList<Produto> produtos = FXCollections.observableArrayList();
            produtos = produtoDao.ConsultaPorSemEstoques();
            Tabela_Produto.setItems(produtos);

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");

            alert.setHeaderText("Erro:");
            alert.setContentText("Você não selecionou a situação que você quer filtrar");
            alert.showAndWait();
        }

    }


// fim tela do Produtos

    // inicio Tela do funcionario
    void limparcamposFuncionario() {
        Textfield_Nome_funcionario.requestFocus();
        Textfield_Nome_funcionario.setText("");
        Textfield_Senha_funcionario.setText("");
        Textfield_Cpf_funcionario.setText("");
        Image imagem = new Image("/Imagens/user.png");
        ImageFun.setImage(imagem);
        Combobox_Tipo_funcionario.setValue("Selecione o tipo");


    }

    @FXML
    void AbrirImagemFun(ActionEvent event) {
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");

        FileChooser fileChooser = new FileChooser();
        FileFuncionario = fileChooser.showOpenDialog(null);
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
        try {
            if (FileFuncionario != null) {
                BufferedImage bufferedImage = ImageIO.read(FileFuncionario);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                ImageFun.setImage(image);
            }
        } catch (IOException e) {

        }


    }
    void Tab_funcionario(){
        int index = Tabela_funcionario.getSelectionModel().getSelectedIndex();
        if (index < -1) {
            return;
        } else {
            CheckBox_Funcionario.setSelected(false);
            CheckBox_Funcionario.setVisible(true);
            btn_cadastrar_fun.setDisable(true);
            btn_Limpar_tudo_fun.setDisable(true);
            btn_Editar_fun.setDisable(false);
            btn_excluir_fun.setDisable(false);
            funcionario.setId_Fun(Coluna_Codigo_fun.getCellData(index));
            funcionario.setNome_Fun(Coluna_Nome_fun.getCellData(index));
            funcionario.setSenha_fun(Coluna_Senha_fun.getCellData(index));
            funcionario.setTipo_Fun(Coluna_Tipo_fun.getCellData(index));
            funcionario.setCpf_Fun(Coluna_Cpf_fun.getCellData(index));
            funcionario.setNome_Foto(Coluna_Foto_fun.getCellData(index));
            Textfield_Nome_funcionario.setText(funcionario.getNome_Fun());
            Textfield_Senha_funcionario.setText(funcionario.getSenha_fun());
            Textfield_Cpf_funcionario.setText(funcionario.getCpf_Fun());
            if (funcionario.getNome_Foto() != null) {

                File file = new File("Fotos_Fun");
                String Atp = file.getAbsolutePath();

                Image imagem = new Image(String.valueOf(new File(Atp+"/"+funcionario.getNome_Foto())));
               ImageFun.setImage(imagem);

            } else {
                Image imagem = new Image("/Imagens/user.png");
                ImageFun.setImage(imagem);
            }
Combobox_Tipo_funcionario.setValue(funcionario.getTipo_Fun());
        }

    }

    @FXML
    void TabelaFuncionarioSelecioandaLinha(MouseEvent event) {
       Tab_funcionario();

    }

    @FXML
    void SetaTabelaFuncionarioSelecionada(KeyEvent event) {
        if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT
                || event.getCode() == KeyCode.UP) {
            Tab_funcionario();
        }
    }


    void CarregarTabelaFuncionario() {
        ObservableList<Funcionario> funcionarios = FXCollections.observableArrayList();
        Coluna_Codigo_fun.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("Id_Fun"));
        Coluna_Nome_fun.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("Nome_Fun"));
        Coluna_Senha_fun.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("Senha_fun"));
        Coluna_Tipo_fun.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("Tipo_Fun"));
        Coluna_Cpf_fun.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("Cpf_Fun"));
        Coluna_Foto_fun.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("Nome_Foto"));
        funcionarios = funcionarioDao.ListaFuncionario();
        Tabela_funcionario.setItems(funcionarios);

    }
    @FXML
    void Cadastrar_Funcionario(ActionEvent event) {
        boolean VerficarCampo = adminfuncionarioi.VerificarCampos(Textfield_Nome_funcionario.getText(),
                Combobox_Tipo_funcionario.getValue(), Textfield_Senha_funcionario.getText(),Textfield_Cpf_funcionario.getText());

        if (VerficarCampo) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");

            alert.setHeaderText("Erro:");
            alert.setContentText("Preencham os campos");
            alert.showAndWait();

        } else {
            String Cod_funcionario = adminfuncionarioi.CodFuncionario(funcionarioDao);
            funcionario.setId_Fun(Cod_funcionario);
            funcionario.setNome_Fun(Textfield_Nome_funcionario.getText());
            funcionario.setSenha_fun(Textfield_Senha_funcionario.getText());
            funcionario.setTipo_Fun(Combobox_Tipo_funcionario.getValue());
            funcionario.setCpf_Fun(Textfield_Cpf_funcionario.getText());
            if (FileFuncionario != null) {
                funcionarioDao.cadastrar(funcionario,FileFuncionario);
                FileFuncionario = null;
                CarregarTabelaFuncionario();
             limparcamposFuncionario();
            } else {
                funcionarioDao.cadastrar(funcionario);
                CarregarTabelaFuncionario();
                limparcamposProdutos();
            }


        }


    }
    @FXML
    void Editar_Funcionario(ActionEvent event) {
        boolean VerficarCampo = adminfuncionarioi.VerificarCampos(Textfield_Nome_funcionario.getText(),
                Combobox_Tipo_funcionario.getValue(), Textfield_Senha_funcionario.getText(), Textfield_Cpf_funcionario.getText());

        if (VerficarCampo) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");

            alert.setHeaderText("Erro:");
            alert.setContentText("Preencham os campos");
            alert.showAndWait();

        } else {

            funcionario.setNome_Fun(Textfield_Nome_funcionario.getText());
            funcionario.setSenha_fun(Textfield_Senha_funcionario.getText());
            funcionario.setTipo_Fun(Combobox_Tipo_funcionario.getValue());
            funcionario.setCpf_Fun(Textfield_Cpf_funcionario.getText());
            adminfuncionarioi.Excluir_e_Editar(false, true, funcionarioDao, funcionario, FileFuncionario);
            limparcamposFuncionario();
            CarregarTabelaFuncionario();

        }

    }
    @FXML
    void Excluir_Funcionario(ActionEvent event) {

            adminfuncionarioi.Excluir_e_Editar(true, false, funcionarioDao, funcionario, FileFuncionario);
            limparcamposFuncionario();
            CarregarTabelaFuncionario();

        }
        @FXML
    void Excluir_Tudo_Funcionario(ActionEvent event) {

        adminfuncionarioi.Excluir_Tudo(funcionarioDao);
        limparcamposFuncionario();
        CarregarTabelaFuncionario();

    }


    @FXML
    void CheckBox_Novo_Cadastro_Funcionario_verificar(ActionEvent event) {
        Textfield_Nome_funcionario.requestFocus();
        CheckBox_Funcionario.setVisible(false);
        btn_cadastrar_fun.setDisable(false);
        btn_Limpar_tudo_fun.setDisable(false);
        btn_Editar_fun.setDisable(true);
        btn_excluir_fun.setDisable(true);
        limparcamposFuncionario();


    }
    @FXML
    void Pesquisar_Nome_Fun(KeyEvent event) {
        ObservableList<Funcionario> funcionarios = FXCollections.observableArrayList();
        funcionarios = funcionarioDao.ConsultaPorNomeOuCodigodoFuncionario(Textfield_Pesquisar_funcionario.getText());
        Tabela_funcionario.setItems(funcionarios);

    }
        // fim da tela do funcionario

        // inicio da tela do config
        void CarregarDadosConfig(){

        boolean vefiricar = adminconfigi.carregardados(configDao);
            ObservableList<Config> configs = FXCollections.observableArrayList();
            configs = configDao.ListaConfig();
        if(vefiricar){
        for (Config c : configs){
            System.out.println(c.getPrimeiraHora_valor());
        lbl_valor_uma_hora.setText(String.format("%.2f", c.getPrimeiraHora_valor()).replace(".",","));
        lbl_valor_duas_hora.setText(String.format("%.2f", c.getSegundaHora_valor()).replace(".",","));
        lbl_valor_cada_hora.setText(String.format("%.2f", c.getValor_cada_hora()).replace(".",","));
        lbl_valor_pernoite_inicio.setText(c.getPernoite_inicio());
        lbl_valor_pernoite_fim.setText(c.getPernoite_fim());
        lbl_valor_pernoite_meio.setText(c.getPernoite_meio());
        lbl_valor_pernoite_valor.setText(String.format("%.2f", c.getPernoite_valor()).replace(".",","));
            }

        }
    }
    @FXML
    void Config_salvo(ActionEvent event) {

        boolean vefiricar = adminconfigi.carregardados(configDao);
        boolean verificarcampos = adminconfigi.VerificarCampos(txt_cada_hora.getText(),txt_duas_hora.getText(),
                txt_cada_hora.getText(), txt_pernoite_Incial.getText(), txt_pernoite_meio.getText(),txt_pernoite_fim.getText(),
                txt_pernoite_valor.getText());
        if (verificarcampos) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");

            alert.setHeaderText("Erro:");
            alert.setContentText("Preencham os campos");
            alert.showAndWait();

        } else {

            config.setPrimeiraHora_valor(Double.parseDouble(txt_uma_hora.getText().replace(",",".")));
            config.setSegundaHora_valor(Double.parseDouble(txt_duas_hora.getText().replace(",",".")));
            config.setValor_cada_hora(Double.parseDouble(txt_cada_hora.getText().replace(",",".")));
            config.setPernoite_valor(Double.parseDouble(txt_pernoite_valor.getText().replace(",",".")));
            config.setPernoite_inicio(txt_pernoite_Incial.getText());
            config.setPernoite_meio(txt_pernoite_meio.getText());
            config.setPernoite_fim(txt_pernoite_fim.getText());

            if(vefiricar){
                configDao.editar(config);
                CarregarDadosConfig();

            }else{
                configDao.cadastrar(config);
                CarregarDadosConfig();
            }

        }


    }

    // Fim da tela do config
    // inicio da tela de venda de menu
    public void AbrirTelaDeVendaPorQuarto(){
        CarregarTabelaVenda_Por_Quarto();
        CarregarLabelVendaPorQuarto();
        Tela_Venda_Menu.setVisible(false);
      Tela_Venda_Por_Quarto.setVisible(true);
        TelaVendaporQuarto.setVisible(true);

    }
    public void AbrirTelaDeVendaPorFun(){
        DesativarLabelseDatepickerFun();
        vendaInterfaceFun.LimparGrafico();
        ComboBox_N1_grafico_venda_Por_Fun.setValue("Selecione o funcionário");
        Clica_N1_de_novo();
        Total_Nome_Fun1.setText("Total da Fulano 1:");
        Total_Nome_Fun2.setText("Total da Fulano 2:");
        Total_Valor_Fun2.setText("0,00");
        Total_Valor_Fun1.setText("0,00");
        Valor_diferencas_Fun.setText("0,00");
        Ganhador_Pesquisar_fun.setText("Fulano");
        Tela_Venda_Menu.setVisible(false);
        Tela_Venda_Fun.setVisible(true);

    }
    public void AbrirTelaDeVendaPorProd(){

        ComboBox_Qtd_produto_por_venda.setValue("Selecione as opções");
        ComboBox_Filtrar_produto_por_venda.setValue("Selecione as opções");
        adminVendaPorProdutos.LimparGrafico();
        DesativarCompDTPersonalzidasVenda_por_produtos();
        DesativarCompHboxPersonalzidasVenda_por_produtos();
        Ganhador_Pesquisar_Produto.setText("Produto");
        Valor_Total_Venda_Produto.setText("0,00");
        Tela_Venda_Menu.setVisible(false);
        Tela_Venda_Prod.setVisible(true);

    }

    public void Menu_Voltar(){
        Tela_Venda_Menu.setVisible(true);
        Tela_Venda_Por_Quarto.setVisible(false);
        TelaVendaporQuarto.setVisible(false);
        Tela_Venda_Fun.setVisible(false);
        Tela_Venda_Prod.setVisible(false);
    }
    public void Menu_Voltar_Fun(){
        Menu_Voltar();
        DesativarLabelseDatepickerFun();
        vendaInterfaceFun.LimparGrafico();
        ComboBox_N1_grafico_venda_Por_Fun.setValue("Selecione o funcionário");
        Clica_N1_de_novo();
        Total_Nome_Fun1.setText("Total da Fulano 1:");
        Total_Nome_Fun2.setText("Total da Fulano 2:");
        Total_Valor_Fun2.setText("0,00");
        Total_Valor_Fun1.setText("0,00");
        Valor_diferencas_Fun.setText("0,00");
        Ganhador_Pesquisar_fun.setText("Fulano");

    }
    public void Menu_Voltar_Tela_Venda_por_quarto(){
        CarregarTabelaVenda_Por_Quarto();
        CarregarLabelVendaPorQuarto();
        TelaVendaporQuarto.setVisible(true);
        TelaGraficoVendaPorQuarto.setVisible(false);
        vendaInterface.LimparGrafico();
    }

    public void Clica_grafico_venda_por_quarto(){
        ComboBox_grafico_venda_Por_Quarto.setValue("Selecione as opções");
        DesativarLabelseDatepicker();
        TelaVendaporQuarto.setVisible(false);
        TelaGraficoVendaPorQuarto.setVisible(true);
    }


    // fim da tela de venda de menu
    // inicio da tela de venda por quarto

    void CarregarTabelaVenda_Por_Quarto() {
        ObservableList<Vendas> venda_por_quarto = FXCollections.observableArrayList();
        Col_Codigo_Venda_Por_Quarto.setCellValueFactory(new PropertyValueFactory<Vendas, String>("Cod_venda"));
        Col_Nome_Fun_Venda_Por_Quarto.setCellValueFactory(new PropertyValueFactory<Vendas, String>("Nome_fun"));
        Col_Num_Venda_Por_Quarto.setCellValueFactory(new PropertyValueFactory<Vendas, String>("Num_quarto"));
        Col_Data_Hora_Entrada_Venda_Por_Quarto.setCellValueFactory(new PropertyValueFactory<Vendas, String>("formatado_Data_Entrada"));
        Col_Data_Hora_Saida_Venda_Por_Quarto.setCellValueFactory(new PropertyValueFactory<Vendas, String>("Formatado_Data_hora_Saida"));
        Col_Total_Venda_Por_Quarto.setCellValueFactory(new PropertyValueFactory<Vendas, String>("totalV"));
        Col_Status_Venda_Por_Quarto.setCellValueFactory(new PropertyValueFactory<Vendas, String>("status"));
        venda_por_quarto = vendaInterface.ListaVendadeHojePorQuarto();
        Tabela_Venda_Por_Quarto.setItems(venda_por_quarto);
        CarregarLabelVendaPorQuarto();

    }
    void PTabelaVenda_Por_Quarto(LocalDate Date) {

        btn_editar_venda.setDisable(true);
        btn_excluir_venda.setDisable(true);
        ObservableList<Vendas> venda_por_quarto = FXCollections.observableArrayList();
        venda_por_quarto.clear();
        venda_por_quarto = vendaInterface.ListaVendaporPesquisar(Date);
        Tabela_Venda_Por_Quarto.setItems(venda_por_quarto);
        CarregarLabelVendaPorQuarto();

    }
   public void excluir_venda(){
       Object[] opc = {"Sim","Não"};

       int resultDialog =  JOptionPane.showOptionDialog(null,"Você quer excluir essa venda: "+vendas.getCod_venda()+" ?","Pergunta",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,opc,opc[0]);
       if(resultDialog == JOptionPane.YES_OPTION) {
           consumoDao.ExcluirVenda(vendas);
           vendaInterface.excluir(vendas);
           vendas.setEstado("Disponível");
           quartodao.Editar(vendas);
           LocalDate date = LocalDate.of(vendas.getData_hora_Entrada().getYear(),vendas.getData_hora_Entrada().getMonth(),vendas.getData_hora_Entrada().getDayOfMonth());
           Pesquisarpordata(date);
       }

   }
    void CarregarLabelVendaPorQuarto(){
        double valorTotal = vendaInterface.TotalVendadeHoje();
        Lbl_Total_venda_por_quarto.setText(String.format("%.2f", valorTotal));
        double valorTotaldeConsumo = vendaInterface.TotalVendadeConsumo();
        Lbl_Total_venda_Consumo_por_quarto.setText(String.format("%.2f", valorTotaldeConsumo));
        int entrada= vendaInterface.TotalVendadeEntrada() + vendaInterface.TotalVendadeSaida();
        int saida = vendaInterface.TotalVendadeSaida();
        Lbl_Total_venda_Entrada_por_quarto.setText("" + entrada);
        Lbl_Total_venda_Saida_por_quarto.setText("" + saida);



    }
    public void Pesquisarpordata(LocalDate date){
        PTabelaVenda_Por_Quarto(date);

        double valorTotal = vendaInterface.TotalVendadePesquisar(date);
        Lbl_Total_venda_por_quarto.setText(String.format("%.2f", valorTotal));
        double valorTotaldeConsumo = vendaInterface.TotalVendadeConsumo(date);
        Lbl_Total_venda_Consumo_por_quarto.setText(String.format("%.2f", valorTotaldeConsumo));
        int entrada= vendaInterface.TotalVendadeEntradaPorPesquisar(date) + vendaInterface.TotalVendadeSaidaPorPesquisar(date);
        int saida = vendaInterface.TotalVendadeSaidaPorPesquisar(date);
        Lbl_Total_venda_Entrada_por_quarto.setText("" + entrada);
        Lbl_Total_venda_Saida_por_quarto.setText("" + saida);
        Lbl_txt_Total_Entrada_Venda_por_Quarto.setText("Total de entrada:");
        Lbl_txt_Total_Saida_Venda_por_Quarto.setText("Total de saída:");
        Por_hora_saida.setVisible(false);


    }
    public void Pesquisarpordata(){
        LocalDate date = TXT_DATEPICKER.getValue();
        PTabelaVenda_Por_Quarto(date);

        double valorTotal = vendaInterface.TotalVendadePesquisar(date);
        Lbl_Total_venda_por_quarto.setText(String.format("%.2f", valorTotal));
        double valorTotaldeConsumo = vendaInterface.TotalVendadeConsumo(date);
        Lbl_Total_venda_Consumo_por_quarto.setText(String.format("%.2f", valorTotaldeConsumo));
        int entrada= vendaInterface.TotalVendadeEntradaPorPesquisar(date) + vendaInterface.TotalVendadeSaidaPorPesquisar(date);
        int saida = vendaInterface.TotalVendadeSaidaPorPesquisar(date);
        Lbl_Total_venda_Entrada_por_quarto.setText("" + entrada);
        Lbl_Total_venda_Saida_por_quarto.setText("" + saida);
        Lbl_txt_Total_Entrada_Venda_por_Quarto.setText("Total de entrada:");
        Lbl_txt_Total_Saida_Venda_por_Quarto.setText("Total de saída:");
        Por_hora_saida.setVisible(false);


    }
    public void PesquisarpordataAtualizado(LocalDate dt_entrada, Vendas vendas,double totalConsumo){
        LocalDate date = dt_entrada;
        PTabelaVenda_Por_Quarto(date);


        Lbl_Total_venda_por_quarto.setText(String.format("%.2f", vendas.getTotal()));

        Lbl_Total_venda_Consumo_por_quarto.setText(String.format("%.2f", totalConsumo));
        Lbl_Total_venda_Entrada_por_quarto.setText(vendas.getFormatado_Data_Entrada());
        Lbl_Total_venda_Saida_por_quarto.setText( vendas.getFormatado_Data_hora_Saida());
        CarregarTabelaQuarto();

        Por_hora_saida.setVisible(false);


    }

    void Tab_Venda_Por_Quarto(){
        ObservableList<Consumo> ListaConsumo = FXCollections.observableArrayList();
        int index = Tabela_Venda_Por_Quarto.getSelectionModel().getSelectedIndex();

        int hora = 0;
        if (index < -1 || Tabela_Venda_Por_Quarto.getSelectionModel().isEmpty()) {
            btn_editar_venda.setDisable(true);
            btn_excluir_venda.setDisable(true);

                    } else {
            btn_editar_venda.setDisable(false);
            btn_excluir_venda.setDisable(false);

            vendas.setCod_venda(Col_Codigo_Venda_Por_Quarto.getCellData(index));
if (Col_Data_Hora_Entrada_Venda_Por_Quarto.getCellData(index).equals("") || Col_Data_Hora_Entrada_Venda_Por_Quarto.getCellData(index).isEmpty() || Col_Data_Hora_Entrada_Venda_Por_Quarto.getCellData(index).isBlank()) {

}else{
    vendas.setData_hora_Entrada(LocalDateTime.parse(Col_Data_Hora_Entrada_Venda_Por_Quarto.getCellData(index), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
}
            if (Col_Data_Hora_Saida_Venda_Por_Quarto.getCellData(index).equals("") || Col_Data_Hora_Saida_Venda_Por_Quarto.getCellData(index).isEmpty() || Col_Data_Hora_Saida_Venda_Por_Quarto.getCellData(index).isBlank() ){
                vendas.setData_hora_Saida(null);
            }else {
                vendas.setData_hora_Saida(LocalDateTime.parse(Col_Data_Hora_Saida_Venda_Por_Quarto.getCellData(index), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                 hora = vendas.getData_hora_Entrada().getHour();
            }
            vendas.setCod_venda(Col_Codigo_Venda_Por_Quarto.getCellData(index));
            vendas.setStatus(Col_Status_Venda_Por_Quarto.getCellData(index));
            vendas.setNome_fun(Col_Nome_Fun_Venda_Por_Quarto.getCellData(index));
            vendas.setTotalV(Col_Total_Venda_Por_Quarto.getCellData(index));
            vendas.setNum_quarto(Col_Num_Venda_Por_Quarto.getCellData(index));
            vendas.setFormatado_Data_Entrada(Col_Data_Hora_Entrada_Venda_Por_Quarto.getCellData(index));
            vendas.setFormatado_Data_hora_Saida(Col_Data_Hora_Saida_Venda_Por_Quarto.getCellData(index));
            Lbl_txt_Total_Entrada_Venda_por_Quarto.setText("Data e hora da entrada:");
            Lbl_txt_Total_Saida_Venda_por_Quarto.setText("Data e hora da saída:");
            Lbl_Total_venda_Entrada_por_quarto.setText(Col_Data_Hora_Entrada_Venda_Por_Quarto.getCellData(index));
            if (Col_Data_Hora_Saida_Venda_Por_Quarto.getCellData(index).equals("")|| Col_Data_Hora_Saida_Venda_Por_Quarto.getCellData(index).isBlank()|| Col_Data_Hora_Saida_Venda_Por_Quarto.getCellData(index).isEmpty()){
                Lbl_Total_venda_Saida_por_quarto.setText("O cliente não saiu ainda...");
            }else{
                Lbl_Total_venda_Saida_por_quarto.setText(Col_Data_Hora_Saida_Venda_Por_Quarto.getCellData(index));
            }

            Lbl_Total_venda_por_quarto.setText(vendas.getTotalV());
            configDao.ListaConfig(config);
            LocalTime configHoraFim = LocalTime.parse(config.getPernoite_fim());
            LocalTime configHoraInicio = LocalTime.parse(config.getPernoite_inicio());

            if(hora > configHoraFim.getHour() & configHoraInicio.getHour() > hora){
                Por_hora_saida.setVisible(true);
                Por_hora_saida.setText("Por: hora");
            }else {
                Por_hora_saida.setVisible(true);
                Por_hora_saida.setText("Por: pernoite");
            }
            Col_Nome_Consumo_Venda_Por_Quarto.setCellValueFactory(new PropertyValueFactory<Consumo,String>("Nome_prod"));
            Col_Valor_Consumo_Venda_Por_Quarto.setCellValueFactory(new PropertyValueFactory<Consumo,String>("ExibirValor"));
            Col_QTD_Consumo_Venda_Por_Quarto.setCellValueFactory(new PropertyValueFactory<Consumo,Integer>("Qtd"));
            ListaConsumo = consumoDao.ListaConsumo(vendas);
            Tabela_Consumo_Venda_Por_Quarto.setItems(ListaConsumo);
            double ResultadoTotal =  ListaConsumo.stream().mapToDouble(C -> Double.parseDouble(C.getExibirValor().replace(",",".")) * C.getQtd()).sum();
            Lbl_Total_venda_Consumo_por_quarto.setText(String.format("%.2f", ResultadoTotal));






        }}
    @FXML
    void SelTab(){
        Tab_Venda_Por_Quarto();
    }

    @FXML
    void editar_venda(ActionEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader =  new FXMLLoader(getClass().getResource("/com/example/demo/Views/Editar_vendas.fxml"));
            Parent root = loader.load();
            controllerEditar_venda= loader.getController();
            controllerEditar_venda.inicial(quartodao,quarto,vendas,funcionarioVendaInterface,funcionario,config,configDao,vendaInterface,this);
            Scene scene = new Scene(root);
            stage.setTitle("Sistema Do Motel");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btn_editar_venda.getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void SetSelTab(KeyEvent event){
        if  (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.UP) {
            Tab_Venda_Por_Quarto();
        }
    }


    // fim da tela de venda por quarto

    // inicio da tela do grafico de venda por quarto
    public void ComboBox_Grafico_Venda(ActionEvent event){
        switch (ComboBox_grafico_venda_Por_Quarto.getValue()){
            case "Por 7 dias":
                Combobox_graficoVendaPorQuarto = "7";
                DesativarLabelseDatepicker();
                Label_erro_Grafico_venda_por_quarto.setText("");

                break;
            case "Por 15 dias":
                Combobox_graficoVendaPorQuarto = "15";
                DesativarLabelseDatepicker();
                Label_erro_Grafico_venda_por_quarto.setText("");
                break;
            case "Por 30 dias":
                Combobox_graficoVendaPorQuarto = "30";
                DesativarLabelseDatepicker();
                Label_erro_Grafico_venda_por_quarto.setText("");
                break;
            case "Por datas personalizadas":
                Combobox_graficoVendaPorQuarto = "Por datas personalizadas";
                AtivarLabelseDatepicker();
                Label_erro_Grafico_venda_por_quarto.setText("");
                break;
        }
    }

    public  void limparLabelErroGrafico(){
        Label_erro_Grafico_venda_por_quarto.setText("");
    }
public void buscarVendaGrafico(){
        if(ComboBox_grafico_venda_Por_Quarto.getValue().equals("Selecione as opções")){
        Label_erro_Grafico_venda_por_quarto.setText("Por favor, selecione as opções para filtrar");
        }else if (ComboBox_grafico_venda_Por_Quarto.getValue().equals("Por datas personalizadas")&  Data_Personalizada_Inicio_Grafico_Venda_Por_Quarto.getValue() == null){
            Label_erro_Grafico_venda_por_quarto.setText("Por favor, preencha o inicio da data");
        }else if (ComboBox_grafico_venda_Por_Quarto.getValue().equals("Por datas personalizadas")&  Data_Personalizada_Fim_Grafico_Venda_Por_Quarto.getValue() == null){
            Label_erro_Grafico_venda_por_quarto.setText("Por favor, preencha o fim da data");
        }else if (ComboBox_grafico_venda_Por_Quarto.getValue().equals("Por datas personalizadas")&  Data_Personalizada_Inicio_Grafico_Venda_Por_Quarto.getValue() !=null & Data_Personalizada_Fim_Grafico_Venda_Por_Quarto.getValue() !=null){
            System.out.println(Data_Personalizada_Inicio_Grafico_Venda_Por_Quarto.getValue());
            System.out.println(Data_Personalizada_Fim_Grafico_Venda_Por_Quarto.getValue());
            if (Data_Personalizada_Fim_Grafico_Venda_Por_Quarto.getValue().isBefore(Data_Personalizada_Inicio_Grafico_Venda_Por_Quarto.getValue())) {
                Label_erro_Grafico_venda_por_quarto.setText("Você colocou a data do inicio depois do fim da data");
            }else {
            vendaInterface.Filtrar(Combobox_graficoVendaPorQuarto);
            }
        }else{
            vendaInterface.Filtrar(Combobox_graficoVendaPorQuarto);
        }
}

    //fim da tela do grafico de venda por quarto
    // inicio da tela do grafico de venda por Funcionario
    public void buscarVendaGraficoFun(){
        if(ComboBox_N1_grafico_venda_Por_Fun.getValue().equals("Selecione o funcionário")){
            Label_erro_Grafico_venda_por_Fun.setText("Por favor, selecione o funcionário 1");
        }else if (ComboBox_N2_grafico_venda_Por_Fun.getValue().equals("Selecione o funcionário")){
            Label_erro_Grafico_venda_por_Fun.setText("Por favor, selecione o funcionário 2");
        }else if (ComboBox_Filtrar_grafico_venda_Por_Fun.getValue().equals("Selecione as opções")){
            Label_erro_Grafico_venda_por_Fun.setText("Por favor, selecione as opções para filtrar");
        }
        else if (ComboBox_grafico_venda_Por_Quarto.getValue().equals("Por datas personalizadas")& Data_Personalizada_Inicio_Grafico_Venda_Por_Fun.getValue() == null){
            Label_erro_Grafico_venda_por_Fun.setText("Por favor, preencha o inicio da data");
        }else if (ComboBox_grafico_venda_Por_Quarto.getValue().equals("Por datas personalizadas")&  Data_Personalizada_Fim_Grafico_Venda_Por_Fun.getValue() == null){
            Label_erro_Grafico_venda_por_Fun.setText("Por favor, preencha o fim da data");
        }else if (ComboBox_grafico_venda_Por_Quarto.getValue().equals("Por datas personalizadas")&  Data_Personalizada_Inicio_Grafico_Venda_Por_Fun.getValue() !=null & Data_Personalizada_Fim_Grafico_Venda_Por_Fun.getValue() !=null){

            if (Data_Personalizada_Fim_Grafico_Venda_Por_Fun.getValue().isBefore(Data_Personalizada_Inicio_Grafico_Venda_Por_Fun.getValue())) {
                Label_erro_Grafico_venda_por_Fun.setText("Você colocou a data do inicio depois do fim da data");
            }else {
                vendaInterfaceFun.Filtrar(Combobox_fil_graficoVendaporfun);
            }
        }else{
            vendaInterfaceFun.Filtrar(Combobox_fil_graficoVendaporfun);
        }
    }
    public void Clica_N1_de_novo(){
        Label_erro_Grafico_venda_por_Fun.setText("");
        if(ComboBox_N2_grafico_venda_Por_Fun.getItems().isEmpty() || ComboBox_N2_grafico_venda_Por_Fun.getItems() == null){
            ComboBox_N2_grafico_venda_Por_Fun.setDisable(true);
        }if(ComboBox_Filtrar_grafico_venda_Por_Fun.getItems().isEmpty() || ComboBox_Filtrar_grafico_venda_Por_Fun.getItems() == null){
            ComboBox_Filtrar_grafico_venda_Por_Fun.setDisable(true);
        }if (!ComboBox_N2_grafico_venda_Por_Fun.getItems().isEmpty() || ComboBox_N2_grafico_venda_Por_Fun.getItems() != null){
            ComboBox_N2_grafico_venda_Por_Fun.setDisable(true);
            ComboBox_N2_grafico_venda_Por_Fun.getItems().clear();
        }if (!ComboBox_Filtrar_grafico_venda_Por_Fun.getItems().isEmpty() || ComboBox_Filtrar_grafico_venda_Por_Fun.getItems() != null){
            ComboBox_Filtrar_grafico_venda_Por_Fun.setDisable(true);
            ComboBox_Filtrar_grafico_venda_Por_Fun.getItems().clear();
        }
    }
    public void Clica_N2_de_novo(){
        Label_erro_Grafico_venda_por_Fun.setText("");
        ComboBox_N2_grafico_venda_Por_Fun.setValue("Selecione o funcionário");
        if(ComboBox_Filtrar_grafico_venda_Por_Fun.getItems().isEmpty() || ComboBox_Filtrar_grafico_venda_Por_Fun.getItems() == null){
            ComboBox_Filtrar_grafico_venda_Por_Fun.setDisable(true);
        }else {
            ComboBox_Filtrar_grafico_venda_Por_Fun.setDisable(true);
            ComboBox_Filtrar_grafico_venda_Por_Fun.getItems().clear();
        }
    }
    public void ComboBox_Fil_Grafico_Venda_Fun(){
        if (!ComboBox_Filtrar_grafico_venda_Por_Fun.isDisabled()) {
            switch (ComboBox_Filtrar_grafico_venda_Por_Fun.getValue()) {
                case "Por 7 dias":
                    Combobox_fil_graficoVendaporfun = "7";
                    Label_erro_Grafico_venda_por_Fun.setText("");
                    DesativarLabelseDatepickerFun();


                    break;
                case "Por 15 dias":
                    Combobox_fil_graficoVendaporfun = "15";
                    Label_erro_Grafico_venda_por_Fun.setText("");
                    DesativarLabelseDatepickerFun();

                    break;
                case "Por 30 dias":
                    Combobox_fil_graficoVendaporfun = "30";
                    Label_erro_Grafico_venda_por_Fun.setText("");
                    DesativarLabelseDatepickerFun();

                    break;
                case "Por datas personalizadas":
                    Combobox_fil_graficoVendaporfun = "Por datas personalizadas";
                    Label_erro_Grafico_venda_por_Fun.setText("");
                    Componente_De_Para.setVisible(true);
                    break;
            }
        }
    }
    public void Sel_N1(){
        if(ComboBox_N1_grafico_venda_Por_Fun.getValue().equals("Selecione o funcionário")){
        ComboBox_N2_grafico_venda_Por_Fun.setDisable(true);
        }else {

            Lista_n2.clear();
            Lista_n2.addAll(Lista_Nomes_Fun);
            Lista_n2.remove(ComboBox_N1_grafico_venda_Por_Fun.getValue());
            ComboBox_N2_grafico_venda_Por_Fun.setItems(Lista_n2);
            ComboBox_N2_grafico_venda_Por_Fun.setValue("Selecione o funcionário");
            ComboBox_N2_grafico_venda_Por_Fun.setDisable(false);
        }

    }
    public void Sel_N2(){

        if(ComboBox_N2_grafico_venda_Por_Fun.getValue().equals("Selecione o funcionário")){
            ComboBox_Filtrar_grafico_venda_Por_Fun.setDisable(true);
        }else {
            ComboBox_Filtrar_grafico_venda_Por_Fun.setItems(FXCollections.observableArrayList("Selecione as opções", "Por 30 dias", "Por 15 dias","Por 7 dias","Por datas personalizadas"));
            ComboBox_Filtrar_grafico_venda_Por_Fun.setValue("Selecione as opções");
            ComboBox_Filtrar_grafico_venda_Por_Fun.setDisable(false);
        }

    }

    //fim da tela do grafico de venda por Funcionario
    //inicio da tela do grafico de venda por produtos
    public  int limite;
    public LocalDate inicialDT_PRODUTOS;
    public LocalDate Fim_PRODUTOS;
    public void limparLAbeLErrorhboxVendaporprodutos(){
        DesativarCompHboxPersonalzidasVenda_por_produtos();
    }
    public void Menu_Voltar_prod(){
        Menu_Voltar();
        ComboBox_Qtd_produto_por_venda.setValue("Selecione as opções");
        ComboBox_Filtrar_produto_por_venda.setValue("Selecione as opções");
        adminVendaPorProdutos.LimparGrafico();
        DesativarCompDTPersonalzidasVenda_por_produtos();
        DesativarCompHboxPersonalzidasVenda_por_produtos();
        Ganhador_Pesquisar_Produto.setText("Produto");
        Valor_Total_Venda_Produto.setText("0,00");
    }

    public void Fil_venda_Por_produtos(){
        if(ComboBox_Filtrar_produto_por_venda.getValue() != "Por datas personalizadas" ){
           if (ComboBox_Filtrar_produto_por_venda.getValue() != "Selecione as opções" && ComboBox_Qtd_produto_por_venda.getValue() != "Selecione as opções"){
            adminVendaPorProdutos.Filtrar(inicialDT_PRODUTOS,Fim_PRODUTOS,limite, Ganhador_Pesquisar_Produto,Valor_Total_Venda_Produto);
           }else {
               Hbox_error_Produto_label.setVisible(true);
               error_venda_por_produto.setText("Preencham os campos!");
           }
        }else{
        if(DTP_Inicial_VPP.getValue() != null && DTP_Fim_VPP.getValue() != null ){
            if (DTP_Inicial_VPP.getValue().isAfter(DTP_Fim_VPP.getValue())){
                Hbox_error_Produto_label.setVisible(true);
                error_venda_por_produto.setText("Data Inicial é depois da Data Final");
            }else if (DTP_Inicial_VPP.getValue().isEqual(DTP_Fim_VPP.getValue())){
                Hbox_error_Produto_label.setVisible(true);
                error_venda_por_produto.setText("Data Inicial é igual da Data Final");
            }else {
                adminVendaPorProdutos.Filtrar(DTP_Inicial_VPP.getValue(),DTP_Fim_VPP.getValue(),limite,Ganhador_Pesquisar_Produto,Valor_Total_Venda_Produto);
            }
        }else{
            Hbox_error_Produto_label.setVisible(true);
            error_venda_por_produto.setText("Preencham os campos!");
        }
        }

    }

    public void ComboBox_Grafico_Filtrar_Venda_Produto(ActionEvent event){
        switch (ComboBox_Filtrar_produto_por_venda.getValue()){
            case "Por 7 dias":
                Fim_PRODUTOS = LocalDate.now();
                inicialDT_PRODUTOS = Fim_PRODUTOS.minusDays(7);
                DesativarCompDTPersonalzidasVenda_por_produtos();
                DesativarCompHboxPersonalzidasVenda_por_produtos();
                error_venda_por_produto.setText("");


                break;
            case "Por 15 dias":
                Fim_PRODUTOS = LocalDate.now();
                inicialDT_PRODUTOS = Fim_PRODUTOS.minusDays(15);
                DesativarCompDTPersonalzidasVenda_por_produtos();
                DesativarCompHboxPersonalzidasVenda_por_produtos();
                error_venda_por_produto.setText("");
                break;
            case "Por 30 dias":
                Fim_PRODUTOS = LocalDate.now();
                inicialDT_PRODUTOS = Fim_PRODUTOS.minusDays(30);
                DesativarCompDTPersonalzidasVenda_por_produtos();
                DesativarCompHboxPersonalzidasVenda_por_produtos();
                error_venda_por_produto.setText("");
                break;
            case "Por datas personalizadas":

               Hbox_produto_por_vend_datas.setVisible(true);
              error_venda_por_produto.setText("");
                break;
        }
    }

    public void ComboBox_GraficoQtd_Venda_Produto(ActionEvent event){
        switch (ComboBox_Qtd_produto_por_venda.getValue()){
            case "Por 10 produtos":
                limite = 10;
                DesativarCompHboxPersonalzidasVenda_por_produtos();
                break;
            case "Por 20 produtos":
                limite = 20;
                DesativarCompHboxPersonalzidasVenda_por_produtos();
                break;
            case "Por 30 produtos":
                limite = 30;
                DesativarCompHboxPersonalzidasVenda_por_produtos();
                break;
            case "Por 40 produtos":
                limite = 40;
                DesativarCompHboxPersonalzidasVenda_por_produtos();
                break;
            case "Por 50 produtos":
                DesativarCompHboxPersonalzidasVenda_por_produtos();
                limite = 50;
                break;
        }
    }
    //fim da tela do grafico de venda por produtos
    //inicio da tela da despesa


    @FXML
    void Cadastrar_despesa(){
        adminDespesaService.cadastrar(Txt_nome_despesa,Data_despesa,Combobox_Tipo_Despesa,Txt_Valor_despesa);
    }
    @FXML
    void clica_despesa_tab(){
     adminDespesaService.tab_Sel(Txt_nome_despesa,Data_despesa,Combobox_Tipo_Despesa,Txt_Valor_despesa);
    }
    @FXML
    void Check_clica_despesa_tab(){
        adminDespesaService.clica_check(Txt_nome_despesa,Data_despesa,Combobox_Tipo_Despesa,Txt_Valor_despesa);
    }
    @FXML
    void excluir_despesa(){
        adminDespesaService.excluir();
    }
    @FXML
    void editar_despesa(){
        adminDespesaService.Editar(Txt_nome_despesa,Data_despesa,Combobox_Tipo_Despesa,Txt_Valor_despesa);
    }


    //fim da tela da despesa

    // inicio de relatorio
    public void ComboBox_Fil_relatorio_tipo(){
        if(Combobox_Duracao_Rel.getValue() != "Por datas personalizadas" ){
            Combobox_fil_Dur_Rel = Combobox_Duracao_Rel.getValue();
            Erro_Rel.setText("");
            DesativarCompDTPersonalzidasRel();
        }else{
            Combobox_fil_Dur_Rel = Combobox_Duracao_Rel.getValue();
            Erro_Rel.setText("");
            DT_Inicial_DT_Final_HBox_RELATORIO.setVisible(true);
        }

    }
    public void Filtrar_Rel() {
        if (Combobox_Duracao_Rel.getValue() == "Selecione as opções" || Combobox_Tipo_Rel.getValue() == "Selecione as opções") {
            Erro_Rel.setText("Preencham os campos");
        }else {
            Erro_Rel.setText("");
      rel();
    }}
    void rel(){

        if(Combobox_Duracao_Rel.getValue() != "Por datas personalizadas" ) {
            try {
                relatorio.Relatorios(Combobox_Tipo_Rel.getValue(),Combobox_Duracao_Rel.getValue(),null,null);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
            else{
            try {
                relatorio.Relatorios(Combobox_Tipo_Rel.getValue(),Combobox_Duracao_Rel.getValue(),Data_Inicial_Rel.getValue(),Data_Final_Rel.getValue());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

    }
    }
