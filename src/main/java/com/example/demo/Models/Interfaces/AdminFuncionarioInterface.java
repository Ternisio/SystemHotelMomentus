package com.example.demo.Models.Interfaces;

import com.example.demo.Models.Classes.Funcionario;
import com.example.demo.Models.Dao.FuncionarioDao;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.File;

public interface AdminFuncionarioInterface {
    public boolean VerificarCampos(String Nome, String Tipo, String Senha, String Cpf);
    public void Excluir_e_Editar(boolean Excluir, boolean Editar, FuncionarioDao funcionariodao, Funcionario funcionario, File file);
    public String CodFuncionario(FuncionarioDao funcionarioDao);
    public void Excluir_Tudo(FuncionarioDao funcionarioDao);

}
