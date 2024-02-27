package com.example.demo.Models.Interfaces;

import com.example.demo.Models.Dao.ConfigDao;

public interface AdminConfigInterface {
    public boolean carregardados(ConfigDao configDao);
    public boolean VerificarCampos(String PrimeiraHora, String SegundaHora, String CadaHora, String Pernoite_inicio,
                                   String Pernoite_meio, String Pernoite_fim, String Pernoite_valor);
}
