package com.example.demo.Models.Classes;

import java.io.Serializable;

public class Funcionario implements Serializable {
private String Id_Fun, Nome_Fun,Senha_fun, Tipo_Fun, Cpf_Fun, Nome_Foto;
public Funcionario(){

}

    public String getId_Fun() {
        return Id_Fun;
    }

    public void setId_Fun(String id_Fun) {
        Id_Fun = id_Fun;
    }

    public String getNome_Fun() {
        return Nome_Fun;
    }

    public void setNome_Fun(String nome_Fun) {
        Nome_Fun = nome_Fun;
    }

    public String getTipo_Fun() {
        return Tipo_Fun;
    }

    public void setTipo_Fun(String tipo_Fun) {
        Tipo_Fun = tipo_Fun;
    }

    public String getSenha_fun() {
        return Senha_fun;
    }

    public void setSenha_fun(String senha_fun) {
        Senha_fun = senha_fun;
    }
    public String getCpf_Fun() {
        return Cpf_Fun;
    }

    public void setCpf_Fun(String cpf_Fun) {
        Cpf_Fun = cpf_Fun;
    }

    public String getNome_Foto() {
        return Nome_Foto;
    }

    public void setNome_Foto(String nome_Foto) {
        Nome_Foto = nome_Foto;
    }

}
