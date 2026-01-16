package com.example.demo.Models.SincronizarAsync;

import com.example.demo.Models.Classes.Despesa;
import com.example.demo.Models.Classes.Vendas;
import com.example.demo.Models.Dao.DespesaDao;
import com.example.demo.Models.Services.Conexoes;
import com.example.demo.Models.Services.DespesaTemporario;
import com.example.demo.Models.Services.VendasFinalizadoNaoSalvoTemporario;
import javafx.collections.ObservableList;

public class VendasSincronizarAsync {
    public static void sincronizarAsyncListaNaoSalvo(ObservableList<Vendas> vendas){
        if( vendas == null || vendas.isEmpty()){
            return;
        }
        if(!Conexoes.InternetConectado()){
            System.out.println("Você está sem internet");
            return;
        }
    new Thread(()->{
        try {

        boolean ok = true;
                if(ok){
                    VendasFinalizadoNaoSalvoTemporario.limpar();
                } else {
                    System.out.println("Erro ao servidor ");
                }


        }catch (Exception e){
            System.out.println("O arquivo está mantido");

        }
    }).start();

    }
}
