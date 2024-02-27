package com.example.demo.Models.Dao;

import com.example.demo.Models.Classes.Quarto;
import com.example.demo.Models.Classes.Vendas;
import com.example.demo.Models.Database.Conexao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuartoDao {
    public int QuartoOcupados() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT count(Status_quartos) FROM quartos WHERE Status_quartos= 'Ocupado'";
        int Total = 0;

        try {
            ps = Conexao.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Total = rs.getInt("count(Status_quartos)");
            }
            rs.close();
            ps.close();
        }catch (SQLException e){
            System.out.println(e);
        }
        return Total;
    }
public int QuartoDisponiveis() {
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "SELECT count(Status_quartos) FROM quartos WHERE Status_quartos= 'Disponível'";
    int Total = 0;

        try {
            ps = Conexao.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Total = rs.getInt("count(Status_quartos)");
            }
        rs.close();
            ps.close();
        }catch (SQLException e){
        System.out.println(e);
    }
        return Total;
}
    public ObservableList<Quarto> ConsultaPorNumerodoQuarto(String num ){
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM quartos WHERE num_quartos LIKE '%"+num+"%' ORDER BY num_quartos;";
        ObservableList<Quarto> lista = FXCollections.observableArrayList();
        try {
            ps = Conexao.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                Quarto quarto = new Quarto();
                quarto.setCod_Quarto(rs.getInt("idquartos"));
                quarto.setNum_quarto( rs.getString("num_quartos"));
                quarto.setEstado(   rs.getString("Status_quartos"));


                lista.add(quarto);
            }
            ps.close();
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return lista;

    }
       public ObservableList<Quarto> ListaQuarto( ){
           PreparedStatement ps = null;
           ResultSet rs = null;
        String sql = "SELECT * FROM quartos  ORDER BY num_quartos";
        ObservableList<Quarto> lista = FXCollections.observableArrayList();
        try {
            ps = Conexao.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                Quarto quarto = new Quarto();
                        quarto.setCod_Quarto(rs.getInt("idquartos"));
                        quarto.setNum_quarto( rs.getString("num_quartos"));
                        quarto.setEstado(   rs.getString("Status_quartos"));

                        ;
                lista.add(quarto);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return lista;

       }


    public void Editar(Quarto Quarto) {
        String sql = "UPDATE quartos SET num_quartos = ?,Status_quartos = ? where idquartos = ?";
        PreparedStatement ps = null;

        try {
            ps = Conexao.conectar().prepareStatement(sql);
            ps.setString(1, Quarto.getNum_quarto());
            ps.setString(2, Quarto.getEstado());
            ps.setInt(3,Quarto.getCod_Quarto());
            ps.execute();
            ps.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Aviso:");
            alert.setContentText("Foi atualizado com sucesso");
            alert.showAndWait();

        } catch (SQLException e) {
            // TODO: handle exception
        }
    }

    public void Editar(Vendas vendas) {
        String sql = "UPDATE quartos SET Status_quartos  = ? where num_quartos = ?";
        PreparedStatement ps = null;

        try {
            ps = Conexao.conectar().prepareStatement(sql);
            ps.setString(1, vendas.getEstado());
            ps.setString(2, vendas.getNum_quarto());

            ps.execute();
            ps.close();


        } catch (SQLException e) {
            // TODO: handle exception
        }
    }


    public void cadastrar(Quarto Quarto) {
        String sql = "INSERT INTO quartos(num_quartos, Status_quartos, idquartos)  VALUES(?,?,?)";
        PreparedStatement ps = null;

        try {
            ps = Conexao.conectar().prepareStatement(sql);
            ps.setString(1, Quarto.getNum_quarto());
            ps.setString(2, Quarto.getEstado());
            ps.setInt(3, Quarto.getCod_Quarto());
            ps.execute();
            ps.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Aviso:");
            alert.setContentText("Foi cadastrado com sucesso");
            alert.showAndWait();

        } catch (SQLException e) {
            // TODO: handle exception
        }
    }

    public void Excluir(Quarto Quarto) {
        String sql = "DELETE FROM quartos WHERE idquartos = ?";
        PreparedStatement ps = null;

        try {
            ps = Conexao.conectar().prepareStatement(sql);
            ps.setInt(1, Quarto.getCod_Quarto());
            ps.execute();
            ps.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Aviso:");
            alert.setContentText("Foi excluido com sucesso");
            alert.showAndWait();

        } catch (SQLException e) {
            // TODO: handle exception
        }
    }

    public void Excluir_tudo() {
        String sql = "DELETE FROM quartos";
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
