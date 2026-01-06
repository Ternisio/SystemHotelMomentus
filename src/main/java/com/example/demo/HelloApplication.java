package com.example.demo;

import com.example.demo.Controllers.ControllerDashboard;
import com.example.demo.Controllers.ControllerLogin;
import com.example.demo.Controllers.ControllerTelaFuncionario;
import com.example.demo.Models.Classes.*;
import com.example.demo.Models.Dao.ConfigDao;
import com.example.demo.Models.Dao.FuncionarioDao;
import com.example.demo.Models.Dao.ProdutoDao;
import com.example.demo.Models.Dao.QuartoDao;
import com.example.demo.Models.Interfaces.*;
import com.example.demo.Models.Services.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    Stage stage = new Stage();
    Quarto quarto = new Quarto();
    Funcionario funcionario = new Funcionario();
    FuncionarioDao funcionarioDao = new FuncionarioDao();
    ProdutoDao produtoDao = new ProdutoDao();
    Produto produto = new Produto();
    Config config = new Config();
    QuartoDao quartoDao = new QuartoDao();
    ConfigDao configDao = new ConfigDao();

    AdminQuartoInterface   adminQuartoInterface = new AdminQuartoService();
    AdminProdutoInterface adminProdutoInterface = new AdminProdutoService();
    AdminFuncionarioInterface adminFuncionarioInterface = new AdminFuncionarioService();
    AdminConfigInterface adminConfigInterface  = new AdminConfigService();
    VendaInterface vendaInterface = new VendaService();
    FuncionarioVendaInterface funcionarioVendaInterface = new FunQuatoVendaService();
    ControllerDashboard controllerDashboard = new ControllerDashboard();
    Vendas vendas = new Vendas();
    VendaInterface vendaInterfaceFun = new VendaServiceFun();
    LoginInterface loginInterface = new LoginService();
    ControllerTelaFuncionario controllerTelaFuncionario = new ControllerTelaFuncionario();
    @Override


    public void start(Stage stage) throws IOException {
        LoginResponse res = SessionManager.carregar();
        if(res != null){
            trocartela(res.getFuncionario().getTipo_Fun(), res.getFuncionario());
        }else{
            login();
        }
    }
    void login() throws IOException {

        FXMLLoader loader =  new FXMLLoader(getClass().getResource("Views/Login.fxml"));

        Parent root = loader.load();
        ControllerLogin controllerLogin = loader.getController();
        controllerLogin.inicial(this, funcionarioDao,loginInterface);
        Scene scene = new Scene(root);
        stage.setTitle("Sistema Do Motel");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();



    }
    public void trocartela(String tipo_fun , Funcionario funcionario) throws IOException{

         if(tipo_fun.equals("Administrador")){
             FXMLLoader loader =  new FXMLLoader(getClass().getResource("Views/TelaAdministrador.fxml"));
             Parent root = loader.load();
             controllerDashboard = loader.getController();
             controllerDashboard.inicial(adminQuartoInterface,adminProdutoInterface,adminFuncionarioInterface,
                     adminConfigInterface,configDao, quarto,quartoDao,produto,produtoDao,config, funcionario,funcionarioDao, vendaInterface, vendaInterfaceFun);

             Scene scene = new Scene(root);
             stage.setTitle("Sistema Do Motel");
             stage.setScene(scene);
             stage.setResizable(false);
             stage.show();
             stage.setOnCloseRequest(event ->{
                 controllerDashboard.Pare = true;

             });
         }else if(tipo_fun.equals("Recepcionista")){

             FXMLLoader loader =  new FXMLLoader(getClass().getResource("Views/Tela_funcionario.fxml"));
             Parent root = loader.load();
             controllerTelaFuncionario =  loader.getController();
             controllerTelaFuncionario.inicial(quartoDao, quarto, vendas, funcionarioVendaInterface, funcionarioDao,funcionario,config,configDao);

             Scene scene = new Scene(root);
             stage.setTitle("Sistema Do Motel");
             stage.setScene(scene);
             stage.setResizable(false);
             stage.show();
             stage.setOnCloseRequest(event ->{
                 controllerTelaFuncionario.parar();
                 System.out.println("parou");
             });
         }
    }

    public static void main(String[] args) {

        launch(args);
    }
}