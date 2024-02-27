package com.example.demo.Models.Interfaces;

import com.example.demo.Controllers.ControllerDashboard;
import com.example.demo.Models.Classes.Consumo;
import com.example.demo.Models.Classes.Produto;
import com.example.demo.Models.Classes.Quarto;
import com.example.demo.Models.Classes.Vendas;
import com.example.demo.Models.Dao.VendaDao;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.time.LocalDate;

public interface VendaInterface {
    public double TotalVendadeConsumo();
    public int TotalVendadeSaida();
    public int TotalVendadeEntrada();
    public double TotalVendadeHoje();
    public  void inicial(ControllerDashboard controllerDashboard);
    public ObservableList<Vendas> ListaVendaporPesquisar(LocalDate data);
    public ObservableList<Vendas> ListaVendadeHoje();

    public ObservableList<Consumo> ListaConsumodeHoje();
    public ObservableList<Vendas>ListaVendadeHojePorQuarto();
    public int TotalVendadeSaidaPorPesquisar(LocalDate Date);
    public int TotalVendadeEntradaPorPesquisar(LocalDate Date);
    public double TotalVendadeConsumo(LocalDate Data);
    public double TotalVendadePesquisar(LocalDate date);
    public void editar_Vendas(Vendas vendas, ComboBox Status_venda);
    public void excluir(Vendas vendas);
    public void Filtrar(String Fitrar);
    public  void LimparGrafico();

}
