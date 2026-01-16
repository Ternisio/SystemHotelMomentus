package com.example.demo.Models.Graphqls.Despesa;

public class Mutation {
    public static String despesa = "{Nome:\"%s\",Tipo:\"%s\",Valor:%s,Data:\"%s\"}";

    public static String Cadastrar = "{\"query\":\"mutation {CadastrarDespesasList(despesasList:[%s])}\"}";

}
