package com.example.demo.Models.Services;

import java.net.InetSocketAddress;
import java.net.Socket;

public class Conexoes {

    public static boolean InternetConectado(){
        try (Socket socket = new Socket()){
            socket.connect(new InetSocketAddress("8.8.8.8",53),3000);
            return true;
        }catch (Exception e){
            return false;
        }


    }


}
