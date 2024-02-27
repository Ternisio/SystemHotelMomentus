package com.example.demo.Models.Interfaces;

import com.example.demo.Controllers.ControllerLogin;
import com.example.demo.Models.Classes.Funcionario;
import com.example.demo.Models.Dao.FuncionarioDao;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public interface LoginInterface {
    public void inicial(ControllerLogin controllerLogin);
    public boolean ValidationLogin(TextField Txt_nome, PasswordField Senha, FuncionarioDao funcionarioDao, Funcionario funcionario);
}
