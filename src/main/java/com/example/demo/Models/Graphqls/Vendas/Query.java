package com.example.demo.Models.Graphqls.Vendas;

public class Query {

    public String BuscarRealizando = """
 query {
                 VendaPorQuartoRealizando(Num: %s) {
                     IdVendas
                     dataEntrada
                     dataSaida
                     valorTotal
                     Forma_Pagamento
                     Status
                     Quarto {
                         Num
                         IdQuarto
                     }
                     Funcionario {
                         Id_Funcionarios
                         Nome_Fun
                     }
                 }
             }
""";
}
