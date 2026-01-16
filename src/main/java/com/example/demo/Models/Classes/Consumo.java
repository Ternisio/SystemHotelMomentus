package com.example.demo.Models.Classes;

import com.example.demo.Models.Interfaces.VendaInterface;
import javafx.collections.ObservableList;

import java.io.Serializable;

public class Consumo extends Produto  {
    int idConsumo;
    int Qtd;
    private Vendas vendas;
    String num_Quarto;
    public Consumo() {
}

    public Vendas getVendas() {
        return vendas;
    }

    public void setVendas(Vendas vendas) {
        this.vendas = vendas;
    }

    public Consumo(int idConsumo, String idProduto, String Nome_prod, String ExibirValor, int Qtd){
super(idProduto,Nome_prod,ExibirValor);
this.idConsumo=idConsumo;
this.Qtd =Qtd;
}
    public Consumo( String num_Quarto,String ExibirValor){
        super(ExibirValor);
        this.num_Quarto = num_Quarto;

    }


    public int getIdConsumo() {
        return idConsumo;
    }

    public void setIdConsumo(int idConsumo) {
        this.idConsumo = idConsumo;
    }

    public int getQtd() {
        return Qtd;
    }

    public void setQtd(int qtd) {
        Qtd = qtd;
    }

    public String getNum_Quarto() {
        return num_Quarto;
    }

    public void setNum_Quarto(String num_Quarto) {
        this.num_Quarto = num_Quarto;
    }




}
