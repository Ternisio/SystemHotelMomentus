package com.example.demo.Models.Services;

import com.example.demo.Models.Classes.Config;
import com.example.demo.Models.Classes.Vendas;
import com.example.demo.Models.Interfaces.Calcular;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class CalcularPorPernoiteService  {

  public static double PorPernoite(LocalDateTime MomentoAtual,Config config,LocalDateTime Fim, LocalDateTime InicioP, LocalDateTime FimP ,boolean primeira, int mint){
        LocalDateTime Proximo = (Fim.isBefore(FimP))?Fim:FimP;
        int mt = (int) (ChronoUnit.MINUTES.between(MomentoAtual, Proximo));
        int horas = mt / 60;
        int minutos = mt % 60;
        LocalDateTime PernoiteMeio = InicioP.plusHours(6);
        if (MomentoAtual.isBefore(PernoiteMeio)){
           if(horas>6){
            return config.getPernoite_valor();
          }else{
           return CalcularPorHoraServiceEditar.PorHora(config,primeira,horas) + (mint > config.getDuracaoTaxaAdicional() ?CalcularPorHoraServiceEditar.PorTolerancia(config,minutos): 0);
          }
        }else{
         return CalcularPorHoraServiceEditar.PorHora(config,primeira,horas) + (mint > config.getDuracaoTaxaAdicional() ?CalcularPorHoraServiceEditar.PorTolerancia(config,minutos): 0);
        }

     }
}