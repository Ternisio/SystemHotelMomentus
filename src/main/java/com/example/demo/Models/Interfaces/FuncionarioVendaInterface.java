package com.example.demo.Models.Interfaces;

import com.example.demo.Controllers.ControllerEditar_venda;
import com.example.demo.Controllers.ControllerTelaFuncionario;
import com.example.demo.Models.Classes.Config;
import com.example.demo.Models.Classes.Funcionario;
import com.example.demo.Models.Classes.Quarto;
import com.example.demo.Models.Classes.Vendas;
import com.example.demo.Models.Dao.ConfigDao;
import com.example.demo.Models.Dao.FuncionarioDao;
import com.example.demo.Models.Dao.QuartoDao;
import com.example.demo.Models.Dao.VendaDao;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.File;

public interface FuncionarioVendaInterface {
    public void Lbl(ControllerTelaFuncionario controllerTelaFuncionario,Config config, ConfigDao configDao);
    public void atualizado(GridPane Grid, QuartoDao quartoDao, Quarto quarto, Vendas vendas, Funcionario funcionario);
    public void Lbl(ControllerEditar_venda controllerEditar_venda, Config config, ConfigDao configDao);
    public  void Cadastro(Vendas vendas, Quarto quarto, Funcionario funcionario);
    public String CodVenda();
    public void limparTabela();
    public void excluir_consumo(String Id_produto,String Nome, int qtd);
    public void Editar_consumo(String Id_Produto, int qtd, int estoques);
    public void excluir_consumo(String Id_produto, int qtd);
    public  void Adicionar_Consumo(String Cod_Produto, int qtd, int TotalEstoques);
    public void finalizar();
    void CarregarTabela(Vendas vendas);
    public double TotalConsumo();
    public void pararThread();
    public void Cancelado();
    public boolean VerificarExistir(String Cod_produto);
    public double total(Vendas vendas, Config config);
    public void Editar(FuncionarioDao funcionariodao, Funcionario funcionario, File file, TextField txt_cpf, PasswordField senha);
}
