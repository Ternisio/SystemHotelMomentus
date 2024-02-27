package com.example.demo.Models.ConexaoRede;




import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Cliente {
    public static void main(String[] args) throws IOException {

              String serverIp = "192.168.250.210"; // Substitua pelo IP do servidor
        int serverPort = 4000; // Porta do servidor
        Socket socket = new Socket(serverIp, serverPort);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

        // Enviar o objeto para o servidor


        // Fechar a conexão com o servidor
        oos.close();
        socket.close();



    }

}
