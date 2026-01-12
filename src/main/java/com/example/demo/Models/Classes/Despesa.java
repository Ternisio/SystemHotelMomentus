package com.example.demo.Models.Classes;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Despesa implements Serializable {
    private String Codigo_Despesa,Data_despesa_formatado, Tipo_Despesa, Nome_Despesa,Valor_Formatado;
    private LocalDate data_despesa;
    private double valor;

    public Despesa(){}
    public Despesa(Long codigo, LocalDate data, String Tipo_Despesa, String Nome_Despesa, double valor ){
        Codigo_Despesa = codigo.toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Data_despesa_formatado = data.format(formatter);
        this.Tipo_Despesa = Tipo_Despesa;
        this.Nome_Despesa = Nome_Despesa;
        Valor_Formatado = String.format("%.2f",valor);
    }

    public String getTipo_Despesa() {
        return Tipo_Despesa;
    }

    public String getCodigo_Despesa() {
        return Codigo_Despesa;
    }

    public void setCodigo_Despesa(String codigo_Despesa) {
        Codigo_Despesa = codigo_Despesa;
    }

    public String getData_despesa_formatado() {
        return Data_despesa_formatado;
    }

    public void setData_despesa_formatado(String data_despesa_formatado) {
        Data_despesa_formatado = data_despesa_formatado;
    }

    public String getValor_Formatado() {
        return Valor_Formatado;
    }

    public void setValor_Formatado(double valor_Formatado) {
        Valor_Formatado = String.format("%.2f",valor_Formatado) ;
    }

    public void setTipo_Despesa(String tipo_Despesa) {
        Tipo_Despesa = tipo_Despesa;
    }

    public String getNome_Despesa() {
        return Nome_Despesa;
    }

    public void setNome_Despesa(String nome_Despesa) {
        Nome_Despesa = nome_Despesa;
    }

    public LocalDate getData_despesa() {
        return data_despesa;
    }

    public void setData_despesa(LocalDate data_despesa) {
        this.data_despesa = data_despesa;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
