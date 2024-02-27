package com.example.demo.Models.Classes;

import java.io.Serializable;

public class Produto extends Vendas implements Serializable {

  private   String idProduto, Nome_prod, Tipo_Produto, ExibirValor;


    private int estoques;
    private float valor;
    private String Foto;
    public Produto(){}
    public Produto(String idProduto, String Nome_prod, String ExibirValor){
        this.idProduto = idProduto;
        this.Nome_prod = Nome_prod;
        this.ExibirValor = ExibirValor;
    }

    public Produto(String Nome_prod, float valor ){this.Nome_prod = Nome_prod; this.valor = valor;}

    public Produto(String ExibirValor) {
        this.ExibirValor = ExibirValor;
    }

    public String getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(String idProduto) {
        this.idProduto = idProduto;
    }

    public String getNome_prod() {
        return Nome_prod;
    }

    public void setNome_prod(String nome_prod) {
        Nome_prod = nome_prod;
    }

    public String getTipo_Produto() {
        return Tipo_Produto;
    }

    public void setTipo_Produto(String tipo_Produto) {
        Tipo_Produto = tipo_Produto;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public int getEstoques() {
        return estoques;
    }

    public void setEstoques(int estoques) {
        this.estoques = estoques;
    }

    public String getFoto() {
        return Foto;
    }

    public void setFoto(String foto) {
        Foto = foto;
    }


    public String getExibirValor() {
        return ExibirValor;
    }

    public void setExibirValor(String exibirValor) {
        ExibirValor = exibirValor;
    }
}
