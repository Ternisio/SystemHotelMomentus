package com.example.demo.Models.Interfaces;

import com.example.demo.Models.Classes.Produto;
import com.example.demo.Models.Dao.ProdutoDao;

import java.io.File;

public interface AdminProdutoInterface {
    public String cod_Produto(String categoria, ProdutoDao produtoDao);
    public boolean VerificarCampos(String Nome, String Tipo, String Valor, String estoque);
    public void Excluir_e_Editar(boolean Excluir, boolean Editar, ProdutoDao produtoDao, Produto produto, File file);
    public void Excluir_Tudo(ProdutoDao produtoDao);
    public String categoria(ProdutoDao produtoDao,String Bebidas);


}
