package com.example.demo.Models.Services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Relatorio {
ExecutarRelatorio relatorio = new ExecutarRelatorio();
    public void Relatorios(String Tipo_Situacao, String Tipo_duracao, LocalDate Data_Inicio, LocalDate Data_Fim) throws ParseException {
    switch (Tipo_Situacao){
        case "Lista de vendas":
            Lista_Vendas(Tipo_duracao,Data_Inicio,Data_Fim);
            break;
        case "Lista de produtos":
            Lista_Produtos(Tipo_duracao,Data_Inicio,Data_Fim);
            break;
    }
    }
    public void Lista_Vendas(String Tipo_duracao, LocalDate Data_Inicio, LocalDate Data_Fim) throws ParseException {
        LocalDateTime hoje = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(),LocalDateTime.now().getDayOfMonth(), 8,0,0);
        System.out.println(Tipo_duracao);
         DateTimeFormatter yyyyMMddHHmmss_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = hoje.format(yyyyMMddHHmmss_DATE_FORMAT);
        Date dt_Hoje = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(formatDateTime);
        switch (Tipo_duracao){
            case "Por 7 dias":
                LocalDateTime fim = hoje.minusDays(7);
                String formatDateT = fim.format(yyyyMMddHHmmss_DATE_FORMAT);
                Date dt_fim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(formatDateT);
                relatorio.ExecutarRelatorioLista_Vendas(dt_fim,dt_Hoje);
                break;
            case "Por 15 dias":
                LocalDateTime fim15 = hoje.minusDays(15);
                String formatDateT15 = fim15.format(yyyyMMddHHmmss_DATE_FORMAT);
                Date dt_fim15 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(formatDateT15);
                relatorio.ExecutarRelatorioLista_Vendas(dt_fim15,dt_Hoje);
                break;
            case "Por 30 dias":
                LocalDateTime fim30 = hoje.minusDays(30);
                String formatDateT30 = fim30.format(yyyyMMddHHmmss_DATE_FORMAT);
                Date dt_fim30 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(formatDateT30);
                relatorio.ExecutarRelatorioLista_Vendas(dt_fim30,dt_Hoje);
                break;

            case "Por datas personalizadas":
                LocalDateTime Init = LocalDateTime.of(Data_Inicio.getYear(),Data_Inicio.getMonth(),Data_Inicio.getDayOfMonth(),8,0,0);
                LocalDateTime End = LocalDateTime.of(Data_Fim.getYear(),Data_Fim.getMonth(),Data_Fim.getDayOfMonth(),8,0,0);
                String formatDateTIP = Init.format(yyyyMMddHHmmss_DATE_FORMAT);
                Date dt_Init = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(formatDateTIP);
                String formatDateTEP = End.format(yyyyMMddHHmmss_DATE_FORMAT);
                Date dt_End = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(formatDateTEP);
                relatorio.ExecutarRelatorioLista_Vendas(dt_Init,dt_End);
                break;

        }


    }
    public void Lista_Produtos(String Tipo_duracao, LocalDate Data_Inicio, LocalDate
            Data_Fim) throws ParseException {
        LocalDateTime hoje = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(),LocalDateTime.now().getDayOfMonth(), 8,0,0);

        DateTimeFormatter yyyyMMddHHmmss_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = hoje.format(yyyyMMddHHmmss_DATE_FORMAT);
        Date dt_Hoje = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(formatDateTime);
        switch (Tipo_duracao){
            case "Por 7 dias":
                LocalDateTime fim = hoje.minusDays(7);
                String formatDateT = fim.format(yyyyMMddHHmmss_DATE_FORMAT);
                Date dt_fim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(formatDateT);

        }
    }
}
