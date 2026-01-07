package com.example.demo.Models.Graphqls.Consumo;

public class Query {
    public String ListaPorCliente =
            """
            query {
            TodosConsumosPorIdVenda(idvendas: "%s") {
                            Id_Consumo
                            Data_C
                            Quantidade
                            IdProduto {
                                idProduto
                                nome
                                valor
                              
                            }
                        }
                        }
             
                    """;

    public String QuantidadeProduto = """
            query {
 QuantidadeProduto(idProduto: "%s") 
            }
            
            """;
}
