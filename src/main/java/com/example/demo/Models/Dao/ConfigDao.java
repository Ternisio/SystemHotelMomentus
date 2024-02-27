package com.example.demo.Models.Dao;

import com.example.demo.Models.Classes.Config;
import com.example.demo.Models.Classes.Produto;
import com.example.demo.Models.Database.Conexao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConfigDao {

    public ObservableList<Config> ListaConfig() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM  config";
        ObservableList<Config> lista = FXCollections.observableArrayList();
        try {
            ps = Conexao.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {

                Config config = new Config();
                config.setPrimeiraHora_valor(rs.getDouble("PrimeiraHora_valor"));
                config.setSegundaHora_valor(rs.getDouble("SegundaHora_valor"));
                config.setValor_cada_hora(rs.getDouble("Valor_cada_Hora"));
                config.setPernoite_inicio(rs.getString("Pernoite_Inicial"));
                config.setPernoite_meio(rs.getString("Pernoite_Horaminimo"));
                config.setPernoite_fim(rs.getString("Pernoite_Fim"));
                config.setPernoite_valor(rs.getDouble("Pernoite_Valor"));

                lista.add(config);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;

    }
    public void ListaConfig(Config config) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM  config";
        ObservableList<Config> lista = FXCollections.observableArrayList();
        try {
            ps = Conexao.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {

                config.setPrimeiraHora_valor(rs.getDouble("PrimeiraHora_valor"));
                config.setSegundaHora_valor(rs.getDouble("SegundaHora_valor"));
                config.setValor_cada_hora(rs.getDouble("Valor_cada_Hora"));
                config.setPernoite_inicio(rs.getString("Pernoite_Inicial"));
                config.setPernoite_meio(rs.getString("Pernoite_Horaminimo"));
                config.setPernoite_fim(rs.getString("Pernoite_Fim"));
                config.setPernoite_valor(rs.getDouble("Pernoite_Valor"));



            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void cadastrar(Config config) {
        String sql = "INSERT INTO config(PrimeiraHora_valor, SegundaHora_valor, Valor_cada_Hora, Pernoite_Inicial,Pernoite_Horaminimo,Pernoite_Fim,Pernoite_Valor)" +
                "  VALUES(?,?,?,?,?,?,?)";
        PreparedStatement ps = null;


        try {
            ps = Conexao.conectar().prepareStatement(sql);
            ps.setDouble(1, config.getPrimeiraHora_valor());
            ps.setDouble(2, config.getSegundaHora_valor());
            ps.setDouble(3, config.getValor_cada_hora());
            ps.setString(4, config.getPernoite_inicio());
            ps.setString(5, config.getPernoite_meio());
            ps.setString(6, config.getPernoite_fim());
            ps.setDouble(7, config.getPernoite_valor());
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

    public void editar(Config config) {
        String sql = "UPDATE config SET PrimeiraHora_valor = ?, SegundaHora_valor = ?, Valor_cada_Hora = ?, Pernoite_Inicial = ?,Pernoite_meio = ?, Pernoite_Fim = ?, Pernoite_Valor = ? where idconfig = 1";
        PreparedStatement ps = null;

        try {
            ps = Conexao.conectar().prepareStatement(sql);
            ps.setDouble(1, config.getPrimeiraHora_valor());
            ps.setDouble(2, config.getSegundaHora_valor());
            ps.setDouble(3, config.getValor_cada_hora());
            ps.setString(4, config.getPernoite_inicio());
            ps.setString(5, config.getPernoite_meio());
            ps.setString(6, config.getPernoite_fim());
            ps.setDouble(7, config.getPernoite_valor());

            ps.execute();
            ps.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Aviso:");
            alert.setContentText("Foi atualizado com sucesso");
            alert.showAndWait();

        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

}
