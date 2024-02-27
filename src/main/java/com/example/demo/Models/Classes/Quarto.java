package com.example.demo.Models.Classes;

import javafx.collections.ObservableArray;

import java.io.Serializable;

public class Quarto implements Serializable {


    private int cod_Quarto;
    private String Num_quarto;
    private String Estado;

    @Override
    public String toString() {
        return Num_quarto;
    }

    public Quarto() {

    }
    public Quarto( String Num_quarto) {
        this.Num_quarto = Num_quarto;

    }
    public Quarto(int cod_Quarto, String Num_quarto, String Estado) {
        this.cod_Quarto = cod_Quarto;
        this.Num_quarto = Num_quarto;
        this.Estado = Estado;

    }
    public int getCod_Quarto() {
        return cod_Quarto;
    }

    public void setCod_Quarto(int cod_Quarto) {
        this.cod_Quarto = cod_Quarto;
    }
    public String getNum_quarto() {
        return Num_quarto;
    }
    public void setNum_quarto(String num_quarto) {
        Num_quarto = num_quarto;
    }

    public String getEstado() {
        return Estado;
    }
    public void setEstado(String estado) {
        Estado = estado;
    }

}

