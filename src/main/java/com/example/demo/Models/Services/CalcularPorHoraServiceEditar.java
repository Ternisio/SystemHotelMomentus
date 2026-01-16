package com.example.demo.Models.Services;

import com.example.demo.Models.Classes.Config;

public  class CalcularPorHoraServiceEditar  {



    public static double PorHora(Config config,boolean PrimeiraIncluir,int hora){
     if (PrimeiraIncluir){
         PrimeiraIncluir = false;
         return hora==0?config.getPrimeiraHora_valor(): config.getPrimeiraHora_valor() + ((hora - 1)*config.getValor_cada_hora());
     }
     return (hora*config.getValor_cada_hora());
    }
    public static double PorTolerancia(Config config,int min){
        if (min <= config.getTolerancia()){
            return 0;
        }
        return config.getValor_cada_hora();
    }
    }

