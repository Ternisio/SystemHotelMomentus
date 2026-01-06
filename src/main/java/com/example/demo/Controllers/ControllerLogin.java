package com.example.demo.Controllers;

import com.example.demo.HelloApplication;
import com.example.demo.Models.Classes.Funcionario;
import com.example.demo.Models.Dao.FuncionarioDao;
import com.example.demo.Models.Interfaces.LoginInterface;
import com.example.demo.Models.Services.LoginService;
import com.example.demo.Util.MaskFieldUtil;
import io.github.gleidson28.GNAvatarView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class ControllerLogin extends MaskFieldUtil implements Initializable {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private GNAvatarView Foto;
    @FXML
    public Label Lbl_error;
    @FXML
    public Label Lbl_error_Senha;
    @FXML
    public Label Lbl_error_cpf;
private LoginInterface loginInterface;
    @FXML
    private TextField txt_nome;
    @FXML
    private TextField txt_CPF;
    @FXML
    private VBox Tela_CPF;
    @FXML
    private VBox Tela_Fazer_login;
    @FXML
    private VBox Tela_Nova_senha;
    private HelloApplication main;

    
    @FXML
    private PasswordField Senha;

    @FXML
    private PasswordField Nova_senha;

    @FXML
    private PasswordField Nova_senha_novamente;
    private FuncionarioDao funcionarioDao;
    Funcionario funcionario = new Funcionario();
    ObservableList<Funcionario> lista = FXCollections.observableArrayList();
    Stage stage;
    public void inicial(HelloApplication main, FuncionarioDao funcionarioDao, LoginInterface loginInterface){
        this.main = main;
        this.funcionarioDao = funcionarioDao;
        this.loginInterface = loginInterface;
        loginInterface.inicial(this);
       lista = this.funcionarioDao.ListaFuncionario();
    }
void validar(){
    boolean validacao = loginInterface.ValidationLogin(txt_nome,Senha,funcionarioDao,funcionario);
    if (validacao){
        try {
            stage = (Stage) anchorPane.getScene().getWindow();
            stage.close();
            main.trocartela(funcionario.getTipo_Fun(),funcionario);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }else{
        Lbl_error.setText("O nome e a senha estão incorretas");
    }
}
void validarCPF(){
    boolean verificandoCPF = funcionarioDao.verificar(txt_CPF);
    if(!txt_CPF.getText().equals("")) {
        if(txt_CPF.getText().length() == 14){
        if (verificandoCPF) {
            Tela_CPF.setVisible(false);
            Tela_Nova_senha.setVisible(true);
        } else {
            Lbl_error_cpf.setText("O seu CPF está incorreto");
        }}else{
            Lbl_error_cpf.setText("Você digitou o seu cpf imcompleto");
        }
    }else{
        Lbl_error_cpf.setText("Preencha o campo");
    }
    }
    void validaraNovaSenha(){
        if (!Nova_senha.getText().equals("") && !Nova_senha_novamente.getText().equals("")) {
            if (Nova_senha.getText().equals(Nova_senha_novamente.getText())) {
                funcionarioDao.Editar(txt_CPF, Nova_senha);
            } else {
                Lbl_error_Senha.setText("As senhas não estão iguais");
            }
        }else {
            Lbl_error_Senha.setText("Preencham os campos");
        }
    }
    public void ButtonLogin(ActionEvent event){
    validar();
    }
    @FXML
    void Entrar_login(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            validar();
        }}
    @FXML
    void apertar_alterarasenha(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            validaraNovaSenha();
        }}
    @FXML
    void apertar_verificarCPF(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            validarCPF();
        }}
    @FXML
    void MostrandoAfoto(KeyEvent event) {
        Lbl_error.setText("");
Path Pasta = Paths.get("Fotos_Fun");
    String foto =  lista.stream() .filter(f -> f.getNome_Fun() != null && f.getNome_Fun().toLowerCase().
                contains(txt_nome.getText().toLowerCase()) && !txt_nome.getText().isBlank()) .map(Funcionario::getNome_Foto) .findFirst() .orElse(null);

           if(foto!=null){
        Path CaminhoFoto = Pasta.resolve(foto);

    if (Files.exists(CaminhoFoto)){
            Image imagem = new Image(String.valueOf(new File(CaminhoFoto.toString())));
            Foto.setImage(imagem);


        }else {

        Image imagem = new Image("/Imagens/user.png");
        Foto.setImage(imagem);
    }
    }else{
               Image imagem = new Image("/Imagens/user.png");
               Foto.setImage(imagem);}}
    @FXML
    void DigitandoaSenha(KeyEvent event){
        if (event.getCode()!= KeyCode.ENTER) {
            Lbl_error.setText("");
        }
    }
    @FXML
    void DigitandooCPF(KeyEvent event){
        if (event.getCode()!= KeyCode.ENTER) {
            Lbl_error_cpf.setText("");
        }
    }
    @FXML
    void DigitandoaNovaSenha(KeyEvent event){
        if (event.getCode()!= KeyCode.ENTER) {
            Lbl_error_Senha.setText("");
        }}
    @FXML
    void Verificar_cpf(){
      validarCPF();
    }
    @FXML
    void alterar_senha(){
        validaraNovaSenha();
    }
    public void clica_esqueceu_senha(){
        Tela_Fazer_login.setVisible(false);
        Tela_CPF.setVisible(true);
        txt_nome.setText("");
        Senha.setText("");
    } public void clica_botao_voltar_Tela_Cpf(){
        Tela_Fazer_login.setVisible(true);
        Tela_CPF.setVisible(false);
        txt_CPF.setText("");
    }
    public void clica_botao_voltar_Tela_Nova_senha(){
        Tela_Fazer_login.setVisible(true);
        Tela_Nova_senha.setVisible(false);
        txt_CPF.setText("");
        Nova_senha.setText("");
        Nova_senha_novamente.setText("");
    }
    public void sair(){
        System.exit(0);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MaskFieldUtil.cpfCnpjField(txt_CPF);

    }
}
