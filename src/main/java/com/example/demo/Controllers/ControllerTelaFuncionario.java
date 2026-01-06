package com.example.demo.Controllers;

import com.example.demo.HelloApplication;
import com.example.demo.Models.Classes.*;
import com.example.demo.Models.Dao.ConfigDao;
import com.example.demo.Models.Dao.FuncionarioDao;
import com.example.demo.Models.Dao.QuartoDao;
import com.example.demo.Models.Interfaces.FuncionarioVendaInterface;
import com.example.demo.Models.Interfaces.LoginInterface;
import com.example.demo.Models.Services.LoginService;
import com.example.demo.Util.MaskFieldUtil;
import io.github.gleidson28.GNAvatarView;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class ControllerTelaFuncionario extends MaskFieldUtil implements Initializable {

    @FXML
    public TableColumn<Consumo, String> Coluna_Cod_Produto;

    @FXML
    public TableColumn<Consumo, String> Coluna_Nome_Produto_venda;

    @FXML
    public TableColumn<Consumo, String> Coluna_Valor_Produto_venda;
    @FXML
    public TableColumn<Consumo, Integer> Coluna_QTS_Produto_Venda;

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
    private CheckBox Checkbox_voltar;

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
    @FXML
    public Button BotaoAcao;
    @FXML
    private TextField Txt_CPF;
    @FXML
    private PasswordField Nova_Senha;
    @FXML
    private PasswordField Nova_senha_novamente;
    Stage stage;
    @FXML
    private Label Btn_perfil;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private GNAvatarView Foto_perfil;
    @FXML
    private GNAvatarView Imagem_perfil;
    @FXML
    private Label Nome_fun_perfil;
    private Quarto quarto;
    private QuartoDao quartoDao;
    private FuncionarioDao funcionarioDao;
    ConsumoController consumoController = new ConsumoController();
    public String id_produto, Nome_Produto;
    int qtd_produto;
    File filePerfil = null;
    boolean Pare = false;

    public void inicial(QuartoDao quartoDao, Quarto quarto, Vendas vendas, FuncionarioVendaInterface funcionarioVendai, FuncionarioDao funcionarioDao,
                        Funcionario funcionario, Config config, ConfigDao configDao) {
        this.quartoDao = quartoDao;
        this.quarto = quarto;
        this.vendas = vendas;
        this.funcionarioDao = funcionarioDao;
        this.funcionarioVendai = funcionarioVendai;
        this.funcionario = funcionario;
        funcionarioVendai.atualizado(Grid, quartoDao, quarto, vendas, funcionario);
        funcionarioVendai.Lbl(this, config, configDao);
        System.out.println(funcionario.getNome_Foto());
        if (funcionario.getNome_Foto().equals(null) || funcionario.getNome_Foto().isBlank() | funcionario.getNome_Foto().isEmpty()) {
            Image imagem = new Image("/Imagens/user.png");
            Foto_perfil.setImage(imagem);
        } else {
            Path path = Paths.get("Fotos_Fun/" + funcionario.getNome_Foto());
            Image imagem = new Image(String.valueOf(new File(path.toString())));
            Foto_perfil.setImage(imagem);
            Imagem_perfil.setImage(imagem);

        }
        Nome_fun_perfil.setText(funcionario.getNome_Fun());
        Txt_CPF.setText(funcionario.getCpf_Fun());
    }

    public void TrocarFormulario(MouseEvent event) {
        if (event.getSource() == Btn_inicio) {
            Form_inicio.setVisible(true);
            Tela_Perfil.setVisible(false);
            Btn_inicio.setStyle("-fx-background-color:  linear-gradient(to right, #0f2027, #203a43, #2c5364);" +
                    "-fx-background-radius: 10px");
            Btn_perfil.setStyle("-fx-background-color: transparent;" +
                    "-fx-background-radius: 0px;");
            if (funcionario.getNome_Foto().equals(null) || funcionario.getNome_Foto().isBlank() | funcionario.getNome_Foto().isEmpty()) {
                Image imagem = new Image("/Imagens/user.png");
                Foto_perfil.setImage(imagem);
            } else {
                File pasta = new File("Fotos_Fun/");
                String caminho = pasta.getAbsolutePath();
                Image imagem = new Image(String.valueOf(new File(caminho + "\\" + funcionario.getNome_Foto())));
                Imagem_perfil.setImage(imagem);
            }
            Txt_CPF.setText(funcionario.getCpf_Fun());
            Nova_Senha.setText("");
            Nova_senha_novamente.setText("");
        } else if (event.getSource() == Btn_perfil) {
            Form_inicio.setVisible(false);
            Tela_Perfil.setVisible(true);
            Btn_perfil.setStyle("-fx-background-color:linear-gradient(to right, #0f2027, #203a43, #2c5364);" +
                    "-fx-background-radius: 10px");
            Btn_inicio.setStyle("-fx-background-color:transparent;" +
                    "-fx-background-radius: 0px;");
        }
    }

    @FXML
    void add_consumo(ActionEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/Views/Tela_Consumo.fxml"));
            Parent root = loader.load();
            consumoController = loader.getController();
            consumoController.setControllerTelaFuncionario(this, funcionarioVendai);
            consumoController.ativarBotaoAdicionar();
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

    @FXML
    void Editar_consumo(ActionEvent event) {
        if (id_produto != null) {
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/Views/Tela_Consumo.fxml"));
                Parent root = loader.load();
                consumoController = loader.getController();
                consumoController.setControllerTelaFuncionario(this, funcionarioVendai);
                consumoController.ativarBotaoEditar();
                consumoController.Editar_Selecionada_Produto(id_produto, Nome_Produto, qtd_produto);
                Scene scene = new Scene(root);
                stage.setTitle("Sistema Do Motel");
                stage.setScene(scene);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(btn_add_consumo.getScene().getWindow());
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro:");
            alert.setContentText("Por favor, selecione a linha da tabela");
            alert.showAndWait();
        }
    }


    @FXML
    void Clicado_X_Venda(MouseEvent event) {
        funcionarioVendai.pararThread();
        lbl_Datatime_Saida.setText("DD/mm/yyyy HH:MM");
        lbl_Datatime_entrada.setText("DD/mm/yyyy HH:MM");
        lbl_Nome_fun_venda.setText("Nome");
        lbl_Duracao_Venda.setText("HH:MM");
        BotaoAcao.setText("Finalizar");
        BotaoAcao.setStyle("-fx-background-color:  linear-gradient(to left, #6441a5, #2a0845);");
        lbl_Num_quarto.setText("00");
        lbl_Total_Venda.setText("0,00");
        lbl_Datatime_Saida.setText("DD/mm/yyyy HH:MM");
        funcionarioVendai.limparTabela();
        pane_Lista_do_Quarto.setDisable(false);
        pane_Detalhes_Venda.setDisable(true);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pane_Detalhes_Venda.setDisable(true);
        HoraeDataAgora();
        MaskFieldUtil.cpfCnpjField(Txt_CPF);

    }

    void HoraeDataAgora() {
        Thread thread = new Thread(() -> {
            SimpleDateFormat FormatadoHora = new SimpleDateFormat("HH:mm");
            SimpleDateFormat FormatadoData = new SimpleDateFormat("dd/MM/yyyy");
            while (!Pare) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {

                }
                final String HoraAgora = FormatadoHora.format(new Date());
                final String DataAgora = FormatadoData.format(new Date());
                Platform.runLater(() -> {
                    lbl_Hora.setText(HoraAgora);
                    lbl_Data.setText(DataAgora);

                });
            }


        });
        thread.start();
    }

    public void parar() {
        Pare = true;
        funcionarioVendai.pararThread();
    }

    @FXML
    void Checkbox_voltar(ActionEvent event) {
        id_produto = "";
        Nome_Produto = "";
        qtd_produto = 0;
        Checkbox_voltar.setSelected(false);
        btn_add_consumo.setVisible(true);
        btn_Editar_consumo.setVisible(false);
        Checkbox_voltar.setVisible(false);
        Tabela_Consumo_venda.getSelectionModel().clearSelection();
    }

    @FXML
    void Excluir(ActionEvent event) {

        if (id_produto != null) {
            funcionarioVendai.excluir_consumo(id_produto, qtd_produto);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro:");
            alert.setContentText("Por favor, selecione a linha da tabela");
            alert.showAndWait();
        }
    }

    void sele_tab() {
        int index = Tabela_Consumo_venda.getSelectionModel().getSelectedIndex();
        if (index < -1 || Tabela_Consumo_venda.getSelectionModel().isEmpty()) {
            id_produto = null;
            Nome_Produto = null;
            qtd_produto = 0;

        } else {
            id_produto = Coluna_Cod_Produto.getCellData(index);
            Nome_Produto = Coluna_Nome_Produto_venda.getCellData(index);
            qtd_produto = Coluna_QTS_Produto_Venda.getCellData(index);
            btn_add_consumo.setVisible(false);
            btn_Editar_consumo.setVisible(true);
            Checkbox_voltar.setVisible(true);

        }
    }

    @FXML
    void SelecioandaLinhaSETA(KeyEvent event) {
        if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
            sele_tab();
        }
    }

    @FXML
    void SelecionadaTabela(MouseEvent event) {
        sele_tab();
    }

    @FXML
    void Finalizar(MouseEvent event) {

        if (BotaoAcao.getText().equals("Cancelar")) {
            Object[] opc = {"Sim", "Não"};
            int resultDialog = JOptionPane.showOptionDialog(null, "Você deseja cancelar a venda ?", "Pergunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opc, opc[0]);
            if (resultDialog == JOptionPane.YES_OPTION) {
                funcionarioVendai.Cancelado();
                lbl_Datatime_entrada.setText("DD/mm/yyyy HH:MM");
                lbl_Num_quarto.setText("00");
                lbl_Nome_fun_venda.setText("Nome");
                funcionarioVendai.limparTabela();
                BotaoAcao.setText("Finalizar");
                BotaoAcao.setStyle("-fx-background-color:  linear-gradient(to left, #6441a5, #2a0845);");
                pane_Lista_do_Quarto.setDisable(false);
                pane_Detalhes_Venda.setDisable(true);
            }
        } else {
            Object[] opc = {"Sim", "Não"};
            int resultDialog = JOptionPane.showOptionDialog(null, "Você deseja finalizar a venda ?", "Pergunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opc, opc[0]);
            if (resultDialog == JOptionPane.YES_OPTION) {

            if (funcionarioVendai.finalizar()){
                lbl_Datatime_Saida.setText("DD/mm/yyyy HH:MM");
                lbl_Datatime_entrada.setText("DD/mm/yyyy HH:MM");
                lbl_Nome_fun_venda.setText("Nome");
                lbl_Duracao_Venda.setText("HH:MM");
                lbl_Num_quarto.setText("00");
                lbl_Total_Venda.setText("0,00");
                funcionarioVendai.limparTabela();
                pane_Lista_do_Quarto.setDisable(false);
                pane_Detalhes_Venda.setDisable(true);}
            }
        }
        ;
    }

    @FXML
    void Sair(MouseEvent event) {
        funcionarioVendai.pararThread();
        stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
        try {
            Stage stage = new Stage();
            HelloApplication main = new HelloApplication();
            FuncionarioDao funcionarioDao = new FuncionarioDao();
            LoginInterface loginInterface = new LoginService();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/Views/Login.fxml"));
            Parent root = loader.load();
            ControllerLogin controllerLogin = loader.getController();
            controllerLogin.inicial(main, funcionarioDao, loginInterface);
            Scene scene = new Scene(root);
            stage.setTitle("Sistema Do Motel");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void AbrirImagem(ActionEvent event) {
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");

        FileChooser fileChooser = new FileChooser();
        filePerfil = fileChooser.showOpenDialog(null);
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
        try {
            if (filePerfil != null) {

                BufferedImage bufferedImage = ImageIO.read(filePerfil);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                Imagem_perfil.setImage(image);

            }
        } catch (IOException e) {

        }


    }

    @FXML
    void salvar(ActionEvent event) {
        if (Txt_CPF.getText().length() == 14) {
            if (filePerfil == null){
                if (Nova_Senha.getText().equals("")) {
                    funcionarioVendai.Editar(funcionarioDao, funcionario, filePerfil, Txt_CPF, Nova_Senha);
                }
                else {
                    if(Nova_Senha.getText().equals(Nova_senha_novamente.getText())){
                        funcionarioVendai.Editar(funcionarioDao, funcionario, filePerfil, Txt_CPF, Nova_Senha);
                    }else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erro");
                        alert.setHeaderText("Erro:");
                        alert.setContentText("As senhas não são iguais, digite novamente");
                        alert.showAndWait();
                        Nova_Senha.setText("");
                        Nova_senha_novamente.setText("");
                    }
                }
        } else {
                if (Nova_Senha.getText().equals("")) {
                    try {
                        BufferedImage bufferedImage = ImageIO.read(filePerfil);
                        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                        Foto_perfil.setImage(image);
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null, "erro: " + e);
                    }
                    funcionarioVendai.Editar(funcionarioDao, funcionario, filePerfil, Txt_CPF, Nova_Senha);
                    filePerfil = null;
                }else{
                    if(Nova_Senha.getText().equals(Nova_senha_novamente.getText())){
                        try {
                            BufferedImage bufferedImage = ImageIO.read(filePerfil);
                            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                            Foto_perfil.setImage(image);
                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(null, "erro: " + e);
                        }
                        funcionarioVendai.Editar(funcionarioDao, funcionario, filePerfil, Txt_CPF, Nova_Senha);
                    }else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erro");
                        alert.setHeaderText("Erro:");
                        alert.setContentText("As senhas não são iguais, digite novamente");
                        alert.showAndWait();
                        Nova_Senha.setText("");
                        Nova_senha_novamente.setText("");
                    }
                }
        }
    }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro:");
            alert.setContentText("Por favor, preencha seu cpf corretamente");
            alert.showAndWait();
        }
}


}