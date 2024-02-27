package com.example.demo.Models.Services;

import com.example.demo.Models.Classes.Config;
import com.example.demo.Models.Dao.ConfigDao;
import com.example.demo.Models.Interfaces.AdminConfigInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AdminConfigService implements AdminConfigInterface {
    public boolean VerificarCampos(String PrimeiraHora, String SegundaHora, String CadaHora, String Pernoite_inicio,
                                   String Pernoite_meio, String Pernoite_fim, String Pernoite_valor) {
        if (PrimeiraHora.equals("") || SegundaHora.equals("") || CadaHora.equals("") || Pernoite_inicio.equals("")
                || Pernoite_meio.equals("") || Pernoite_fim.equals("") || Pernoite_valor.equals("")) {
            return true;
        } else {

            return false;
        }


    }

    public boolean carregardados(ConfigDao configDao) {
        ObservableList<Config> configs = FXCollections.observableArrayList();
        configs = configDao.ListaConfig();
        if (!configs.isEmpty()){
            return true;
        }else {
            return false;
        }
    }
}