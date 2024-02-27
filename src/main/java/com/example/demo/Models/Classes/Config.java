package com.example.demo.Models.Classes;

import java.io.Serializable;

public class Config implements Serializable {

    private Double PrimeiraHora_valor,SegundaHora_valor, Valor_cada_hora, Pernoite_valor;
    private String Pernoite_inicio,Pernoite_fim, Pernoite_meio;
    public Double getPrimeiraHora_valor() {
        return PrimeiraHora_valor;
    }

    public void setPrimeiraHora_valor(Double primeiraHora_valor) {
        PrimeiraHora_valor = primeiraHora_valor;
    }

    public Double getSegundaHora_valor() {
        return SegundaHora_valor;
    }

    public void setSegundaHora_valor(Double segundaHora_valor) {
        SegundaHora_valor = segundaHora_valor;
    }

    public Double getValor_cada_hora() {
        return Valor_cada_hora;
    }

    public void setValor_cada_hora(Double valor_cada_hora) {
        Valor_cada_hora = valor_cada_hora;
    }

    public Double getPernoite_valor() {
        return Pernoite_valor;
    }

    public void setPernoite_valor(Double pernoite_valor) {
        Pernoite_valor = pernoite_valor;
    }

    public String getPernoite_inicio() {
        return Pernoite_inicio;
    }

    public void setPernoite_inicio(String pernoite_inicio) {
        Pernoite_inicio = pernoite_inicio;
    }

    public String getPernoite_fim() {
        return Pernoite_fim;
    }

    public void setPernoite_fim(String pernoite_fim) {
        Pernoite_fim = pernoite_fim;
    }

    public String getPernoite_meio() {
        return Pernoite_meio;
    }


    public void setPernoite_meio(String pernoite_meio) {
        Pernoite_meio = pernoite_meio;
    }
}
