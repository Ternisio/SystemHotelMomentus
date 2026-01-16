package com.example.demo.Models.SincronizarAsync;

import com.example.demo.Models.Classes.Despesa;
import com.example.demo.Models.Dao.DespesaDao;
import com.example.demo.Models.Services.Conexoes;
import com.example.demo.Models.Services.DespesaTemporario;
import javafx.collections.ObservableList;

public class DespesaSincronizarAsync {
    public static void sincronizarAsync(ObservableList<Despesa> despesas){
        if(despesas == null || despesas.isEmpty()){
            return;
        }
        if(!Conexoes.InternetConectado()){
            System.out.println("Você está sem internet");
            return;
        }
    new Thread(()->{
        try {
        boolean ok = DespesaDao.CadastrarLista(despesas);
        if(ok){
            DespesaTemporario.limpar();
        } else {
            System.out.println("Erro ao servidor ");
        }
        }catch (Exception e){
            System.out.println("O arquivo está mantido");

        }
    }).start();

    }
}
