package com.example.demo.Controllers;

import com.example.demo.Models.Classes.Produto;
import com.example.demo.Models.Dao.ProdutoDao;
import com.example.demo.Models.Interfaces.FuncionarioVendaInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.util.OptionalInt;
import java.util.ResourceBundle;

public class Consumo_VendaController implements Initializable {
    @FXML
    private TextField Txt_qts;
    @FXML
    private ImageView ImagemFoto;
    @FXML
    private VBox View_Tabela_consumo_editar;

    @FXML
    private TextField BuscarProduto;

    @FXML
    private AnchorPane View_Foto;
    @FXML
    private Button btn_add;
    @FXML
    private TableView<Produto> Tabela_consumo;

    @FXML
    private TableColumn<Produto,String> coluna_Cod_Produto;

    @FXML
    private TableColumn<Produto, String> coluna_Nome_Produto;

    @FXML
    private TableColumn<Produto, Integer> coluna_QTS_Produto;

    @FXML
    private TableColumn<Produto, String> coluna_Valor_Produto;
    @FXML
      private Button btn_editar;
    @FXML
    private TableColumn<Produto, String> coluna_foto_Produto;

    @FXML
    private TableView<Produto> Tabela_consumo_Editar;

    @FXML
    private TableColumn<Produto, String> coluna_Valor_Produto_Editar;
    @FXML
    private TableColumn<Produto, String> coluna_Nome_Produto_Editar;
    @FXML
    private TableColumn<Produto, Integer> coluna_QTS_Produto_Editar;

String Id_produto,Nome_produto;
int qtd_produto;
ProdutoDao produtoDao = new ProdutoDao();
Produto produto = new Produto();
private FuncionarioVendaInterface funcionarioVendaInterface;
    private ControllerTelaFuncionario controllerTelaFuncionario;
    ObservableList<Produto> produtos = FXCollections.observableArrayList();
    void CarregarTabelaProduto() {
        produtos.clear();
        coluna_Cod_Produto.setCellValueFactory(new PropertyValueFactory<Produto, String>("idProduto"));
        coluna_Nome_Produto.setCellValueFactory(new PropertyValueFactory<Produto, String>("Nome_prod"));
        coluna_Valor_Produto.setCellValueFactory(new PropertyValueFactory<Produto, String>("ExibirValor"));
        coluna_QTS_Produto.setCellValueFactory(new PropertyValueFactory<Produto, Integer>("estoques"));
        coluna_foto_Produto.setCellValueFactory(new PropertyValueFactory<Produto, String>("Foto"));
        produtos = produtoDao.ConsultaPorComEstoques();
        Tabela_consumo.setItems(produtos);
    }void CarregarTabelaProdutoEditar(String Nome_Produto) {
        produtos.clear();
        coluna_Cod_Produto.setCellValueFactory(new PropertyValueFactory<Produto, String>("idProduto"));
        coluna_Nome_Produto_Editar.setCellValueFactory(new PropertyValueFactory<Produto, String>("Nome_prod"));
        coluna_Valor_Produto_Editar.setCellValueFactory(new PropertyValueFactory<Produto, String>("ExibirValor"));
        coluna_QTS_Produto_Editar.setCellValueFactory(new PropertyValueFactory<Produto, Integer>("estoques"));

        produtos = produtoDao.ConsultaPorNomeOuCodigodoProduto(Nome_Produto);
        Tabela_consumo_Editar.setItems(produtos);
    }
    public void Editar_Selecionada_Produto(String Id_produto, String Nome_produto,int qtd_produto){
        this.Id_produto = Id_produto;
        this.Nome_produto = Nome_produto;
        this.qtd_produto = qtd_produto;
        BuscarProduto.setText(Nome_produto);
        BuscarProduto.setEditable(false);
        CarregarTabelaProdutoEditar(Id_produto);
        Txt_qts.setText(Integer.toString(qtd_produto));
    }

    public void setControllerTelaFuncionario( FuncionarioVendaInterface funcionarioVendaInterface){
        this.funcionarioVendaInterface = funcionarioVendaInterface;
    }
    @FXML
    void TabelaPRoduto_SelecioandaLinha(MouseEvent event) {
        int index = Tabela_consumo.getSelectionModel().getSelectedIndex();
        if (index < -1) {
            return;
        } else {
            produto.setIdProduto(coluna_Cod_Produto.getCellData(index));
            produto.setEstoques(coluna_QTS_Produto.getCellData(index));
            produto.setFoto(coluna_foto_Produto.getCellData(index));

            if (produto.getFoto() != null) {

                Image imagem = new Image( produto.getFoto());
                ImagemFoto.setImage(imagem);
            } else {
                Image imagem = new Image("/Imagens/products.png");
                ImagemFoto.setImage(imagem);
            }

        }
    }

    public void ativarBotaoEditar(){
        btn_editar.setVisible(true);
        btn_add.setVisible(false);
        View_Foto.setVisible(false);
        Tabela_consumo.setVisible(false);
        View_Tabela_consumo_editar.setVisible(true);
        btn_add.managedProperty().bind(btn_add.visibleProperty());
        View_Foto.managedProperty().bind(View_Foto.visibleProperty());
        Tabela_consumo.managedProperty().bind(Tabela_consumo.visibleProperty());
    }
    public void ativarBotaoAdicionar(){
        View_Foto.setVisible(true);
        Tabela_consumo.setVisible(true);
        View_Tabela_consumo_editar.setVisible(false);
        BuscarProduto.setText("");
        BuscarProduto.setEditable(true);
        btn_add.setVisible(true);
        btn_editar.setVisible(false);
        btn_editar.managedProperty().bind(btn_editar.visibleProperty());
        View_Tabela_consumo_editar.managedProperty().bind(View_Tabela_consumo_editar.visibleProperty());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    CarregarTabelaProduto();

    }
    @FXML
    void add_qts(MouseEvent event) {
        int qts = Integer.parseInt(Txt_qts.getText());
        int result = qts + 1;
        Txt_qts.setText(String.valueOf(result));
    }

    @FXML
    void Editar_consumo(ActionEvent event) {
        int qtd_Editado = Integer.parseInt(Txt_qts.getText());
        OptionalInt estoques = produtos.stream().mapToInt(P -> P.getEstoques()).findFirst();
        int Estoques = estoques.getAsInt();
        if (qtd_Editado == 0){
            JOptionPane.showMessageDialog(null,"Selecione a quantidade do produto");
        }
        else if(qtd_Editado > qtd_produto && Estoques >= qtd_Editado){
            int res = qtd_Editado - qtd_produto;
            int totaleq = Estoques - res;
            funcionarioVendaInterface.Editar_consumo(Id_produto,qtd_Editado,totaleq);
            qtd_produto = qtd_Editado;
            CarregarTabelaProdutoEditar(Id_produto);

        }else if(qtd_produto>qtd_Editado && Estoques >= qtd_Editado){
            int res = qtd_produto - qtd_Editado;
            int totaleq = Estoques + res;
            funcionarioVendaInterface.Editar_consumo(Id_produto,qtd_Editado,totaleq);
            qtd_produto = qtd_Editado;
            CarregarTabelaProdutoEditar(Id_produto);
        }else if( qtd_Editado > Estoques ){
            JOptionPane.showMessageDialog(null,"O Produto não tem suficiente no estoques");
        }
    }
    @FXML
    void meno_qts(MouseEvent event) {
        int qts = Integer.parseInt(Txt_qts.getText());
        if (qts != 0){

            int result = qts - 1;
            Txt_qts.setText(String.valueOf(result));

        }

    }

    @FXML
    void Add_consumo(ActionEvent event) {

        int qts = Integer.parseInt(Txt_qts.getText());
        System.out.println(produto.getEstoques()+"\n"+qts+"");
        if (qts != 0 && produto.getIdProduto() != null && produto.getEstoques() >= qts ){
            funcionarioVendaInterface.Adicionar_Consumo(produto.getIdProduto(),qts,produto.getEstoques());
            produto.setIdProduto(null);
            Txt_qts.setText("0");
            CarregarTabelaProduto();


        }else if (produto.getIdProduto() == null && qts == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");

            alert.setHeaderText("Erro:");
            alert.setContentText("Por favor, selecione a linha da tabela e adicione a quantidade do produto ");
            alert.showAndWait();
        } else if (produto.getIdProduto() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");

            alert.setHeaderText("Erro:");
            alert.setContentText("Por favor, selecione a linha da tabela ");
            alert.showAndWait();

        } else if (qts==0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");

            alert.setHeaderText("Erro:");
            alert.setContentText("Por favor, adicione a quantidade do produto ");
            alert.showAndWait();
        } else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");

            alert.setHeaderText("Erro:");
            alert.setContentText("O número de quantidades que você adicionou foi maior do que o estoque tem ");
            alert.showAndWait();
        }
    }



}
