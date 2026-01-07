package com.example.demo.Models.Graphqls.Vendas;

public class Mutation {

  public String Vendas_Cadastrar= """
            mutation {
  AtualizarVenda(inputVenda: {
    idVenda: "%s",
    idFun: "%s",
    idQuarto: "%s",
    Data_Inicio: "%s",
    Data_fim: "%s",
    Status: "%s",
    Total: %s,
    Pagamento: "%s"
  })
            """;
}
