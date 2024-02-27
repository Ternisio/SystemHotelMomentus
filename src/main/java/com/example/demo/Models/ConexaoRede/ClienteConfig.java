package com.example.demo.Models.ConexaoRede;

import javax.swing.*;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClienteConfig implements Runnable {
    private Socket socket;
    public ClienteConfig(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            // Receber o objeto enviado pelo cliente

           JOptionPane.showMessageDialog(null,"Objeto recebido do cliente: "  );

            // Fechar a conexão com o cliente
            ois.close();
            socket.close();

            // Inserir os dados no banco de dados
            // Implemente aqui a lógica de inserção no banco de dados
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }

