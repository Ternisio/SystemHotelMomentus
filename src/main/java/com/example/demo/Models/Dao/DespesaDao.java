package com.example.demo.Models.Dao;

import com.example.demo.Models.Classes.Despesa;
import com.example.demo.Models.Classes.Quarto;
import com.example.demo.Models.Classes.Vendas;
import com.example.demo.Models.Database.Conexao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;

public class DespesaDao {
    public void cadastrar(Despesa despesa) {
        String sql = "INSERT INTO despesas(Data_despesa,Tipo_despesas,Nome_despesa,Valor_despesas)" +
                "  VALUES(?,?,?,?)";
        PreparedStatement ps = null;


        try {
            ps = Conexao.conectar().prepareStatement(sql);
            ps.setObject(1, despesa.getData_despesa());
            ps.setString(2, despesa.getTipo_Despesa());
            ps.setString(3, despesa.getNome_Despesa());
            ps.setDouble(4, despesa.getValor());
            ps.execute();
            ps.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Aviso:");
            alert.setContentText("Foi cadastrado com sucesso");
            alert.showAndWait();

        }
        catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    public ObservableList ListaDeDespesa()  {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM despesas";
        ObservableList<Despesa> lista = FXCollections.observableArrayList();


        try {
            ps = Conexao.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                Despesa despesa = new Despesa(rs.getLong("idDespesas"),rs.getDate("Data_despesa").toLocalDate(),rs.getString("Tipo_despesas"), rs.getString("Nome_despesa"), rs.getDouble("Valor_despesas"));
                lista.add(despesa);
            }
            ps.close();
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }



        return lista;
    }
    public void Excluir(Despesa despesa) {
        String sql = "DELETE FROM despesas WHERE idDespesas= " + despesa.getCodigo_Despesa() + "";
        PreparedStatement ps = null;

        try {
            ps = Conexao.conectar().prepareStatement(sql);
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
        public void Editar(Despesa despesa) {
            String sql = "UPDATE despesas SET Data_despesa = ?, Tipo_despesas=?,Nome_despesa = ?, Valor_despesas = ? WHERE idDespesas= "+despesa.getCodigo_Despesa()+"";
            PreparedStatement ps = null;

            try {
                ps = Conexao.conectar().prepareStatement(sql);
                ps.setObject(1, despesa.getData_despesa());
                ps.setString(2, despesa.getTipo_Despesa());
                ps.setString(3, despesa.getNome_Despesa());
                ps.setDouble(4, despesa.getValor());
                ps.execute();
                ps.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso");
                alert.setHeaderText("Aviso:");
                alert.setContentText("Foi atualizado com sucesso");
                alert.showAndWait();

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null,e);
            }
    }
}
