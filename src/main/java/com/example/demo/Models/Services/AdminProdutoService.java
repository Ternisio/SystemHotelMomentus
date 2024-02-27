package com.example.demo.Models.Services;

import com.example.demo.Models.Classes.Produto;
import com.example.demo.Models.Dao.ProdutoDao;

import com.example.demo.Models.Interfaces.AdminProdutoInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.File;
import java.util.Optional;

public class AdminProdutoService implements AdminProdutoInterface {

    public boolean VerificarCampos(String Nome, String Tipo, String Valor, String estoque) {
        if (Nome.equals("") || Tipo.equals("Selecione o tipo") || Valor.equals("") || estoque.equals("")) {
            return true;
        } else {

            return false;
        }


    }

    public String cod_Produto(String categoria, ProdutoDao produtoDao) {
        String resultado = "";
        switch (categoria) {
            case "Bebidas":
                resultado = categoria(produtoDao, categoria);
                break;
            case "Pratos":
                resultado = categoria(produtoDao, categoria, 1);
                break;

            case "Itens":
                resultado = categoria(produtoDao, 1, categoria);
                break;

            case "Produtos eroticos":
                resultado = categoria(categoria, produtoDao, 1);
                break;
            case "Diversos":
                resultado = categoria(categoria, produtoDao);
                break;

        }
        return resultado;

    }

    public void Excluir_e_Editar(boolean Excluir, boolean Editar, ProdutoDao produtoDao, Produto produto, File file){
        if(Editar == true && Excluir == false){
            if(file != null){
                if (produto.getFoto() != null){

                    File arquivoAtual = new File("Fotos_prod/" +produto.getFoto());
                    boolean c = arquivoAtual.delete();
                    System.out.println("Deletado"+c);
                    produtoDao.Editar(produto,file);

                }else {
                    produtoDao.Editar(produto,file);
                }
            }else {
                produtoDao.Editar(produto);
            }
            }

        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mensagem");
            alert.setHeaderText("Pergunta:");
            alert.setContentText("Tem certeza que você quer excluir o produto: "+produto.getNome_prod()+" ?\n"+
                    "Para confirmar Sim, clique OK\n" +
                    "Para confirmar Não, clique CANCEL");
            Optional<ButtonType> confirma = alert.showAndWait();

            if(confirma.get() == ButtonType.OK){
                if (produto.getFoto() != null){

                    File arquivoAtual = new File("Fotos_prod/" +produto.getFoto());
                    boolean c = arquivoAtual.delete();
                    System.out.println("Deletado"+c);
                    produtoDao.Excluir(produto);

                }else {
                    produtoDao.Excluir(produto);
                }
            }

        }
    }

    public String categoria(ProdutoDao produtoDao,String Bebidas) {
        ObservableList<Produto> Cod_Produto = FXCollections.observableArrayList();
        Cod_Produto = produtoDao.ConsultaPorCategoria(Bebidas);
        int index = 1;
        String cod = "B000001";

        for (Produto produto : Cod_Produto) {
            if (index < 10) {
                cod = "B00000" + index;
                if (cod.equals(produto.getIdProduto())) {
                    index++;
                    cod = "B00000" + index;
                }
            } if (index > 9 & index < 100) {
                cod = "B0000" + index;
                if (cod.equals(produto.getIdProduto())) {
                    index++;
                    cod = "B0000" + index;
                }
            } if (index > 99 & index < 1000) {
            	  cod = "B000" + index;
                if (cod.equals(produto.getIdProduto())) {
                    index++;
                    cod = "B000" + index;
                }
            }  if (index > 999 &index < 10000) {
                cod = "B00" + index;
                if (cod.equals(produto.getIdProduto())) {
                    index++;
                    cod = "B00" + index;
                }
            }  if (index > 9999 & index < 100000) {
            	cod = "B0" + index;
                if (cod.equals(produto.getIdProduto())) {
                    index++;
                    cod = "B0" + index;
                }
            } if(index>99999) {
            	cod = "B" + index;
                if (cod.equals(produto.getIdProduto())) {
                    index++;
                    cod = "B" + index;
                }
            }


        }

        return cod;


    }
    public String categoria(String Diversos,ProdutoDao produtoDao) {
        ObservableList<Produto> Cod_Produto = FXCollections.observableArrayList();
        Cod_Produto = produtoDao.ConsultaPorCategoria(Diversos);
        int index = 1;
        String cod = "D000001";

        for (Produto produto : Cod_Produto) {
            
                if (index < 10) {
                	cod = "D00000" + index;
                    if (cod.equals(produto.getIdProduto())) {
                        index++;
                        cod = "D00000" + index;
                    }
                } if (index > 9 & index < 100) {
                    cod = "D0000" + index;
                    if (cod.equals(produto.getIdProduto())) {
                        index++;
                        cod = "D0000" + index;
                    }
                } if (index > 99 & index < 1000) {
                	  cod = "D000" + index;
                    if (cod.equals(produto.getIdProduto())) {
                        index++;
                        cod = "D000" + index;
                    }
                }  if (index > 999 &index < 10000) {
                    cod = "D00" + index;
                    if (cod.equals(produto.getIdProduto())) {
                        index++;
                        cod = "D00" + index;
                    }
                }  if (index > 9999 & index < 100000) {
                	cod = "D0" + index;
                    if (cod.equals(produto.getIdProduto())) {
                        index++;
                        cod = "D0" + index;
                    }
                } if(index>99999) {
                	cod = "D" + index;
                    if (cod.equals(produto.getIdProduto())) {
                        index++;
                        cod = "D" + index;
                    }
                }
            }
        

        return cod;


    }

    public String categoria(ProdutoDao produtoDao, String Pratos, int index) {
        ObservableList<Produto> Cod_Produto = FXCollections.observableArrayList();
        Cod_Produto = produtoDao.ConsultaPorCategoria(Pratos);

        String cod = "PRA000001";
        for (Produto produto : Cod_Produto) {

            if (index < 10) {
                cod = "PRA00000" + index;
                if (cod.equals(produto.getIdProduto())) {
                    index++;
                    cod = "PRA00000" + index;
                }
            } if (index > 9 & index < 100) {
                cod = "PRA0000" + index;
                if (cod.equals(produto.getIdProduto())) {
                    index++;
                    cod = "PRA0000" + index;
                }
            } if (index > 99 & index < 1000) {
                cod = "PRA000" + index;
                if (cod.equals(produto.getIdProduto())) {
                    index++;
                    cod = "PRA000" + index;
                }
            }  if (index > 999 &index < 10000) {
                cod = "PRA00" + index;
                if (cod.equals(produto.getIdProduto())) {
                    index++;
                    cod = "PRA00" + index;
                }
            }  if (index > 9999 & index < 100000) {
                cod = "PRA0" + index;
                if (cod.equals(produto.getIdProduto())) {
                    index++;
                    cod = "PRA0" + index;
                }
            } if(index>99999) {
                cod = "PRA" + index;
                if (cod.equals(produto.getIdProduto())) {
                    index++;
                    cod = "PRA" + index;
                }
            }
        }



        return cod;


    }
    public String categoria(ProdutoDao produtoDao,int index ,String Itens) {
        ObservableList<Produto> Cod_Produto = FXCollections.observableArrayList();
        Cod_Produto = produtoDao.ConsultaPorCategoria(Itens);

        String cod = "I000001";
        for (Produto produto : Cod_Produto) {

            if (index < 10) {
                cod = "I00000" + index;
                if (cod.equals(produto.getIdProduto())) {
                    index++;
                    cod = "I00000" + index;
                }
            } if (index > 9 & index < 100) {
                cod = "I0000" + index;
                if (cod.equals(produto.getIdProduto())) {
                    index++;
                    cod = "I0000" + index;
                }
            } if (index > 99 & index < 1000) {
                cod = "I000" + index;
                if (cod.equals(produto.getIdProduto())) {
                    index++;
                    cod = "I000" + index;
                }
            }  if (index > 999 &index < 10000) {
                cod = "I00" + index;
                if (cod.equals(produto.getIdProduto())) {
                    index++;
                    cod = "I00" + index;
                }
            }  if (index > 9999 & index < 100000) {
                cod = "I0" + index;
                if (cod.equals(produto.getIdProduto())) {
                    index++;
                    cod = "I0" + index;
                }
            } if(index>99999) {
                cod = "I" + index;
                if (cod.equals(produto.getIdProduto())) {
                    index++;
                    cod = "I" + index;
                }
            }
        }
        return cod;


    }
    public String categoria(String ProdutosEroticos,ProdutoDao produtoDao, int index) {
        ObservableList<Produto> Cod_Produto = FXCollections.observableArrayList();
        Cod_Produto = produtoDao.ConsultaPorCategoria(ProdutosEroticos);

        String cod = "E000001";
        for (Produto produto : Cod_Produto) {

            if (index < 10) {
                cod = "E00000" + index;
                if (cod.equals(produto.getIdProduto())) {
                    index++;
                    cod = "E00000" + index;
                }
            } if (index > 9 & index < 100) {
                cod = "E0000" + index;
                if (cod.equals(produto.getIdProduto())) {
                    index++;
                    cod = "E0000" + index;
                }
            } if (index > 99 & index < 1000) {
                cod = "E000" + index;
                if (cod.equals(produto.getIdProduto())) {
                    index++;
                    cod = "E000" + index;
                }
            }  if (index > 999 &index < 10000) {
                cod = "E00" + index;
                if (cod.equals(produto.getIdProduto())) {
                    index++;
                    cod = "E00" + index;
                }
            }  if (index > 9999 & index < 100000) {
                cod = "E0" + index;
                if (cod.equals(produto.getIdProduto())) {
                    index++;
                    cod = "E0" + index;
                }
            } if(index>99999) {
                cod = "E" + index;
                if (cod.equals(produto.getIdProduto())) {
                    index++;
                    cod = "E" + index;
                }
            }
        }

        return cod;


    }
    public void Excluir_Tudo(ProdutoDao produtoDao){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Mensagem");

        alert.setHeaderText("Pergunta:");
        alert.setContentText("Tem certeza que você quer excluir tudo na tabela do produto ?\n" +
                "Para confirmar Sim, clique OK\n" +
                "Para confirmar Não, clique CANCEL");
        Optional<ButtonType> confirma = alert.showAndWait();

        if(confirma.get() == ButtonType.OK){
            ObservableList<Produto> Nome_Foto_Produto = FXCollections.observableArrayList();
            Nome_Foto_Produto = produtoDao.ListaProduto();

            for (Produto produto : Nome_Foto_Produto) {
                if (produto.getFoto() != null) {
                    File arquivoAtual = new File("Fotos_prod/" +produto.getFoto());
                    boolean c = arquivoAtual.delete();
                    System.out.println("Deletado"+c);

                }
                }
            produtoDao.Excluir_tudo();
    }
}
}