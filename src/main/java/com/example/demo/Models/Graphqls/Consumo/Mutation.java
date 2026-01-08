package com.example.demo.Models.Graphqls.Consumo;

public class Mutation {
 public String Consumo_atual = """
                   mutation {
         AtualizarConsumo(input: {
            IdVendas: "%s",
           IdProduto: "%s",
           Quantidade: %s
             })
         }
         
         """;

 public String Consumo_add = """
                   mutation {
         CadastrarConsumo(input: {
            IdVendas: "%s",
           IdProduto: "%s",
           Quantidade: %s,
           Data_C: "%s"  })
         
         }
         """;
 public String AtualizarEstoques = """
                   mutation {
         AtualizarEstoques(input: {
           Idprodutos: "%s",
           Quantidade: %s })
         
         }
         """;
 public String Consumo_Excluir = """
                   mutation {
         ExcluirConsumo(input: {
            IdVendas: "%s",
           IdProduto: "%s"
             })
         
         }
         """;

}

