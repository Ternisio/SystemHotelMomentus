package com.example.demo.Models.ConexaoRede;

import javax.swing.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(5000); // Porta do servidor
        System.out.println("Servidor iniciado. Aguardando conexões...");

        while (true) {
            Socket socket = serverSocket.accept();
            JOptionPane.showMessageDialog(null,"Cliente conectado: "+socket.getInetAddress());
            System.out.println("Cliente conectado: " + socket.getInetAddress());

            // Iniciar uma thread para tratar o objeto recebido
            Thread clientHandler = new Thread(new ClienteConfig(socket));
            clientHandler.start();
        }
    }
}
