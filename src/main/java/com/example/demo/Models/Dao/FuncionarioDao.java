package com.example.demo.Models.Dao;

import com.example.demo.Models.Api.GlobalApi;
import com.example.demo.Models.Classes.Funcionario;
import com.example.demo.Models.Classes.LoginResponse;
import com.example.demo.Models.Classes.Produto;
import com.example.demo.Models.Database.Conexao;
import com.example.demo.Models.Services.SessionManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class FuncionarioDao {
    GlobalApi globalApi = new GlobalApi();
    Dotenv dotenv = Dotenv.load();
    String enderecoFotoFun = dotenv.get("ENDERECO_FOTO_FUN");

    public ObservableList<Funcionario> ConsultaPorNomeOuCodigodoFuncionario(String resultado ){
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM  funcionarios  WHERE Nome _Fun LIKE '%"+resultado+"%' OR idFuncionarios LIKE '%"+resultado+ "%'";

        ObservableList<Funcionario> lista = FXCollections.observableArrayList();
        try {
            ps = Conexao.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                Funcionario funcionario = new Funcionario();
                funcionario.setId_Fun(rs.getString("idFuncionarios"));
                funcionario.setNome_Fun( rs.getString("Nome_Fun"));
                funcionario.setSenha_fun( rs.getString("Senha_Fun"));
                funcionario.setTipo_Fun( rs.getString("Tipo_Fun"));
                funcionario.setCpf_Fun(   rs.getString("Cpf_Fun"));
                funcionario.setNome_Foto(rs.getString("Nome_Foto"));                        ;
                lista.add(funcionario);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return lista;

    }
   public String foto(String Nome){
       PreparedStatement ps = null;
       ResultSet rs = null;
       String sql = "SELECT Nome_Foto FROM funcionarios WHERE Nome_Fun LIKE '%"+Nome+"%'";
       String Nome_foto ="";
       try {
           ps = Conexao.conectar().prepareStatement(sql);
           rs = ps.executeQuery();
           if (rs.next()){
               Nome_foto = rs.getString("Nome_Foto");
           }
       }
       catch (Exception e){

       }
       return Nome_foto;

   }
   public boolean verificar(TextField Nome, PasswordField Senha, Funcionario funcionario)  {

       boolean valicacao = false;
       HttpClient client = HttpClient.newHttpClient();
       String query = "mutation { LoginDesktop(inputLogin: { Nome: \\\""
               + Nome.getText()
               + "\\\", Senha: \\\""
               + Senha.getText()
               + "\\\", Remember: true }) { success token funcionario { Id_Funcionarios "
               + "Nome_Fun "
               + "Tipo_Fun "
               + "CPF_Fun "
               + "Nome_Foto } } }";

       String json = "{ \"query\": \"" + query + "\" }";
       try {
       HttpResponse<String> response = globalApi.Api(client,json);
// Processa resposta
       ObjectMapper mapper = new ObjectMapper();
       JsonNode root = mapper.readTree(response.body()).path("data").path("LoginDesktop");

       boolean success = root.path("success").asBoolean();
       if (success) {
           String token = root.path("token").asText();
           funcionario.setId_Fun(root.path("funcionario").path("Id_Funcionarios").asText());
           funcionario.setNome_Fun(root.path("funcionario").path("Nome_Fun").asText());
           funcionario.setTipo_Fun(root.path("funcionario").path("Tipo_Fun").asText());
           funcionario.setCpf_Fun(root.path("funcionario").path("CPF_Fun").asText());
           funcionario.setNome_Foto(root.path("funcionario").path("Nome_Foto").asText());
            LoginResponse res = new LoginResponse(token, funcionario);

           // salva token para não precisar logar de novo
           SessionManager.salvar(res);
                valicacao  = success;
           // abre tela principal);
       } else {
           valicacao  = success;
       }

} catch (Exception e) {
}
       return valicacao;
   }
    public boolean verificar(TextField Cpf)  {
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean valicacao = false;
        String sql = "SELECT * FROM  funcionarios WHERE Cpf_Fun = ? ";
        try {
            ps = Conexao.conectar().prepareStatement(sql);
            ps.setString(1, Cpf.getText());
            rs = ps.executeQuery();
            while (rs.next()){
                valicacao = true;

            }

            ps.close();
            rs.close();
        } catch (SQLException e){
            System.out.println(e);
        } catch (Exception e){

        }
        return valicacao;
    }
    public ObservableList<Funcionario> ListaFuncionario( ) {
        ObservableList<Funcionario> lista = FXCollections.observableArrayList();
        HttpClient client = HttpClient.newHttpClient();

        String json = """
{
  "query": "query { TodosFun { Id_Funcionarios Nome_Fun Tipo_Fun Senha_Fun CPF_Fun Nome_Foto } }"
}
""";


    try{


            HttpResponse<String> response = globalApi.Api(client,json);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode data = objectMapper.readTree(response.body()).get("data").get("TodosFun");
    for (JsonNode node : data){
        Funcionario f = new Funcionario();
        f.setId_Fun(node.get("Id_Funcionarios").asText());
        f.setNome_Fun(node.get("Nome_Fun").asText());
        f.setSenha_fun(node.get("Senha_Fun").asText());
        f.setCpf_Fun(node.get("CPF_Fun").asText());
        f.setTipo_Fun(node.get("Tipo_Fun").asText());
        f.setNome_Foto(node.get("Nome_Foto").asText());

        lista.add(f);
        if(f.getNome_Foto() != null && !f.getNome_Foto().isBlank()) {
            String url = enderecoFotoFun+"/" + f.getId_Fun();
            Path Pasta = Paths.get("Fotos_Fun");
            if (!Files.exists(Pasta)) {
                Files.createDirectories(Pasta);
            }
            Path destinno = Pasta.resolve(f.getNome_Foto());

            if (!Files.exists(destinno)) {
                try {
                    InputStream in = new URL(url).openStream();
                    Files.copy(in, destinno);
                } catch (IOException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);

                }

            }
        }
    }


} catch (Exception e){
    Alert alert = new Alert(Alert.AlertType.ERROR);
alert.setTitle("Erro");
alert.setHeaderText("Mensagem:");
alert.setHeaderText(""+e);
}




        return lista;

    }
    public ObservableList<String> Lista_Nome_Funcionario() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select Nome_Fun from funcionarios where Tipo_Fun=\"Recepcionista\";";
        String nome_Fun = "";
        ObservableList<String> lista = FXCollections.observableArrayList();
        try {
            ps = Conexao.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                nome_Fun = rs.getString("Nome_Fun");
                lista.add(nome_Fun);
            }
            ps.close();
            rs.close();
        }
        catch (SQLException e){
            System.out.println(e);
        }
        return lista;

    }

    public void Editar(Funcionario funcionario) {
        String sql = "UPDATE funcionarios SET Nome_Fun = ?,Senha_Fun = ?, Tipo_Fun = ?, Cpf_Fun = ? where idFuncionarios = ?";
        PreparedStatement ps = null;

        try {
            ps = Conexao.conectar().prepareStatement(sql);

            ps.setString(1, funcionario.getNome_Fun());
            ps.setString(2, funcionario.getSenha_fun());
            ps.setString(3, funcionario.getTipo_Fun());
            ps.setString(4, funcionario.getCpf_Fun());

            ps.setString(5, funcionario.getId_Fun());
            ps.execute();
            ps.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Aviso:");
            alert.setContentText("Foi atualizado com sucesso");
            alert.showAndWait();

        }
        catch (SQLException e) {
            // TODO: handle exception
        }
    }

    public void Editar(TextField Cpf, PasswordField Senha){
        String sql = "UPDATE funcionarios SET Senha_Fun = ? where Cpf_Fun = ? ";
        PreparedStatement ps = null;

        try {
            ps = Conexao.conectar().prepareStatement(sql);

            ps.setString(1, Senha.getText());
            ps.setString(2, Cpf.getText());
            ps.execute();
            ps.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Aviso:");
            alert.setContentText("Foi alterado com sucesso");
            alert.showAndWait();

        }
        catch (SQLException e) {
            // TODO: handle exception
        }
    }
    public void Editar(TextField Cpf, PasswordField Senha,Funcionario funcionario) {
        String sql = "UPDATE funcionarios SET Senha_Fun = ?,Cpf_Fun = ? where idFuncionarios = ? ";
        PreparedStatement ps = null;

        try {
            ps = Conexao.conectar().prepareStatement(sql);

            ps.setString(1, Senha.getText());
            ps.setString(2, Cpf.getText());
            ps.setString(3, funcionario.getId_Fun());
            ps.execute();
            ps.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Aviso:");
            alert.setContentText("Foi alterado com sucesso");
            alert.showAndWait();

        }
        catch (SQLException e) {
            // TODO: handle exception
        }
    }
    public void Editar(TextField Cpf, PasswordField Senha,Funcionario funcionario,File file) {
        String sql = "UPDATE funcionarios SET Senha_Fun = ?,Cpf_Fun = ?, Nome_Foto = ?, Foto_Fun = ? where idFuncionarios = ? ";
        PreparedStatement ps = null;

        try {
            ps = Conexao.conectar().prepareStatement(sql);

            ps.setString(1, Senha.getText());
            ps.setString(2, Cpf.getText());
            ps.setString(3,file.getName());
            FileInputStream fis = new FileInputStream(file);
            ps.setBinaryStream(4, (InputStream) fis, (int) file.length());
            ps.setString(5, funcionario.getId_Fun());
            ps.execute();
            ps.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Aviso:");
            alert.setContentText("Foi alterado com sucesso");
            alert.showAndWait();

        }
        catch (SQLException e) {
            // TODO: handle exception
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void Editar(TextField Cpf, Funcionario funcionario,File file) {
        String sql = "UPDATE funcionarios SET Cpf_Fun = ?, Nome_Foto = ?, Foto_Fun = ? where idFuncionarios = ? ";
        PreparedStatement ps = null;

        try {
            ps = Conexao.conectar().prepareStatement(sql);

            ps.setString(1, Cpf.getText());
            ps.setString(2,file.getName());
            FileInputStream fis = new FileInputStream(file);
            funcionario.setNome_Foto(file.getName());
            ps.setBinaryStream(3, (InputStream) fis, (int) file.length());
            ps.setString(4, funcionario.getId_Fun());
            ps.execute();
            ps.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Aviso:");
            alert.setContentText("Foi alterado com sucesso");
            alert.showAndWait();

        }
        catch (SQLException e) {
            // TODO: handle exception
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void Editar(Funcionario funcionario, File file) {
        String sql = "UPDATE funcionarios SET Nome_Fun = ?,Senha_Fun = ?, Tipo_Fun = ?, Cpf_Fun = ?,Nome_Foto = ? , Foto_Fun = ? where idFuncionarios = ?";
        //                                               1             2             3            4             5              6                        7
        PreparedStatement ps = null;

        System.out.println(funcionario.getCpf_Fun()+", "+funcionario.getNome_Fun()+", "+funcionario.getNome_Foto()+", "
                + funcionario.getTipo_Fun()+", "+ funcionario.getSenha_fun()+", "+ file.getName());
        try {
            ps = Conexao.conectar().prepareStatement(sql);

            ps.setString(1, funcionario.getNome_Fun());
            ps.setString(2, funcionario.getSenha_fun());
            ps.setString(3, funcionario.getTipo_Fun());
            ps.setString(4, funcionario.getCpf_Fun());
            ps.setString(5,file.getName());
            FileInputStream fis = new FileInputStream(file);
            ps.setBinaryStream(6, (InputStream) fis, (int) file.length());
            ps.setString(7, funcionario.getId_Fun());
            System.out.println( ps.execute());

            ps.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Aviso:");
            alert.setContentText("Foi atualizado com sucesso");
            alert.showAndWait();

        }catch (IOException e){
            System.out.println(e);
        }
        catch (SQLException e) {
            // TODO: handle exception
         System.out.println(e);
        }
    }
    public void cadastrar(Funcionario funcionario, File file) {
        String sql = "INSERT INTO funcionarios(idFuncionarios,Nome_Fun,Senha_Fun,Tipo_Fun, Cpf_Fun,Nome_Foto,Foto_Fun)" +
                "  VALUES(?,?,?,?,?,?,?)";
        PreparedStatement ps = null;


        try {
            ps = Conexao.conectar().prepareStatement(sql);
            ps.setString(1, funcionario.getId_Fun());
            ps.setString(2, funcionario.getNome_Fun());
            ps.setString(3, funcionario.getSenha_fun());
            ps.setString(4, funcionario.getTipo_Fun());
            ps.setString(5, funcionario.getCpf_Fun());
            ps.setString(6,file.getName());
            FileInputStream fis = new FileInputStream(file);
            ps.setBinaryStream(7, (InputStream) fis, (int) file.length());

            ps.execute();
            ps.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Aviso:");
            alert.setContentText("Foi cadastrado com sucesso");
            alert.showAndWait();

        } catch (IOException e){

        }
        catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    public void cadastrar(Funcionario funcionario) {
        String sql = "INSERT INTO funcionarios(idFuncionarios,Nome_Fun,Senha_Fun,Tipo_Fun, Cpf_fun)" +
                "  VALUES(?,?,?,?,?)";
        PreparedStatement ps = null;


        try {
            ps = Conexao.conectar().prepareStatement(sql);
            ps.setString(1, funcionario.getId_Fun());
            ps.setString(2, funcionario.getNome_Fun());
            ps.setString(3, funcionario.getSenha_fun());
            ps.setString(4, funcionario.getTipo_Fun());
            ps.setString(5, funcionario.getCpf_Fun());
            ps.execute();
            ps.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Aviso:");
            alert.setContentText("Foi cadastrado com sucesso");
            alert.showAndWait();

        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    public void Excluir(Funcionario funcionario) {
        String sql = "DELETE FROM funcionarios WHERE idFuncionarios = ?";
        PreparedStatement ps = null;

        try {
            ps = Conexao.conectar().prepareStatement(sql);
            ps.setString(1, funcionario.getId_Fun());
            ps.execute();
            ps.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Aviso:");
            alert.setContentText("Foi excluido com sucesso");
            alert.showAndWait();

        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    public void Excluir_tudo() {
        String sql = "DELETE FROM funcionarios";
        PreparedStatement ps = null;

        try {
            ps = Conexao.conectar().prepareStatement(sql);
            ps.execute();
            ps.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Aviso:");
            alert.setContentText("Foi excluido tudo com sucesso");
            alert.showAndWait();

        } catch (SQLException e) {
            // TODO: handle exception
        }
    }
}
