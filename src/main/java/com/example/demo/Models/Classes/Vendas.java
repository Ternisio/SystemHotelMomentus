package com.example.demo.Models.Classes;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Vendas  extends Quarto{
    private String Cod_venda, status, formatado_Data_Entrada, Formatado_Data_hora_Saida,Nome_fun, totalV;



    private LocalDateTime  Data_hora_Entrada, Data_hora_Saida;
    private double total;
    public Vendas(){}
    public Vendas( String status,String Num_quarto, LocalDateTime Data_hora_Entrada, LocalDateTime Data_hora_Saida){
    super(Num_quarto);
    this.status = status;
    this.Data_hora_Entrada = Data_hora_Entrada;
    this.Data_hora_Saida = Data_hora_Saida;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        if(Data_hora_Saida == null){
            this.Formatado_Data_hora_Saida = "";
            this.formatado_Data_Entrada = Data_hora_Entrada.format(formatter);
        }else {
            this.Formatado_Data_hora_Saida = Data_hora_Saida.format(formatter);
            this.formatado_Data_Entrada = Data_hora_Entrada.format(formatter);
        }
    }
    public Vendas( String Cod_venda,String status,String Nome_fun,String Num_quarto, LocalDateTime Data_hora_Entrada, LocalDateTime Data_hora_Saida, double totalV) {
        super(Num_quarto);
        this.Cod_venda = Cod_venda;
        this.status = status;
        this.Nome_fun = Nome_fun;
        this.Data_hora_Entrada = Data_hora_Entrada;
        this.Data_hora_Saida = Data_hora_Saida;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        if (Data_hora_Saida == null) {
            this.Formatado_Data_hora_Saida = "";
            this.formatado_Data_Entrada = Data_hora_Entrada.format(formatter);
        } else {
            this.Formatado_Data_hora_Saida = Data_hora_Saida.format(formatter);
            this.formatado_Data_Entrada = Data_hora_Entrada.format(formatter);
        }
        this.totalV = String.format("%.2f",totalV);
    }
    public String getCod_venda() {
        return Cod_venda;
    }

    public void setCod_venda(String cod_venda) {
        Cod_venda = cod_venda;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getData_hora_Entrada() {
        return Data_hora_Entrada;
    }

    public void setData_hora_Entrada(LocalDateTime data_hora_Entrada) {
        Data_hora_Entrada = data_hora_Entrada;
    }

    public LocalDateTime getData_hora_Saida() {
        return Data_hora_Saida;
    }

    public void setData_hora_Saida(LocalDateTime data_hora_Saida) {
        Data_hora_Saida = data_hora_Saida;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getFormatado_Data_Entrada() {
        return formatado_Data_Entrada;
    }

    public void setFormatado_Data_Entrada(String formatado_Data_Entrada) {
        this.formatado_Data_Entrada = formatado_Data_Entrada;
    }

    public String getFormatado_Data_hora_Saida() {
        return Formatado_Data_hora_Saida;
    }

    public void setFormatado_Data_hora_Saida(String formatado_Data_hora_Saida) {
        Formatado_Data_hora_Saida = formatado_Data_hora_Saida;
    }

    public String getNome_fun() {
        return Nome_fun;
    }

    public String getTotalV() {
        return totalV;
    }

    public void setTotalV(String totalV) {
        this.totalV = totalV;
    }

    public void setNome_fun(String nome_fun) {
        Nome_fun = nome_fun;
    }
}
