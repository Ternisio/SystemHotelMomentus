package com.example.demo.Models.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static Connection conn;
    private static final String Driver = "com.mysql.cj.jdbc.Driver";
    private static final String Servidor = "localhost";
    private static final String Porta = "3306";
    private static final String Banco = "systemhotel";
    private static final String Url = "jdbc:mysql://"+ Servidor +":"+Porta+"/"+Banco;
    private static final String User = "MotelMomentus";
    private static final String Password = "Theo254688957";

    public static Connection conectar() {

        try {
            Class.forName(Driver);
            if(conn == null) {
                conn = DriverManager.getConnection(Url, User, Password);
                System.out.println("O Banco está conectado");
                return conn;
            } else {
                return conn;
            }

        } catch (ClassNotFoundException e) {
            System.out.println(e);
            return null;

        }
        catch (SQLException e) {
            // TODO: handle exception
            System.out.println(e);
            return null;
        }
    }

}
