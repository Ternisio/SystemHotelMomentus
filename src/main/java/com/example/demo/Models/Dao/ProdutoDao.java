package com.example.demo.Models.Dao;

import com.example.demo.Models.Classes.Produto;
import com.example.demo.Models.Database.Conexao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdutoDao {
    public ObservableList<Produto> ConsultaPorNomeOuCodigodoProdutoCOMESTOQUES(String resultado ){
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM produtos WHERE (Nome_prod LIKE '%"+resultado+"%' OR idProdutos LIKE '%"+resultado+ "%') AND estoque_prod > 0 ";
        ObservableList<Produto> lista = FXCollections.observableArrayList();
        try {
            ps = Conexao.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                Produto produto = new Produto();
                produto.setIdProduto(rs.getString("idProdutos"));
                produto.setNome_prod( rs.getString("Nome_prod"));
                produto.setTipo_Produto(   rs.getString("tipo_prod"));
                produto.setExibirValor( String.format("%.2f",rs.getFloat("Valor_prod")).replace(".",","));
                produto.setEstoques(   rs.getInt("estoque_prod"));
                produto.setFoto(rs.getString("Nome_foto"));

                lista.add(produto);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return lista;

    }
    public ObservableList<Produto> ConsultaPorNomeOuCodigodoProduto(String resultado ){
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM produtos WHERE Nome_prod LIKE '%"+resultado+"%' OR idProdutos LIKE '%"+resultado+ "%'";
        ObservableList<Produto> lista = FXCollections.observableArrayList();
        try {
            ps = Conexao.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                Produto produto = new Produto();
                produto.setIdProduto(rs.getString("idProdutos"));
                produto.setNome_prod( rs.getString("Nome_prod"));
                produto.setTipo_Produto(   rs.getString("tipo_prod"));
                produto.setExibirValor( String.format("%.2f",rs.getFloat("Valor_prod")).replace(".",","));
                produto.setEstoques(   rs.getInt("estoque_prod"));
                produto.setFoto(rs.getString("Nome_foto"));

                lista.add(produto);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return lista;

    }
    public ObservableList<Produto> ConsultaPorComEstoques( ){
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM produtos WHERE estoque_prod > 0";
        ObservableList<Produto> lista = FXCollections.observableArrayList();
        try {
            ps = Conexao.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                Produto produto = new Produto();
                produto.setIdProduto(rs.getString("idProdutos"));
                produto.setNome_prod( rs.getString("Nome_prod"));
                produto.setTipo_Produto(   rs.getString("tipo_prod"));
                produto.setExibirValor( String.format("%.2f",rs.getFloat("Valor_prod")).replace(".",","));
                produto.setEstoques(   rs.getInt("estoque_prod"));
                produto.setFoto(rs.getString("Nome_foto"));

                ;
                lista.add(produto);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return lista;

    }
    public ObservableList<Produto> ConsultaPorCategoria(String categoria ){
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT idProdutos FROM produtos WHERE tipo_prod = '"+categoria+"' ORDER BY idProdutos ASC";
        ObservableList<Produto> lista = FXCollections.observableArrayList();
        try {
            ps = Conexao.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                Produto produto = new Produto();
                produto.setIdProduto(rs.getString("idProdutos"));


                ;
                lista.add(produto);
            }
            rs.close();
            ps.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return lista;

    }
    public ObservableList<Produto> ConsultaPorSemEstoques( ){
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM produtos WHERE estoque_prod = 0";
        ObservableList<Produto> lista = FXCollections.observableArrayList();
        try {
            ps = Conexao.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                Produto produto = new Produto();
                produto.setIdProduto(rs.getString("idProdutos"));
                produto.setNome_prod( rs.getString("Nome_prod"));
                produto.setTipo_Produto(   rs.getString("tipo_prod"));
                produto.setExibirValor( String.format("%.2f",rs.getFloat("Valor_prod")).replace(".",","));
                produto.setEstoques(   rs.getInt("estoque_prod"));
                produto.setFoto(rs.getString("Nome_foto"));


                lista.add(produto);
            }
            rs.close();
            ps.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return lista;

    }
    public void foto() {
    	 PreparedStatement ps = null;
         ResultSet rs = null;
         
      String sql = "SELECT Nome_foto, foto_prod FROM  produtos  ";
      
      try {
          ps = Conexao.conectar().prepareStatement(sql);
          rs = ps.executeQuery();
          while (rs.next()){


              String Foto = rs.getString("Nome_foto")                        ;
            
              Blob blob = rs.getBlob("foto_prod");

              if(blob != null) {

                  InputStream inputStream = blob.getBinaryStream();
                  Files.copy(inputStream, Paths.get("Fotos_prod/" +Foto), StandardCopyOption.REPLACE_EXISTING);
              }
          }
          rs.close();
          ps.close();

    }catch (IOException  e) {
		// TODO: handle exception
    	System.out.print(e);
	}catch(SQLException e) {
	   	System.out.print(e);
		}
      
	}
       public ObservableList<Produto> ListaProduto( ){
           PreparedStatement ps = null;
           ResultSet rs = null;
        String sql = "SELECT * FROM  produtos  ";
        ObservableList<Produto> lista = FXCollections.observableArrayList();
        try {
            ps = Conexao.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){

                Produto produto = new Produto();
                produto.setIdProduto(rs.getString("idProdutos"));
                produto.setNome_prod( rs.getString("Nome_prod"));
                produto.setTipo_Produto(   rs.getString("tipo_prod"));
                produto.setExibirValor( String.format("%.2f",rs.getFloat("Valor_prod")).replace(".",","));
                produto.setEstoques(   rs.getInt("estoque_prod"));

                produto.setFoto(rs.getString("Nome_foto"));                        ;
                lista.add(produto);

            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return lista;

       }

    public void Editar(Produto produto) {
        String sql = "UPDATE produtos SET Nome_prod= ?, tipo_prod = ?, Valor_prod = ?, estoque_prod=? where idProdutos = ?";
        PreparedStatement ps = null;

        try {
            ps = Conexao.conectar().prepareStatement(sql);
            ps.setString(1, produto.getNome_prod());
            ps.setString(2, produto.getTipo_Produto());
            ps.setFloat(3,produto.getValor());
            ps.setInt(4, produto.getEstoques());

            ps.setString(5, produto.getIdProduto());
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
    public void Editar(Produto produto, File file) {
        String sql = "UPDATE produtos SET Nome_prod= ?, tipo_prod = ?, Valor_prod = ?, estoque_prod= ?,Nome_foto= ?, foto_prod = ? where idProdutos = ?";
        PreparedStatement ps = null;

        try {
            ps = Conexao.conectar().prepareStatement(sql);
            ps.setString(1, produto.getNome_prod());
            ps.setString(2, produto.getTipo_Produto());
            ps.setFloat(3,produto.getValor());
            ps.setInt(4, produto.getEstoques());
            ps.setString(5,file.getName());
            FileInputStream fis = new FileInputStream(file);
            ps.setBinaryStream(6, (InputStream) fis, (int) file.length());
            ps.setString(7, produto.getIdProduto());
            ps.execute();
            ps.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Aviso:");
            alert.setContentText("Foi atualizado com sucesso");
            alert.showAndWait();

        }catch (IOException e){
        e.printStackTrace();
        }
        catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    public void cadastrar(Produto produto, File file) {
        String sql = "INSERT INTO produtos(idProdutos,Nome_prod, tipo_prod,  Valor_prod, estoque_prod,Nome_foto,foto_prod)" +
                "  VALUES(?,?,?,?,?,?,?)";
        PreparedStatement ps = null;


        try {
            ps = Conexao.conectar().prepareStatement(sql);
            ps.setString(1, produto.getIdProduto());
            ps.setString(2, produto.getNome_prod());
            ps.setString(3, produto.getTipo_Produto());
            ps.setFloat(4,produto.getValor());
            ps.setInt(5, produto.getEstoques());
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
    public void cadastrar(Produto produto) {
        String sql = "INSERT INTO produtos(idProdutos,Nome_prod, tipo_prod,  Valor_prod, estoque_prod)" +
                "  VALUES(?,?,?,?,?)";
        PreparedStatement ps = null;


        try {
            ps = Conexao.conectar().prepareStatement(sql);
            ps.setString(1, produto.getIdProduto());
            ps.setString(2, produto.getNome_prod());
            ps.setString(3, produto.getTipo_Produto());
            ps.setFloat(4, produto.getValor());
            ps.setInt(5, produto.getEstoques());
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

        public void Excluir(Produto produto) {
        String sql = "DELETE FROM produtos WHERE idProdutos = ?";
        PreparedStatement ps = null;

        try {
            ps = Conexao.conectar().prepareStatement(sql);
            ps.setString(1, produto.getIdProduto());
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
        String sql = "DELETE FROM produtos";
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
