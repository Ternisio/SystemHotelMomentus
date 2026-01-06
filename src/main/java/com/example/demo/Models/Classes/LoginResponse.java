package com.example.demo.Models.Classes;

import java.io.Serializable;

public class LoginResponse implements Serializable {
    private String token;
    private Funcionario funcionario;
    public LoginResponse() {
    }
    public LoginResponse(String token, Funcionario funcionario) {
        this.token = token;
        this.funcionario = funcionario;
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

}
