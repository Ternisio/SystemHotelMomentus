package com.example.demo.Models.Services;

import com.example.demo.Models.Classes.Config;
import com.example.demo.Models.Classes.Vendas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class CalculadoraGeral {
    public double calcular(Config config, Vendas vendas, Label duracao, Button botao) {
        LocalDateTime  Inicio = vendas.getData_hora_Entrada();
        LocalDateTime  Fim = LocalDateTime.now();
        LocalTime HoraInicioP = LocalTime.parse(config.getPernoite_inicio());
        LocalTime HoraFimP = HoraInicioP.plusHours(12);
        LocalDateTime Limite;
        LocalDateTime InicioP = LocalDateTime.of(
                Inicio.getYear(),
                Inicio.getMonth(),
                Inicio.getDayOfMonth(),
                HoraInicioP.getHour(),
                HoraInicioP.getMinute());

                if (Inicio.toLocalTime().isBefore(HoraFimP)){
                  InicioP =  InicioP.minusDays(1);
                }
    LocalDateTime FimP = InicioP.plusHours(12);
    LocalDateTime MomentoAtual = Inicio;
    double valor = 0.0;
    int mint = (int) (ChronoUnit.MINUTES.between(Inicio, Fim));
   MostrarDuracao(duracao,mint);
    boolean primeira = true;

        if (config.getTolerancia() > mint){
            botao.setText("Cancelar");
            botao.setStyle("-fx-background-color: red;");
            return 0;
        }else{
            botao.setText("Finalizar");
            botao.setStyle("-fx-background-color:  linear-gradient(to left, #6441a5, #2a0845);");
        }
    while (MomentoAtual.isBefore(Fim)){

        if(MomentoAtual.isBefore(InicioP)){
             Limite = (Fim.isBefore(InicioP))?Fim:InicioP;
            long difff = Duration.between(MomentoAtual, Limite).toMinutes();
             int horas = (int) (difff / config.getDuracaoTaxaAdicional());
             int minutos = (int) (difff % config.getDuracaoTaxaAdicional());
             valor += CalcularPorHoraServiceEditar.PorHora(config,primeira,horas);
             valor += mint>config.getDuracaoTaxaAdicional() ? CalcularPorHoraServiceEditar.PorTolerancia(config,minutos): 0;

             MomentoAtual = Limite;

        }

        if((MomentoAtual.isAfter(InicioP) || MomentoAtual.isEqual(InicioP)) && MomentoAtual.isBefore(FimP)){
            valor += CalcularPorPernoiteService.PorPernoite(MomentoAtual,config,Fim,InicioP,FimP,primeira, mint);
            Limite = (Fim.isBefore(FimP))?Fim:FimP;
            MomentoAtual = Limite;
        }
        if(MomentoAtual.isAfter(FimP)){
            InicioP = FimP.plusHours(12);
            FimP = InicioP.plusHours(12);
            Limite = (Fim.isBefore(InicioP))?Fim:InicioP;
            long difff = Duration.between(MomentoAtual, Limite).toMinutes();
             int horas = (int) (difff / config.getDuracaoTaxaAdicional());
             int minutos = (int) (difff % config.getDuracaoTaxaAdicional());
             valor += CalcularPorHoraServiceEditar.PorHora(config,primeira,horas);
             valor += CalcularPorHoraServiceEditar.PorTolerancia(config,minutos);
            MomentoAtual = Limite;
        }
    }
    return valor;
    }

    void MostrarDuracao(Label duracao, int min){
        int horas = min / 60;
        int minutos = min % 60;
        int dias = horas / 24;
        if(dias== 1){
            duracao.setText( String.format("1 dia, %02d:%02d", horas,minutos));
        }else if(dias>1){
            duracao.setText( String.format("%d dias, %02d:%02d", dias, horas,minutos));
        }else {
        duracao.setText( String.format("%02d:%02d", horas, minutos));}
    }
}
