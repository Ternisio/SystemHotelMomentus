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

public class CalcularPorPernoiteService implements Calcular {
    public double ValorPeriodoDiaria(LocalDateTime Hora_entrada, LocalDateTime Hora_Saida, Config config) {
        double ValorPernoite = 0.0;
        LocalDateTime Proximo = Hora_entrada;
        System.out.print(Proximo + "\n");
        for (LocalDateTime dateTime = Proximo; dateTime.isBefore(Hora_Saida); dateTime = dateTime.plusDays(1)) {
            LocalDate Data_entrada = dateTime.toLocalDate();


            LocalDateTime DataP = LocalDateTime.of(Data_entrada.getYear(), Data_entrada.getMonth(), Data_entrada.getDayOfMonth(), 18, 00);
            ;

            if (dateTime.isBefore(DataP) && (Hora_Saida.isAfter(DataP) || DataP.isBefore(Hora_Saida))) {
                int minutop = (int) ChronoUnit.MINUTES.between(DataP, Hora_Saida);
                System.out.print(minutop + "\n\n");
                if ((minutop >= config.getTolerancia() || minutop <= 60) && minutop < 480) {
                    ValorPernoite = ValorPernoite + (config.getPernoite_valor() / 2);

                } else if (minutop > 480) {
                    ValorPernoite = ValorPernoite + config.getPernoite_valor();
                }
            }

        }
        return ValorPernoite;
    }

    @Override
    public double calcular(Config config, Vendas vendas, Label duracao, Button botao) {
        LocalDateTime Hora_entrada = vendas.getData_hora_Entrada();
        LocalTime tpi = LocalTime.parse(config.getPernoite_inicio());
        LocalTime tpf = LocalTime.parse(config.getPernoite_fim());
        LocalTime duracaotempo = LocalTime.parse(config.getPernoite_meio());
        LocalDateTime Hora_Saida = LocalDateTime.now();
        LocalDate Data_entrada = Hora_entrada.toLocalDate();
        int minduracao = duracaotempo.getHour()*60 + duracaotempo.getMinute();
        int  mintotal = (int) (ChronoUnit.MINUTES.between(tpi,tpf));
        LocalDateTime DataP = LocalDateTime.of(Data_entrada.getYear(), Data_entrada.getMonth(), Data_entrada.getDayOfMonth(), tpi.getHour(), tpi.getMinute());
        double ValorTotal = 0.0;
        int min = (int) (ChronoUnit.MINUTES.between(Hora_entrada, Hora_Saida));
        MostrarDuraçãoHoras(duracao,min);
            if (config.getTolerancia() > min){
                botao.setText("Cancelar");
                botao.setStyle("-fx-background-color: red;");
            }
            else if (min > minduracao && mintotal>=min  ) {
         ValorTotal = config.getPernoite_valor();
            } else {
                if (min >= config.getTolerancia() && 60>=min) {
                    botao.setText("Finalizar");
                    botao.setStyle("-fx-background-color:  linear-gradient(to left, #6441a5, #2a0845);");
                    ValorTotal = config.getPrimeiraHora_valor();
                }
                else if (min > 60 && min < 120) {
                    ValorTotal = config.getSegundaHora_valor();
                }
                else if (min > 120 ) {
                    double valorInicial = config.getSegundaHora_valor(); // Valor inicial para 2 horas
                    double valorPorIntervalo = config.getValor_cada_hora(); // Valor por intervalo de 15 a 60 minutos

                    long intervalosRestantes = (min - (2 * 60)) / 45; // Número de intervalos restantes

                    ValorTotal = valorInicial + (intervalosRestantes * valorPorIntervalo);



                }
            }
        return ValorTotal;
    }

    @Override
    public double calcular(Config config, Vendas vendas, Label duracao) {
        return 0;
    }

    void MostrarDuraçãoHoras(Label duracao, int min){
        String MostrarHoras = "";
        String MostrarMinutos = "";
        int valorHoras = min/60;
        int valorMinutos = min%60;
        if (10 > valorHoras){
            MostrarHoras = "0"+ valorHoras;
        }else{
            MostrarHoras = MostrarHoras + valorHoras;

        }
        if (9 > valorMinutos){
            MostrarMinutos = "0"+ valorMinutos;
        }else{
            MostrarMinutos = MostrarMinutos + valorMinutos;

        }

        duracao.setText(MostrarHoras+":"+MostrarMinutos);


    }
}