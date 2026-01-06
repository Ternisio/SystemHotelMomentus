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

public class CalcularPorPernoiteServiceEditar implements Calcular {
    public  double ValorPeriodoDiaria(LocalDateTime Hora_entrada, LocalDateTime Hora_Saida, Config config)  {
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
        Double ValorPernoite = 0.00;
        LocalDateTime EntradaDataHora = vendas.getData_hora_Entrada();
        LocalDateTime SaidaDataHora = vendas.getData_hora_Saida();
        LocalTime tpi = LocalTime.parse(config.getPernoite_inicio());
        LocalTime tpf = LocalTime.parse(config.getPernoite_fim());
        LocalTime tpM = LocalTime.parse(config.getPernoite_meio());
        LocalDate Data_entrada = EntradaDataHora.toLocalDate();
        LocalDateTime DataPF = null, DataPPF = null, DataNovaI = null;
        if ((EntradaDataHora.getHour() == 0 || tpf.getHour()>EntradaDataHora.getHour()) ){
            DataPF = LocalDateTime.of(Data_entrada.getYear(), Data_entrada.getMonth(), Data_entrada.getDayOfMonth(), tpf.getHour(), tpf.getMinute());
            DataPPF = LocalDateTime.of(Data_entrada.getYear(), Data_entrada.getMonth(), Data_entrada.getDayOfMonth(), tpM.getHour(), tpM.getMinute());

        } else {
            LocalDate DataMaisdia = EntradaDataHora.toLocalDate().plusDays(1);
             DataPF = LocalDateTime.of(DataMaisdia.getYear(), DataMaisdia.getMonth(), DataMaisdia.getDayOfMonth(), tpf.getHour(), tpf.getMinute());
             DataPPF = LocalDateTime.of(DataMaisdia.getYear(), DataMaisdia.getMonth(), DataMaisdia.getDayOfMonth(), tpM.getHour(), tpM.getMinute());


        }

        LocalDateTime DataPI = LocalDateTime.of(SaidaDataHora.getYear(), SaidaDataHora.getMonth(), SaidaDataHora.getDayOfMonth(), tpi.getHour(), tpi.getMinute());


        int  min = (int) ChronoUnit.MINUTES.between(EntradaDataHora,SaidaDataHora);
        MostrarDuraçãoHoras(duracao,min);
        if (((min >= config.getTolerancia() & 70 >=min) )){
            ValorPernoite = ValorPernoite + (config.getPernoite_valor()/2);
        } else if (min>480 && EntradaDataHora.isBefore(DataPPF) && SaidaDataHora.isBefore(DataPF)) {
            ValorPernoite = ValorPernoite + config.getPernoite_valor();
        }else if((EntradaDataHora.getHour() == tpM.getHour() || EntradaDataHora.getHour() == tpM.getHour() + 1 ) && SaidaDataHora.isBefore(DataPF.plusHours(1))){
            ValorPernoite = ValorPernoite + (config.getPernoite_valor() );
        } else if ((EntradaDataHora.isAfter(DataPPF)  & EntradaDataHora.getHour() == 2 & SaidaDataHora.isBefore(DataPF) )  ){
            ValorPernoite = ValorPernoite + config.getPernoite_valor();
        }else if((EntradaDataHora.getHour() == 0 || EntradaDataHora.getHour() == 1) && SaidaDataHora.isAfter(DataPF.plusHours(1))& SaidaDataHora.isBefore(DataPI)){
            int  minph = (int) ChronoUnit.MINUTES.between(DataPF.plusHours(1),SaidaDataHora);
            if (minph == 15 && minph <= 60){
                ValorPernoite = config.getPernoite_valor() + config.getValor_cada_hora();
            } else  {
               int convertporhora = minph/60;
               ValorPernoite = config.getPernoite_valor() + (convertporhora * config.getValor_cada_hora());

            }

        }else if(SaidaDataHora.isAfter(DataPF)& min >= 720 & 1440>=min){
        int horasT = (int) ChronoUnit.MINUTES.between(SaidaDataHora,DataPF)/config.getDuracaoTaxaAdicional();
        double valor_cada_hora = config.getValor_cada_hora();
        ValorPernoite = config.getPernoite_valor() + (valor_cada_hora*horasT);
        }
            else if(min >= 1440 ){
            int qtspernoites= (min/ config.getDuracaoTaxaAdicional())/12;
            int horastrabalhadas = (min/config.getDuracaoTaxaAdicional())-(12 * qtspernoites + 2);


            double pernoitestrabalhadas = ValorPeriodoDiaria(EntradaDataHora, SaidaDataHora, config);
            ValorPernoite = pernoitestrabalhadas + (config.getValor_cada_hora() * horastrabalhadas);


        }

        return ValorPernoite;

    }

    @Override
    public double calcular(Config config, Vendas vendas, Label duracao) {
        LocalDateTime Hora_entrada = vendas.getData_hora_Entrada();
        LocalTime tpi = LocalTime.parse(config.getPernoite_inicio());
        LocalDateTime Hora_Saida = vendas.getData_hora_Saida();
        LocalDate Data_entrada = Hora_entrada.toLocalDate();
        LocalDateTime DataP = LocalDateTime.of(Data_entrada.getYear(), Data_entrada.getMonth(), Data_entrada.getDayOfMonth(), 18, 00);
        double ValorTotal = 0.0;
        int min = (int) (ChronoUnit.MINUTES.between(Hora_entrada, Hora_Saida));
        MostrarDuraçãoHoras(duracao,min);

        if (min >= 15 && min <= 60) {
            ValorTotal = config.getPrimeiraHora_valor();
        } else if (15>= min) {

        } else if (min > 60 && min < 120) {
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
