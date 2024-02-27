package com.example.demo.Models.Dao;

import com.example.demo.Models.Classes.Config;
import com.example.demo.Models.Classes.Consumo;
import com.example.demo.Models.Classes.Produto;
import com.example.demo.Models.Classes.Vendas;
import com.example.demo.Models.Database.Conexao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ConsumoDao {
    public void cadastrar( Vendas vendas, String Cod_produto, int qtd) {
        String sql = "INSERT INTO consumo(id_venda, id_produto, Quantidade, data_Consumo_Produto)" +
                "  VALUES(?,?,?,?)";
        PreparedStatement ps = null;


        try {
            ps = Conexao.conectar().prepareStatement(sql);
            ps.setString(1, vendas.getCod_venda());
            ps.setString(2,Cod_produto);
            ps.setInt(3, qtd);
            ps.setObject(4, LocalDate.now());
            ps.execute();
            ps.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Aviso:");
            alert.setContentText("Foi salvo com sucesso");
            alert.showAndWait();

        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    public ObservableList<Consumo> ListaConsumo(Vendas vendas){
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT consumo.idConsumo, produtos.idProdutos, produtos.Nome_prod, produtos.Valor_prod, consumo.Quantidade \n" +
                "                from consumo inner join produtos on consumo.id_produto = produtos.idProdutos \n" +
                "                WHERE consumo.id_venda = ?";
        ObservableList<Consumo> lista = FXCollections.observableArrayList();
        try {
            ps = Conexao.conectar().prepareStatement(sql);
            ps.setString(1,vendas.getCod_venda());
            rs = ps.executeQuery();
            while (rs.next()){
                Consumo consumo =  new Consumo(rs.getInt("consumo.idConsumo"), rs.getString("produtos.idProdutos"),
                        rs.getString("produtos.Nome_prod"), String.format("%.2f",rs.getFloat("produtos.Valor_prod")).replace(".",","),
                        rs.getInt("consumo.Quantidade") );




                lista.add(consumo);
            }
            rs.close();
            ps.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return lista;

    }
    public int Estoques(Vendas vendas, String Id_produto){
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql =" SELECT produtos.estoque_prod  from consumo inner join produtos on consumo.id_produto = produtos.idProdutos"+
       " WHERE consumo.id_venda = ? and consumo.id_produto = ?";
    int qtd_Estoques = 0;
        try {
            ps = Conexao.conectar().prepareStatement(sql);
            ps.setString(1,vendas.getCod_venda());
            ps.setString(2, Id_produto);
            rs = ps.executeQuery();
            while (rs.next()){
             qtd_Estoques = rs.getInt("produtos.estoque_prod");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return qtd_Estoques;

    }
    public ObservableList<Produto> ListaConsumoDados(LocalDate Data_Inicio, LocalDate Data_fim, int Limite){
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT p.Nome_prod as nome, sum(c.Quantidade * p.Valor_prod) as total FROM consumo c INNER JOIN produtos p on p.idProdutos = c.id_produto  WHERE c.data_Consumo_Produto BETWEEN '"+Data_Inicio+"' AND '"+Data_fim+"' GROUP BY nome ORDER BY total desc LIMIT "+Limite+";";
        ObservableList<Produto> lista = FXCollections.observableArrayList();
        try {
            ps = Conexao.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
              Produto produto = new Produto(rs.getString("nome"), rs.getFloat("total"));
              lista.add(produto);
            }
            rs.close();
            ps.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return lista;

    }
    public void editarProduto(String Cod_Produto, int Restantes){
    String sql = "UPDATE produtos SET  estoque_prod= ? where idProdutos = ?";
    PreparedStatement ps = null;

        try {
        ps = Conexao.conectar().prepareStatement(sql);
        ps.setInt(1, Restantes);
        ps.setString(2, Cod_Produto);
        ps.execute();
        ps.close();


    }
        catch (SQLException e) {
        // TODO: handle exception
        e.printStackTrace();
    }
}
    public void editarConsumo(String Cod_Produto, int qtd, Vendas vendas){
        String sql = "UPDATE consumo SET  Quantidade = ? where id_produto = ? and id_venda = ?";
        PreparedStatement ps = null;

        try {
            ps = Conexao.conectar().prepareStatement(sql);
            ps.setInt(1, qtd);
            ps.setString(2, Cod_Produto);
            ps.setString(3, vendas.getCod_venda());
            ps.execute();
            ps.close();


        }
        catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    public void Excluir(Vendas vendas, String id_produto) {
        String sql = "DELETE FROM consumo WHERE id_produto= ? and id_venda = ?";
        PreparedStatement ps = null;

        try {
            ps = Conexao.conectar().prepareStatement(sql);
            ps.setString(1, id_produto);
            ps.setString(2, vendas.getCod_venda());
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

    public void ExcluirVenda(Vendas vendas) {
        String sql = "DELETE FROM consumo WHERE  id_venda = ?";
        PreparedStatement ps = null;

        try {
            ps = Conexao.conectar().prepareStatement(sql);
            ps.setString(1, vendas.getCod_venda());
            ps.execute();
            ps.close();

        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
