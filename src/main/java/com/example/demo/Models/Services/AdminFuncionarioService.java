package com.example.demo.Models.Services;

import com.example.demo.Models.Classes.Funcionario;

import com.example.demo.Models.Classes.Produto;
import com.example.demo.Models.Dao.FuncionarioDao;
import com.example.demo.Models.Dao.ProdutoDao;
import com.example.demo.Models.Interfaces.AdminFuncionarioInterface;
import com.example.demo.Models.Interfaces.AdminProdutoInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.File;
import java.util.Optional;

public class AdminFuncionarioService implements AdminFuncionarioInterface {

    public boolean VerificarCampos(String Nome, String Tipo, String Senha, String Cpf) {
        if (Nome.equals("") || Tipo.equals("Selecione o tipo") || Senha.equals("") || Cpf.equals("")) {
            return true;
        } else {

            return false;
        }


    }
    public void Excluir_e_Editar(boolean Excluir, boolean Editar, FuncionarioDao funcionariodao, Funcionario funcionario, File file){
        if(Editar == true && Excluir == false){
            if(file != null){
                if (funcionario.getNome_Foto() != null){
                    File pasta = new File("Fotos_Fun");
                    String Caminho = pasta.getAbsolutePath();
                    File arquivoAtual = new File(Caminho+"\\"+funcionario.getNome_Foto());
                    boolean c = arquivoAtual.delete();
                    System.out.println("Deletado: "+c);
                    funcionariodao.Editar(funcionario,file);

                }else {

                    funcionariodao.Editar(funcionario,file);
                }
            }else {
                funcionariodao.Editar(funcionario);
            }
        }

        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mensagem");
            alert.setHeaderText("Pergunta:");
            alert.setContentText("Tem certeza que você quer excluir esse funcionario: "+funcionario.getNome_Fun()+" ?\n"+
                    "Para confirmar Sim, clique OK\n" +
                    "Para confirmar Não, clique CANCEL");
            Optional<ButtonType> confirma = alert.showAndWait();

            if(confirma.get() == ButtonType.OK){
                if (funcionario.getNome_Foto() != null){

                    File arquivoAtual = new File("src/main/resources/com.example.demo.Fotos_Fun/"+funcionario.getNome_Foto());
                    boolean c = arquivoAtual.delete();
                    System.out.println("Deletado"+c);
                    funcionariodao.Excluir(funcionario);


                }else {
                    funcionariodao.Excluir(funcionario);
                }
            }

        }
    }

        public String CodFuncionario(FuncionarioDao funcionarioDao) {
        ObservableList<Funcionario> Cod_Fun = FXCollections.observableArrayList();
        Cod_Fun = funcionarioDao.ListaFuncionario();

        String cod = "F000001";
        int index = 1;
        for (Funcionario funcionario : Cod_Fun) {
            if (index < 10) {

                if (cod.equals(funcionario.getId_Fun())) {
                    index++;
                    cod = "F00000" + index;
                }
            } else if (index < 100) {

                if (cod.equals(funcionario.getId_Fun())) {
                    index++;
                    cod = "F0000" + index;
                }
            } else if (index < 1000) {

                if (cod.equals(funcionario.getId_Fun())) {
                    index++;
                    cod = "F000" + index;
                }
            } else if (index < 10000) {

                 if (cod.equals(funcionario.getId_Fun())) {
                    index++;
                    cod = "F00" + index;
                }
            } else if (index < 100000) {

                if (cod.equals(funcionario.getId_Fun())) {
                    index++;
                    cod = "F0" + index;
                }
            } else {
                cod = "F" + index;
                if (cod.equals(funcionario.getId_Fun())) {
                    index++;
                }
            }


        }

        return cod;


    }

    public void Excluir_Tudo(FuncionarioDao funcionarioDao) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Mensagem");

        alert.setHeaderText("Pergunta:");
        alert.setContentText("Tem certeza que você quer excluir tudo na tabela do funcionario ?\n" +
                "Para confirmar Sim, clique OK\n" +
                "Para confirmar Não, clique CANCEL");
        Optional<ButtonType> confirma = alert.showAndWait();

        if (confirma.get() == ButtonType.OK) {
            ObservableList<Funcionario> Nome_Foto_Fun = FXCollections.observableArrayList();
            Nome_Foto_Fun = funcionarioDao.ListaFuncionario();

            for (Funcionario funcionario : Nome_Foto_Fun) {
                if (funcionario.getNome_Foto() != null) {
                    File arquivoAtual = new File("src/main/resources/com.example.demo.Fotos_Fun/" + funcionario.getNome_Foto());
                    boolean c = arquivoAtual.delete();
                    System.out.println("Deletado" + c);

                }
            }
            funcionarioDao.Excluir_tudo();
        }


    }
}