package com.example.demo.Models.Services;

import com.example.demo.Controllers.BtnBotoesQuartos;
import com.example.demo.Controllers.ControllerEditar_venda;
import com.example.demo.Controllers.ControllerTelaFuncionario;
import com.example.demo.Models.Classes.*;

import com.example.demo.Models.Dao.*;
import com.example.demo.Models.Database.Conexao;
import com.example.demo.Models.Interfaces.Calcular;
import com.example.demo.Models.Interfaces.FuncionarioVendaInterface;
import com.example.demo.Models.Interfaces.MeuClick;
import com.example.demo.Util.MensagemAlert;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class FunQuatoVendaService implements FuncionarioVendaInterface {
    VendaDao vendaDao = new VendaDao();
    private  GridPane Grid;
     private MeuClick meuClick;
     private QuartoDao quartoDao;
     private Vendas vendas;
     private Funcionario funcionario;
     private Quarto quarto;
     private Config config;
     private ConfigDao configDao;
     public boolean PareD = false;
    ConsumoDao consumoDao = new ConsumoDao();
  private   ObservableList<Quarto> ListaQuartoUI = null;
     private ControllerTelaFuncionario controllerTelaFuncionario;
MensagemAlert alert = new MensagemAlert();
     public void pararThread(){
         PareD = true;
     }
     public void Lbl(ControllerTelaFuncionario controllerTelaFuncionario, Config config, ConfigDao configDao){
         this.controllerTelaFuncionario = controllerTelaFuncionario;
         this.config = config;
         this.configDao = configDao;
         configDao.ListaConfig(this.config);
     }
 private  ObservableList<Consumo> ListaConsumo = FXCollections.observableArrayList();
    private ObservableList<Quarto> ListaQuarto = FXCollections.observableArrayList();
    public void atualizado(GridPane Grid,  QuartoDao quartoDao, Quarto quarto, Vendas vendas,  Funcionario funcionario){
        this.Grid = Grid;
        this.vendas = vendas;
        this.quarto = quarto;
        this.quartoDao = quartoDao;
        this.funcionario = funcionario;
        ListaQuartoUI = quartoDao.ListaQuarto();
        atualizar();
        }

        void atualizar(){
            ListaQuarto.clear();
            Grid.getChildren().clear();
            ListaQuarto.addAll(ListaQuartoUI);
            if (!ListaQuarto.isEmpty()) {
                meuClick = new MeuClick() {
                    @Override
                    public void onClickQuarto(Quarto quarto) {
                        SelecionouQuarto(quarto);
                    }
                };

            }
            int col = 0;
            int row = 0;
            try {

                for (int i =  0; i < ListaQuarto.size(); i++){
                    FXMLLoader item =  new FXMLLoader();
                    item.setLocation(getClass().getResource("/com/example/demo/Views/Btn_botoes_quartos.fxml"));
                    VBox vBox = item.load();
                    BtnBotoesQuartos btnBotoesQuartos = item.getController();
                    btnBotoesQuartos.Botoes(ListaQuarto.get(i), meuClick);
                    if(col == 4 ){
                        col = 0;

                        row++;
                    }
                    Grid.add(vBox,col++,row);
                    GridPane.setMargin(vBox, new Insets(5 ));



                }
            }catch (IOException e){
                e.printStackTrace();


            }

        }

    @Override
    public void Lbl(ControllerEditar_venda controllerEditar_venda, Config config, ConfigDao configDao) {

    }

    public void SelecionouQuarto(Quarto quarto ){
        System.out.println(quarto.getCod_Quarto());
        Object[] opc = {"Sim","Não"};
        if (quarto.getEstado().equals("Disponível")){
            int resultDialog =  JOptionPane.showOptionDialog(null,"Você quer o quarto "+quarto.getNum_quarto()+" ficar em ocupado ?","Pergunta",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,opc,opc[0]);
            if (resultDialog == JOptionPane.YES_OPTION){
                quarto.setEstado("Ocupado");
                String cod = CodVenda();
                vendas.setCod_venda(cod);
                vendas.setPagamento("");
                vendas.setData_hora_Entrada(LocalDateTime.now());
                vendas.setData_hora_Saida(null);
                vendas.setStatus("Realizando");
                vendas.setTotal(0.00);
                Cadastro(vendas,quarto,funcionario);
                quartoDao.Editar(quarto);
                AtualizarLista(quarto);

            }
        } else if (quarto.getEstado().equals("Ocupado")) {
            int resultDialog =  JOptionPane.showOptionDialog(null,"Você quer ver os detalhes no quarto "+quarto.getNum_quarto()+"?","Pergunta",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,opc,opc[0]);
            if (resultDialog == JOptionPane.YES_OPTION){
                vendas.setQuarto(quarto);
            vendaDao.BuscarRealizando(vendas);
            System.out.println(vendas.getQuarto().getNum_quarto());
        controllerTelaFuncionario.pane_Lista_do_Quarto.setDisable(true);
        controllerTelaFuncionario.pane_Detalhes_Venda.setDisable(false);
            controllerTelaFuncionario.lbl_Num_quarto.setText(quarto.getNum_quarto());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            controllerTelaFuncionario.lbl_Datatime_entrada.setText(vendas.getData_hora_Entrada().format(formatter));
            controllerTelaFuncionario.lbl_Nome_fun_venda.setText(vendas.getFuncionario().getNome_Fun());
              PareD = false;
CarregarTabela(vendas);
                Thread thread = new Thread(()-> {
                    while (!PareD) {
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {

                        }

                        Platform.runLater(() -> {
                            if(PareD){
                                controllerTelaFuncionario.lbl_Datatime_Saida.setText("DD/mm/yyyy HH:MM");
                                controllerTelaFuncionario.lbl_Total_Venda.setText("0,00");
                                controllerTelaFuncionario.lbl_Duracao_Venda.setText("HH:MM");
                            }else {
                                controllerTelaFuncionario.lbl_Datatime_Saida.setText(LocalDateTime.now().format(formatter));

                                double totalPorHorasOuPrenoite2 = 0.00 + total(vendas, config);
                                double TotalConsumo2 = 0.00 + TotalConsumo();
                                double totatl2 = totalPorHorasOuPrenoite2 + TotalConsumo2;
                                controllerTelaFuncionario.lbl_Total_Venda.setText(String.format("%.2f", totatl2));
                            }
                        });

                    }

            });
                thread.start();
        }}else if(quarto.getEstado().equals("Sujo")){
            int resultDialog =  JOptionPane.showOptionDialog(null,"O quarto "+quarto.getNum_quarto()+" pode fica em disponível ?","Pergunta",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,opc,opc[0]);
            if (resultDialog == JOptionPane.YES_OPTION){
                quarto.setEstado("Disponível");
                quartoDao.Editar(quarto);
                AtualizarLista(quarto);
            }
        }
    }
    public String CodVenda() {

      String  Cod_Venda = "V" + vendaDao.CodigodoVenda();
        return Cod_Venda;


    }
    public boolean finalizar(){
        String[] opcoes = { "Pix", "Dinheiro", "Cartão" };
        vendas.setData_hora_Saida(LocalDateTime.now());

        String escolha = (String) JOptionPane.showInputDialog(
                null,
                "Selecione a forma de pagamento:",
                "Forma de Pagamento",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );
    System.out.println(escolha);
    if (escolha != null){
        PareD = true;
        vendas.setPagamento(escolha);
        vendas.setStatus("Finalizado");
        vendas.setTotal(Double.parseDouble(controllerTelaFuncionario.lbl_Total_Venda.getText().replace(".","").replace(",",".")));
        vendaDao.cadastrar(vendas);
        quarto.setCod_Quarto(vendas.getQuarto().getCod_Quarto());
        quarto.setNum_quarto(vendas.getQuarto().getNum_quarto());
        quarto.setEstado("Sujo");
        quartoDao.Editar(quarto);
        AtualizarLista(quarto);
        return true;

    }else {
        return false;
    }

    }
    void AtualizarLista(Quarto quarto){
        for (int i = 0; i < ListaQuartoUI.size();i++){
            if (ListaQuartoUI.get(i).getCod_Quarto() == quarto.getCod_Quarto()){
                ListaQuartoUI.set(i,quarto);
                break;
            }

        }
        atualizar();

    }




    public void Cancelado(){
        vendas.setStatus("Cancelado");
        vendaDao.cadastrar(vendas);
        vendaDao.Editarq(controllerTelaFuncionario.lbl_Num_quarto.getText(), "Disponível");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText("Aviso:");
        alert.setContentText("Foi cancelado com sucesso");
        alert.showAndWait();
        PareD = true;
        atualizado(Grid,quartoDao,quarto,vendas,funcionario);

    }
    public void CarregarTabela(Vendas vendas){
        ListaConsumo = consumoDao.ListaConsumo(vendas);
        controllerTelaFuncionario.Coluna_Cod_Produto.setCellValueFactory(new PropertyValueFactory<Consumo,String>("idProduto"));
        controllerTelaFuncionario.Coluna_Nome_Produto_venda.setCellValueFactory(new PropertyValueFactory<Consumo,String>("Nome_prod"));
        controllerTelaFuncionario.Coluna_Valor_Produto_venda.setCellValueFactory(new PropertyValueFactory<Consumo,String>("ExibirValor"));
        controllerTelaFuncionario.Coluna_QTS_Produto_Venda.setCellValueFactory(new PropertyValueFactory<Consumo,Integer>("Qtd"));
        controllerTelaFuncionario.Tabela_Consumo_venda.setItems(ListaConsumo);

    }
    public  void Adicionar_Consumo(String Cod_Produto, int qtd, int TotalEstoques){
        boolean Verificarexistir = VerificarExistir(Cod_Produto);
        if (Verificarexistir){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");

            alert.setHeaderText("Erro:");
            alert.setContentText("Já existe na tabela");
            alert.showAndWait();

        }else {
            int restantes = ADDcalcularEstoques(qtd,TotalEstoques);
            consumoDao.cadastrar(vendas,Cod_Produto,qtd);
            consumoDao.editarProduto(Cod_Produto,restantes);
            CarregarTabela(vendas);
            double Subtotal = total(vendas,config) + TotalConsumo();
            controllerTelaFuncionario.lbl_Total_Venda.setText(String.format("%.2f",Subtotal));
        }
    }
    @Override
    public void Cadastro(Vendas vendas, Quarto quarto, Funcionario funcionario) {
        vendas.setQuarto(quarto);
        vendas.setFuncionario(funcionario);
        vendaDao.cadastrar(vendas);

    }

    public void limparTabela(){
        ListaConsumo.clear();
        controllerTelaFuncionario.Tabela_Consumo_venda.setItems(ListaConsumo);
    }
public ObservableList<Vendas> ListarVendas(){
    LocalDate Data_inicial = LocalDate.now().minusDays(1);
    LocalDate Data_final = LocalDate.now();

    LocalDateTime Data_inicial_formatada = LocalDateTime.parse(Data_inicial.toString()+"T"+config.getPernoite_fim());
    LocalDateTime Data_final_formatada = LocalDateTime.parse(Data_final.toString()+"T"+config.getPernoite_inicio());
        return vendaDao.ListaDevendadehoje(Data_inicial_formatada,Data_final_formatada);
}
    @Override
    public void excluir_consumo(String Id_produto, String Nome, int qtd) {

    }

    public boolean VerificarExistir(String Cod_produto){
        boolean verificarex = ListaConsumo.stream().anyMatch(Cod -> Cod.getIdProduto().equals(Cod_produto));
        if(verificarex){
            return true;
        }else {
            return false;
        }

    }
    public void Editar_consumo(String Id_Produto, int qtd, int estoques){
consumoDao.editarConsumo(Id_Produto,qtd,vendas);
consumoDao.editarProduto(Id_Produto,estoques);
        CarregarTabela(vendas);
        double Subtotal =total(vendas,config) + TotalConsumo();
        controllerTelaFuncionario.lbl_Total_Venda.setText(String.format("%.2f",Subtotal));

    }
    public void excluir_consumo(String Id_produto, int qtd){
     int qtd_estoques = consumoDao.Estoques( Id_produto);
     int totalEstoques = qtd_estoques + qtd ;
 consumoDao.editarProduto(Id_produto,totalEstoques);
 consumoDao.Excluir(vendas,Id_produto);

        CarregarTabela(vendas);
        double Subtotal =total(vendas,config) + TotalConsumo();
        controllerTelaFuncionario.lbl_Total_Venda.setText(String.format("%.2f",Subtotal));
        controllerTelaFuncionario.id_produto = null;

    }
    public double total(Vendas vendas, Config config){
    double Total =0.00;

    LocalTime tpi = LocalTime.parse(config.getPernoite_inicio());
    LocalTime tpf = LocalTime.parse(config.getPernoite_fim());
    LocalDateTime DataPI = LocalDateTime.of(vendas.getData_hora_Entrada().getYear(),vendas.getData_hora_Entrada().getMonth(),vendas.getData_hora_Entrada().getDayOfMonth(),tpi.getHour(),tpi.getMinute());
   if (vendas.getData_hora_Entrada().getHour() == 0 || tpf.getHour()> vendas.getData_hora_Entrada().getHour()){
       DataPI = DataPI.minusDays(1);
   }
    if (vendas.getData_hora_Entrada().isBefore(DataPI)){
        Calcular calcular = new CalcularPorHoraService();
         Total = calcular.calcular(config,vendas,controllerTelaFuncionario.lbl_Duracao_Venda,controllerTelaFuncionario.BotaoAcao);
    }else {

        Calcular calcular = new CalcularPorPernoiteService();
        Total = Total + calcular.calcular(config,vendas,controllerTelaFuncionario.lbl_Duracao_Venda, controllerTelaFuncionario.BotaoAcao);
    }
    return  Total;
}
    public void Editar(FuncionarioDao funcionariodao, Funcionario funcionario, File file, TextField txt_cpf, PasswordField senha) {
        if (file != null) {
            if (funcionario.getNome_Foto() != null) {
                File pasta = new File("Fotos_Fun");
                String Caminho = pasta.getAbsolutePath();
                File arquivoAtual = new File(Caminho + "\\" + funcionario.getNome_Foto());
                boolean c = arquivoAtual.delete();
                System.out.println("Deletado: " + c);
                if(senha.getText().equals("")){
                    funcionariodao.Editar(txt_cpf,funcionario, file);
                }else{
                    funcionariodao.Editar(txt_cpf,senha,funcionario,file);
                }


            } else {
                if(senha.getText().equals("")){
                    funcionariodao.Editar(txt_cpf,funcionario, file);
                }else{
                    funcionario.setSenha_fun(senha.getText());
                    funcionariodao.Editar(txt_cpf,senha,funcionario,file);
                }
            }
        } else {
            if (senha.getText().equals("")){
                senha.setText(funcionario.getSenha_fun());
                funcionariodao.Editar(txt_cpf, senha,funcionario);
            }else {
                funcionario.setSenha_fun(senha.getText());
                funcionariodao.Editar(txt_cpf, senha,funcionario);
            }

        }

    }
    public int ADDcalcularEstoques(int qts, int Totalestoques){
        int restantes = Totalestoques - qts;
        return  restantes;
    }


public double TotalConsumo(){
   double ResultadoTotal =  ListaConsumo.stream().mapToDouble(C -> Double.parseDouble(C.getExibirValor().replace(",",".")) * C.getQtd()).sum();
   return ResultadoTotal;
}
}