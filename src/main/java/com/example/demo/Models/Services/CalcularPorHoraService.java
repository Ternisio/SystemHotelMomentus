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

public  class CalcularPorHoraService implements Calcular {

    public static double ValorPeriodoDiaria(LocalDateTime Hora_entrada, LocalDateTime Hora_Saida, Config config)  {
        double ValorPernoite = 0.0;
        LocalDateTime Proximo = Hora_entrada;
        System.out.print(Proximo + "\n");
        for (LocalDateTime dateTime = Proximo; dateTime.isBefore(Hora_Saida); dateTime = dateTime.plusDays(1)) {
            LocalDate Data_entrada = dateTime.toLocalDate();


            LocalDateTime DataP = LocalDateTime.of(Data_entrada.getYear(), Data_entrada.getMonth(), Data_entrada.getDayOfMonth(), 18, 00);;

            if (dateTime.isBefore(DataP) && (Hora_Saida.isAfter(DataP) || DataP.isBefore(Hora_Saida))) {
                int minutop = (int) ChronoUnit.MINUTES.between(DataP, Hora_Saida);
                System.out.print(minutop + "\n\n");
                if ((minutop >= 15 || minutop <= 60) && minutop < 480) {
                    ValorPernoite = ValorPernoite + (config.getPernoite_valor()/2) ;

                } else if (minutop > 480) {
                    ValorPernoite = ValorPernoite + config.getPernoite_valor();
                }
            } else {
                ValorPernoite = 70.00;
            }

        }
        return ValorPernoite;
    }

    @Override
    public double calcular(Config config, Vendas vendas, Label duracao, Button botao) {
        LocalDateTime Hora_entrada = vendas.getData_hora_Entrada();
        LocalTime tpi = LocalTime.parse(config.getPernoite_inicio());
        LocalDateTime Hora_Saida = LocalDateTime.now();
        LocalDate Data_entrada = Hora_entrada.toLocalDate();
        LocalDateTime DataP = LocalDateTime.of(Data_entrada.getYear(), Data_entrada.getMonth(), Data_entrada.getDayOfMonth(), 18, 00);
        double ValorTotal = 0.0;
        int min = (int) (ChronoUnit.MINUTES.between(Hora_entrada, Hora_Saida));
        MostrarDuraçãoHoras(duracao,min);
        if(config.getTolerancia() > min){
            botao.setText("Cancelar");
            botao.setStyle("-fx-background-color: red;");
        }else {
            botao.setText("Finalizar");
            botao.setStyle("-fx-background-color:  linear-gradient(to left, #6441a5, #2a0845);");
        }
        if (min >= config.getTolerancia() && min <= 70) {
            ValorTotal = config.getPrimeiraHora_valor();
        } else if (min > 70 && min < 120) {
            ValorTotal = config.getSegundaHora_valor();
        } else if (min > 120 ) {
            double valorInicial = config.getSegundaHora_valor(); // Valor inicial para 2 horas
            double valorPorIntervalo = config.getValor_cada_hora(); // Valor por intervalo de 15 a 60 minutos

            long intervalosRestantes = (min - 120) / config.getDuracaoTaxaAdicional(); // Número de intervalos restantes

            ValorTotal = valorInicial + (intervalosRestantes * valorPorIntervalo);
            int MinTol = min%config.getDuracaoTaxaAdicional();
            if(config.getTolerancia() > MinTol){
                ValorTotal = ValorTotal + config.getValor_cada_hora();
            }




        } else if (Hora_entrada.isBefore(DataP) && Hora_Saida.isAfter(DataP) && min < 1440) {
            double ValorPernoite = 0.0;
            double valorInicial = 0.0;
            double valorH = 0.0;
            long minutop = ChronoUnit.MINUTES.between(DataP, Hora_Saida);
            if (minutop >= 15 || minutop <= 60 || minutop < 480) {
                ValorPernoite = config.getPernoite_valor()/2;
            } else {
                ValorPernoite = config.getPernoite_valor();
            }
            int fim_hora = tpi.getHour()  - 1;
            LocalDateTime Hrfim = LocalDateTime.of(vendas.getData_hora_Entrada().getYear(), vendas.getData_hora_Entrada().getMonth(), vendas.getData_hora_Entrada().getDayOfMonth(), fim_hora, 0);
            long minutoH = ChronoUnit.MINUTES.between(Hora_entrada, Hrfim);
            if(minutoH>= config.getTolerancia() && 60>=minutoH){
                valorInicial = config.getPrimeiraHora_valor();
            }else if(minutoH>=120){
                valorInicial = config.getSegundaHora_valor();
            }else {
                valorInicial = config.getSegundaHora_valor(); // Valor inicial para 2 horas
                double valorPorIntervalo = config.getValor_cada_hora(); // Valor por intervalo de 60 minutos
                int intervalosRestantes = (int) ((minutoH - 120) / config.getDuracaoTaxaAdicional()); // Número de intervalos restantes

                valorH = valorInicial + (intervalosRestantes * valorPorIntervalo);
                int MinTol = min%config.getDuracaoTaxaAdicional();
                if(config.getTolerancia() > MinTol){
                    valorH = valorH + config.getValor_cada_hora();
                }
            }
            ValorTotal = ValorPernoite + valorH;


        } else if (min >= 1440){
            long dias = ChronoUnit.DAYS.between(Hora_entrada, Hora_Saida);
            double duasvalor = config.getSegundaHora_valor();
            double taxaHoraria = config.getValor_cada_hora(); // Exemplo de taxa horária

            long horasTrabalhadas = ((min / config.getDuracaoTaxaAdicional()) - (12*dias)) - 2; // Exemplo de horas trabalhadas

            double valorHorasTrabalhadas = horasTrabalhadas * taxaHoraria;
            double valorNoitesPernoitadas = ValorPeriodoDiaria(Hora_entrada, Hora_Saida, config);
            ValorTotal = duasvalor + valorHorasTrabalhadas + valorNoitesPernoitadas;
        }
        return ValorTotal;
    }
    @Override
    public double calcular(Config config, Vendas vendas, Label duracao) {
        LocalDateTime Hora_entrada = vendas.getData_hora_Entrada();
        LocalTime tpi = LocalTime.parse(config.getPernoite_inicio());
        LocalDateTime Hora_Saida = LocalDateTime.now();
        LocalDate Data_entrada = Hora_entrada.toLocalDate();
        LocalDateTime DataP = LocalDateTime.of(Data_entrada.getYear(), Data_entrada.getMonth(), Data_entrada.getDayOfMonth(), 18, 00);
        double ValorTotal = 0.0;
        int min = (int) (ChronoUnit.MINUTES.between(Hora_entrada, Hora_Saida));
        MostrarDuraçãoHoras(duracao,min);

        if (min >= 15 && min <= 70) {
         ValorTotal = config.getPrimeiraHora_valor();
        } else if (min > 70 && min < 120) {
            ValorTotal = config.getSegundaHora_valor();
        } else if (min > 120 ) {
            double valorInicial = config.getSegundaHora_valor(); // Valor inicial para 2 horas
            double valorPorIntervalo = config.getValor_cada_hora(); // Valor por intervalo de 15 a 60 minutos

            long intervalosRestantes = (min - (2 * 60)) / 45; // Número de intervalos restantes

            ValorTotal = valorInicial + (intervalosRestantes * valorPorIntervalo);



        } else if (Hora_entrada.isBefore(DataP) && Hora_Saida.isAfter(DataP) && min < 1440) {
            double ValorPernoite = 0.0;
            double valorInicial = 0.0;
            double valorH = 0.0;
            long minutop = ChronoUnit.MINUTES.between(DataP, Hora_Saida);
            if (minutop >= 15 || minutop <= 60 || minutop < 480) {
                ValorPernoite = config.getPernoite_valor()/2;
            } else {
                ValorPernoite = config.getPernoite_valor();
            }
            int fim_hora = tpi.getHour()  - 1;
            LocalDateTime Hrfim = LocalDateTime.of(vendas.getData_hora_Entrada().getYear(), vendas.getData_hora_Entrada().getMonth(), vendas.getData_hora_Entrada().getDayOfMonth(), fim_hora, 0);
            long minutoH = ChronoUnit.MINUTES.between(Hora_entrada, Hrfim);
                if(minutoH>= 15 && 60>=minutoH){
                    valorInicial = config.getPrimeiraHora_valor();
                }else if(minutoH>=120){
                    valorInicial = config.getSegundaHora_valor();
                }else {
                    valorInicial = config.getSegundaHora_valor(); // Valor inicial para 2 horas
                    double valorPorIntervalo = config.getValor_cada_hora(); // Valor por intervalo de 60 minutos
                     int intervalosRestantes = (int) (minutoH - (2 * 60)) / 45; // Número de intervalos restantes

                     valorH = valorInicial + (intervalosRestantes * valorPorIntervalo);
                }
            ValorTotal = ValorPernoite + valorH;


        } else if (min >= 1440){
            long dias = ChronoUnit.DAYS.between(Hora_entrada, Hora_Saida);
            double duasvalor = config.getSegundaHora_valor();
            double taxaHoraria = config.getValor_cada_hora(); // Exemplo de taxa horária

            long horasTrabalhadas = ((min / 60) - (12*dias)) - 2; // Exemplo de horas trabalhadas

            double valorHorasTrabalhadas = horasTrabalhadas * taxaHoraria;
            double valorNoitesPernoitadas = ValorPeriodoDiaria(Hora_entrada, Hora_Saida, config);
             ValorTotal = duasvalor + valorHorasTrabalhadas + valorNoitesPernoitadas;
        }
return ValorTotal;
    }
    void MostrarDuraçãoHoras(Label duracao, int min){
     String MostrarHoras = "";
     String MostrarMinutos = "";
    int valorHoras = min/60;
    int valorMinutos = min%60;
    int valorDias = valorHoras/24;
    int valorHorasRestantes = valorHoras%24;

    if (valorDias > 1){
        duracao.setText(String.format("%d dias, %02d:%02d",valorDias,valorHorasRestantes,valorMinutos ));
    }else if(valorDias == 1){
        duracao.setText(String.format("%d dia, %02d:%02d",valorDias,valorHorasRestantes,valorMinutos ));
    }else{
        duracao.setText(String.format("%02d:%02d",valorHorasRestantes,valorMinutos ));
    }



    }
}
