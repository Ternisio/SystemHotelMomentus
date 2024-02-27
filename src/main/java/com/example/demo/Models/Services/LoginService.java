package com.example.demo.Models.Services;

import com.example.demo.Controllers.ControllerLogin;
import com.example.demo.Models.Classes.Funcionario;
import com.example.demo.Models.Dao.FuncionarioDao;
import com.example.demo.Models.Interfaces.LoginInterface;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginService implements LoginInterface {
    private ControllerLogin controllerLogin;
    public void inicial(ControllerLogin controllerLogin){
        this.controllerLogin =  controllerLogin;
    }
public boolean ValidationLogin(TextField Txt_nome, PasswordField Senha, FuncionarioDao funcionarioDao, Funcionario funcionario){
    boolean verificar = false;
    if (Txt_nome.getText().equals("") & Senha.getText().equals("")){
        controllerLogin.Lbl_error.setText("Preencham todos os campos");
    }
    else if(Txt_nome.getText().equals("")){
       controllerLogin.Lbl_error.setText("Preencha o nome");
    } else if (Senha.getText().equals("")) {
        controllerLogin.Lbl_error.setText("Preencha a senha");
    }else {
        verificar = funcionarioDao.verificar(Txt_nome, Senha, funcionario);
    }
    return verificar;
}
}
