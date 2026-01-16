package com.example.demo.Models.Services;

import com.example.demo.Controllers.BtnBotoesQuartos;
import com.example.demo.Controllers.ControllerEditar_venda;
import com.example.demo.Controllers.ControllerTelaFuncionario;
import com.example.demo.Models.Classes.*;

import com.example.demo.Models.Dao.*;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;
import org.jfree.chart.renderer.category.LineRenderer3D;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
     ObservableList<Vendas> ListaVendasRealizando = VendasRealizandoTemporario.carregar();
     ObservableList<Vendas> ListaVendasFinalizado = VendasFinalizadoTemporario.carregar();
     ObservableList<Vendas> ListaVendasFinalizadoNaoSalvo = VendasFinalizadoNaoSalvoTemporario.carregar();
    ConsumoDao consumoDao = new ConsumoDao();
  private   ObservableList<Quarto> ListaQuartoUI = null;
     private ControllerTelaFuncionario controllerTelaFuncionario;
MensagemAlert alert = new MensagemAlert();
     public void pararThread(){
         PareD = true;
     }
ConsumoTemporario consumoTemporario = new ConsumoTemporario();
     public void Lbl(ControllerTelaFuncionario controllerTelaFuncionario, Config config, ConfigDao configDao){
         this.controllerTelaFuncionario = controllerTelaFuncionario;
         this.config = config;
         this.configDao = configDao;
         configDao.ListaConfig(this.config);
ConsumoGeral = ConsumoTemporario.carregar();
         if(VendasRealizandoTemporario.carregar() == null || VendasRealizandoTemporario.carregar().isEmpty()){
             LocalDateTime Inicio = LocalDateTime.now().minusDays(1);
             LocalDateTime Fim = LocalDateTime.now();
             ObservableList<Vendas> vendasP = vendaDao.ListaDevendadehojeL(Inicio,Fim);

             ListaVendasRealizando.addAll(
                     FXCollections.observableArrayList(
                             vendasP.stream()
                                     .filter(venda -> venda.getStatus().equals("Realizando")) // evita NPE
                                     .collect(Collectors.toList())
                     )
             );
             for(Vendas v : vendasP){
                 System.out.println(v.getQuarto().getCod_Quarto());
             }
             if(!ListaVendasRealizando.isEmpty() || ListaVendasRealizando != null){
                 try {

                     vendasRealizandoTemporario.salvar(ListaVendasRealizando);
                 }

                 catch (Exception e){
                     alert.MensagemError(e.getMessage());
                 }}
         }


     }
     VendasFinalizadoNaoSalvoTemporario vendasFinalizadoNaoSalvoTemporario = new VendasFinalizadoNaoSalvoTemporario();
     VendasRealizandoTemporario vendasRealizandoTemporario = new VendasRealizandoTemporario();
     VendasFinalizadoTemporario vendasFinalizadoTemporario = new VendasFinalizadoTemporario();
 private  ObservableList<Consumo> ListaConsumo = FXCollections.observableArrayList();
    private ObservableList<Consumo> ConsumoGeral = FXCollections.observableArrayList();
    public void atualizado(GridPane Grid,  QuartoDao quartoDao, Quarto quarto, Vendas vendas,  Funcionario funcionario){
        this.Grid = Grid;
        this.vendas = vendas;
        this.quarto = quarto;
        this.quartoDao = quartoDao;
        this.funcionario = funcionario;
        if(QuartoTemporario.carregar() == null || QuartoTemporario.carregar().isEmpty()){ ListaQuartoUI = quartoDao.ListaQuarto();} else {ListaQuartoUI = QuartoTemporario.carregar();}
        atualizar();


    }

        void atualizar(){

            Grid.getChildren().clear();

            if (!ListaQuartoUI.isEmpty()) {
                meuClick = new MeuClick() {
                    @Override
                    public void onClickQuarto(Quarto quarto) throws IOException {
                        SelecionouQuarto(quarto);
                    }
                };

            }
            int col = 0;
            int row = 0;
            try {

                for (int i =  0; i < ListaQuartoUI.size(); i++){
                    FXMLLoader item =  new FXMLLoader();
                    item.setLocation(getClass().getResource("/com/example/demo/Views/Btn_botoes_quartos.fxml"));
                    VBox vBox = item.load();
                    BtnBotoesQuartos btnBotoesQuartos = item.getController();
                    btnBotoesQuartos.Botoes(ListaQuartoUI.get(i), meuClick);
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

    public void SelecionouQuarto(Quarto quarto )  throws IOException{
        System.out.println(quarto.getCod_Quarto());
        Object[] opc = {"Sim","Não"};
        if (quarto.getEstado().equals("Disponível")){
            int resultDialog =  JOptionPane.showOptionDialog(null,"Você quer o quarto "+quarto.getNum_quarto()+" ficar em ocupado ?","Pergunta",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,opc,opc[0]);
            if (resultDialog == JOptionPane.YES_OPTION){
                String cod = CodVenda();
                System.out.println(cod);
                if(!cod.equals("Sem retorno")){
                quarto.setEstado("Ocupado");

                vendas.setCod_venda(cod);
                vendas.setPagamento("");
                vendas.setData_hora_Entrada(LocalDateTime.now());
                vendas.setData_hora_Saida(null);
                vendas.setStatus("Realizando");
                vendas.setTotal(0.00);
                Vendas venda = new Vendas();
                venda.setCod_venda(cod);
                venda.setQuarto(quarto);
                venda.setFuncionario(funcionario);
                venda.setData_hora_Entrada(LocalDateTime.now());
                venda.setData_hora_Saida(null);
                venda.setStatus("Realizando");
                venda.setTotal(0.00);
                ListaVendasRealizando.add(venda);
                vendasRealizandoTemporario.salvar(ListaVendasRealizando);

                if(Cadastro(vendas,quarto,funcionario)){
                quartoDao.Editar(quarto);
                AtualizarLista(quarto);}else {
                ListaVendasRealizando.remove(vendas);
                if(Conexoes.InternetConectado()){
                    alert.MensagemError("Erro ao servidor");
                }else {
                    alert.MensagemError("Você está sem internet");
                }
                }
                }else {
                    if(Conexoes.InternetConectado()){
                        alert.MensagemError("Erro ao servidor");
                    }else {
                        alert.MensagemError("Você está sem internet");
                    }
                }

            }
        } else if (quarto.getEstado().equals("Ocupado")) {
            int resultDialog =  JOptionPane.showOptionDialog(null,"Você quer ver os detalhes no quarto "+quarto.getNum_quarto()+"?","Pergunta",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,opc,opc[0]);
            if (resultDialog == JOptionPane.YES_OPTION){
             /*   vendas.setStatus("Realizando");
            vendas.setQuarto(quarto);
           vendaDao.BuscarRealizando(vendas);*/
                vendas = ListaVendasRealizando.stream().filter(v->v.getQuarto().getCod_Quarto() == quarto.getCod_Quarto()).findFirst().orElseThrow(()-> new  NoSuchElementException("Nao foi encontrado"));

            System.out.println(vendas.getCod_venda()+" "+vendas.getQuarto().getNum_quarto());
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
        String Cod_Venda = vendaDao.CodigodaVenda();
       if(Cod_Venda!=null){
       return Cod_Venda;}
       else {
          return "Sem retorno";
       }


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
        if(vendaDao.cadastrar(vendas)){;
        int indiceRealizando = IntStream.range(0,ListaVendasRealizando.size()).filter(i-> ListaVendasRealizando.get(i).getCod_venda() == vendas.getCod_venda()).findFirst().orElseThrow(()-> new NoSuchElementException("Não foi encontrado"));
        ListaVendasRealizando.remove(indiceRealizando);
       vendas.getQuarto().setEstado("Sujo");
        quartoDao.Editar(vendas.getQuarto());
        AtualizarLista(vendas.getQuarto());
        Vendas venda = new Vendas();
        venda.setCod_venda(vendas.getCod_venda());
        venda.setQuarto(vendas.getQuarto());
            venda.setFuncionario(vendas.getFuncionario());
            venda.setData_hora_Entrada(vendas.getData_hora_Entrada());
            venda.setData_hora_Saida(vendas.getData_hora_Saida());
            venda.setPagamento(vendas.getPagamento());
            venda.setStatus(vendas.getStatus());
            venda.setTotal(vendas.getTotal());
            ConsumoGeral.removeIf(consumo -> consumo.getVendas().getCod_venda().equals(vendas.getCod_venda()));

        ListaVendasFinalizado.add(venda);
       try {
           consumoTemporario.salvar(ConsumoGeral);
           vendasRealizandoTemporario.salvar(ListaVendasRealizando);
           vendasFinalizadoTemporario.salvar(ListaVendasFinalizado);
       } catch (IOException e){
           e.printStackTrace();

       }
        return true;}else{
            int indiceRealizando = IntStream.range(0,ListaVendasRealizando.size()).filter(i-> ListaVendasRealizando.get(i).getCod_venda().equals( vendas.getCod_venda())).findFirst().orElseThrow(()-> new NoSuchElementException("Não foi encontrado"));
            ListaVendasRealizando.remove(indiceRealizando);
            vendas.getQuarto().setEstado("Sujo");
            quartoDao.Editar(vendas.getQuarto());
            AtualizarLista(vendas.getQuarto());
            ListaVendasFinalizado.add(vendas);
            try {
                vendasRealizandoTemporario.salvar(ListaVendasRealizando);
                vendasFinalizadoTemporario.salvar(ListaVendasFinalizado);
                ListaVendasFinalizadoNaoSalvo.add(vendas);
                vendasFinalizadoNaoSalvoTemporario.salvar(ListaVendasFinalizadoNaoSalvo);
            } catch (IOException e){
                e.printStackTrace();
            }
            return true;
        }

    }else {
        return false;
    }

    }
    QuartoTemporario quartoTemporario = new QuartoTemporario();
    void AtualizarLista(Quarto quartoA){
      int indice = IntStream.range(0,ListaQuartoUI.size()).filter(i -> ListaQuartoUI.get(i).getCod_Quarto() == quartoA.getCod_Quarto()).findFirst().orElseThrow(()->new NoSuchElementException("Não foi encontrado") );
        ListaQuartoUI.set(indice,quartoA);
        try {
            quartoTemporario.salvar(ListaQuartoUI);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
        ListaConsumo = ConsumoGeral.stream()
                .filter(consumo -> vendas.getCod_venda().equals(consumo.getVendas().getCod_venda()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        controllerTelaFuncionario.Coluna_Cod_Produto.setCellValueFactory(new PropertyValueFactory<Consumo,String>("idProduto"));
        controllerTelaFuncionario.Coluna_Nome_Produto_venda.setCellValueFactory(new PropertyValueFactory<Consumo,String>("Nome_prod"));
        controllerTelaFuncionario.Coluna_Valor_Produto_venda.setCellValueFactory(new PropertyValueFactory<Consumo,String>("ExibirValor"));
        controllerTelaFuncionario.Coluna_QTS_Produto_Venda.setCellValueFactory(new PropertyValueFactory<Consumo,Integer>("Qtd"));
        controllerTelaFuncionario.Tabela_Consumo_venda.setItems(ListaConsumo);

    }
    public  void Adicionar_Consumo(Produto produto, int qtd) {
        boolean Verificarexistir = VerificarExistir(produto.getIdProduto());
        if (Verificarexistir) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");

            alert.setHeaderText("Erro:");
            alert.setContentText("Já existe na tabela");
            alert.showAndWait();

        } else {
            Vendas venda = new Vendas();
            venda.setCod_venda(vendas.getCod_venda());
            Consumo consumo = new Consumo();
            consumo.setIdProduto(produto.getIdProduto());
            consumo.setNome_prod(produto.getNome_prod());
            consumo.setValor(produto.getValor());
            consumo.setExibirValor(produto.getExibirValor());
            consumo.setVendas(venda);
            consumo.setQtd(qtd);
            ConsumoGeral.add(consumo);
            int restantes = ADDcalcularEstoques(qtd, produto.getEstoques());

            if (consumoDao.cadastrar(vendas, produto.getIdProduto(), qtd)) {
                consumoDao.editarProduto(produto.getIdProduto(), restantes);
                CarregarTabela(vendas);
                double Subtotal = total(vendas, config) + TotalConsumo();
                controllerTelaFuncionario.lbl_Total_Venda.setText(String.format("%.2f", Subtotal));
                try {
                    consumoTemporario.salvar(ConsumoGeral);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }else {
                try {
                    consumoTemporario.salvar(ConsumoGeral);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }
    }
    @Override
    public boolean Cadastro(Vendas vendas, Quarto quarto, Funcionario funcionario) {
        vendas.setQuarto(quarto);
        vendas.setFuncionario(funcionario);
        if(vendaDao.cadastrar(vendas)){
            return true;
        }else{
            return false;
        }
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
        return vendaDao.ListaDevendadehojeL(Data_inicial_formatada,Data_final_formatada);
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
 ListaConsumo.removeIf(c-> c.getVendas().getCod_venda().equals(vendas.getCod_venda()) && c.getIdProduto().equals(Id_produto));
        ConsumoGeral.removeIf(c-> c.getVendas().getCod_venda().equals(vendas.getCod_venda()) && c.getIdProduto().equals(Id_produto));
 consumoDao.Excluir(vendas,Id_produto);

        CarregarTabela(vendas);
        double Subtotal =total(vendas,config) + TotalConsumo();
        controllerTelaFuncionario.lbl_Total_Venda.setText(String.format("%.2f",Subtotal));
        controllerTelaFuncionario.id_produto = null;

    }

    @Override
    public void Adicionar_Consumo(String Cod_Produto, int qtd, int TotalEstoques) {

    }
CalculadoraGeral calculadoraGeral = new CalculadoraGeral();
    public double total(Vendas vendas, Config config){
    double Total =0.00;
    Total = calculadoraGeral.calcular(config,vendas,controllerTelaFuncionario.lbl_Duracao_Venda,controllerTelaFuncionario.BotaoAcao);
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