package com.example.demo.Models.Graphqls.Produtos;

public class Query {

    public String ProdutosComEstoques = """
        query {
        ProdutosComEstoques {
                            idProduto
                            nome
                            valor
                            quantidade
                            categoria
                            Nome_Foto
                        }
    }
""";
    public String TodosProdutos = """
        query {
        TodosProdutos {
                            idProduto
                            nome
                            valor
                            quantidade
                            categoria
                            Nome_Foto
                        }
    }
""";
}
