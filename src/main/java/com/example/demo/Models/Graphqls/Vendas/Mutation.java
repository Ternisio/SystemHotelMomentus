package com.example.demo.Models.Graphqls.Vendas;

public class Mutation {
  String query = """
        mutation ($list:InputVenda) {
            AtualizarVenda(inputVenda: $list)
        }
        """;
  public String Vendas_Cadastrar= """
            mutation ($list:InputVenda) {
            AtualizarVenda(inputVenda: $list)
        }
          
            """;
}
