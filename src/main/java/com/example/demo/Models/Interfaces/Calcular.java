package com.example.demo.Models.Interfaces;

import com.example.demo.Models.Classes.Config;
import com.example.demo.Models.Classes.Vendas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public interface Calcular {

    public double calcular(Config config, Vendas vendas, Label duracao, Button botao);

    double calcular(Config config, Vendas vendas, Label duracao);
}
