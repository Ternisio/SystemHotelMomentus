package com.example.demo.Models.Services;

import com.example.demo.Controllers.ControllerDashboard;
import com.example.demo.Models.Classes.Config;
import com.example.demo.Models.Classes.Consumo;
import com.example.demo.Models.Classes.Produto;
import com.example.demo.Models.Classes.Vendas;
import com.example.demo.Models.Dao.ConfigDao;
import com.example.demo.Models.Dao.ConsumoDao;
import com.example.demo.Models.Dao.VendaDao;
import com.example.demo.Models.Interfaces.VendaInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class AdminVendaPorProdutos {
    public ObservableList<Produto> produtos = FXCollections.observableArrayList();

            ConsumoDao consumo = new ConsumoDao();
        Config config = new Config();
        ConfigDao configDao = new ConfigDao();
        private ControllerDashboard controllerDashboard;


        XYChart.Series<String,Number> series1 ;
        Boolean iniciado = false;
        private ObservableList<XYChart.Data<String,Number>> GraficoDados1;
        private ScheduledExecutorService executorService;
        public  void inicial(ControllerDashboard controllerDashboard){
            this.controllerDashboard = controllerDashboard;
            GraficoDados1 = FXCollections.observableArrayList();
            series1 = new XYChart.Series<>(GraficoDados1);
            controllerDashboard.yAxisP.setAutoRanging(false);
            controllerDashboard.yAxisP.setLowerBound(0);
            controllerDashboard.yAxisP.setUpperBound(50);
            controllerDashboard.yAxisP.setTickUnit(5);
            controllerDashboard.Grafico_venda_por_produto.getData().addAll(series1);


        }


        public void StartExecutorService(LocalDate data_inicio, LocalDate data_fim, int limite, Label GANHADOR, Label TOTALv){

            executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.scheduleAtFixedRate(()->{
                Buscar(data_inicio, data_fim,  limite, GANHADOR,TOTALv);
            }, 0,5, TimeUnit.SECONDS);

        }
        public void Buscar(LocalDate data_inicio, LocalDate data_fim, int limite, Label  GANHADOR, Label TOTALv){
            Task<ObservableList<Produto>> valorfun1 = Criartarefa(data_inicio, data_fim, limite);
            valorfun1.setOnSucceeded(event -> {
                MostrarGraficoVendaPorQuarto(valorfun1.getValue(), GANHADOR,TOTALv);
            });

            executorService.execute(valorfun1);
        }
        public void stopExecutorService() {
            executorService.shutdownNow();
        }
        public void Filtrar(LocalDate data_inicio, LocalDate data_fim, int limite, Label GANHADOR, Label TOTALv){
            StartExecutorService( data_inicio,  data_fim,  limite,GANHADOR,TOTALv);


        }
        private Task<ObservableList<Produto>> Criartarefa(LocalDate data_inicio, LocalDate data_fim, int limite){
            return new Task<>(){
                @Override
                protected ObservableList<Produto> call() throws Exception{
                    produtos.clear();
                    Thread.sleep(2000);
                    produtos = consumo.ListaConsumoDados(data_inicio,data_fim,limite);
                        System.out.println(produtos.size());
                    return produtos;
                }

            };
        }
        public void MostrarGraficoVendaPorQuarto(ObservableList<Produto> produtos,Label GANHADOR,Label TOTALv) {
            System.out.println("entrou mostrar");
            int i = 0;
            GraficoDados1.clear();
            series1.getData().clear();
            controllerDashboard.yAxisP.setTickLength(0);
            controllerDashboard.yAxisP.setTickLength(produtos.size());
            controllerDashboard.xAxisP.setTickLength(0);
            controllerDashboard.xAxisP.setTickLength(produtos.size());
            String Ganhador_produto = "";
            double precoinicial = 0.00;
            double total = 0.00;
            for (Produto produto : produtos) {
                total = total + produto.getValor();
                System.out.println(produto.getNome_prod() + ", " + produto.getValor());
                GraficoDados1.add(new XYChart.Data<>(produto.getNome_prod(), produto.getValor()));
            if (precoinicial > produto.getValor()){
                precoinicial = precoinicial;
            }else{
                Ganhador_produto = produto.getNome_prod();
                precoinicial = produto.getValor();
            }}
            GANHADOR.setText(Ganhador_produto);
            TOTALv.setText(String.format("%.2f",total));
            // Animação das barras
            for (XYChart.Data<String, Number> data : GraficoDados1) {

                int initialValue = 0; // Valor inicial para a animação
                Number finalValue = data.getYValue(); // Valor final

                // Configurar a animação da barra
                data.setYValue(initialValue);
                data.getNode().setOpacity(0);

                // Animação da altura da barra
                javafx.animation.Timeline timeline = new javafx.animation.Timeline();
                javafx.animation.KeyFrame keyFrame = new javafx.animation.KeyFrame(
                        javafx.util.Duration.seconds(1), // Duração da animação
                        new javafx.animation.KeyValue(data.YValueProperty(), finalValue)
                );
                timeline.getKeyFrames().add(keyFrame);
                timeline.play();

                // Animação da opacidade da barra
                javafx.animation.FadeTransition fadeTransition = new javafx.animation.FadeTransition(
                        javafx.util.Duration.seconds(1), // Duração da animação
                        data.getNode()
                );
                fadeTransition.setToValue(1);
                fadeTransition.play();
                Node node = data.getNode();
                if (node != null) {
                    node.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            controllerDashboard.Lbl_Total_Venda_Prod1.setVisible(true);
                            controllerDashboard.Valor_Total_Venda_Produto1.setVisible(true);
                            controllerDashboard.Valor_Total_Venda_Produto1.setText(String.format("%.2f",data.getYValue()));
                        }
                    });
                    node.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            controllerDashboard.DesativarLabelTotalVendaProduto();
                        }
                    });
                }


            }

stopExecutorService();
        }
        public String DataValor(){
            LocalDate  DATA =  controllerDashboard.Data_Personalizada_Inicio_Grafico_Venda_Por_Quarto.getValue();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String StringFormatado = DATA.format(formatter);
            return StringFormatado;
        }
        public String DataValorF(){
            LocalDate  DATA =  controllerDashboard.Data_Personalizada_Fim_Grafico_Venda_Por_Quarto.getValue();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String StringFormatado = DATA.format(formatter);
            return StringFormatado;
        }
        public String DataMesAno(String num, String ano){
            String Nome_Data = "";
            switch (num){
                case "01":
                    Nome_Data = "Jan/"+ano;
                    break;
                case "02":
                    Nome_Data = "Fev/"+ano;
                    break;
                case "03":
                    Nome_Data = "Mar/"+ano;
                    break;
                case "04":
                    Nome_Data = "Abr/"+ano;
                    break;
                case "05":
                    Nome_Data = "Mai/"+ano;
                    break;
                case "06":
                    Nome_Data = "Jun/"+ano;
                    break;
                case "07":
                    Nome_Data = "Jul/"+ano;
                    break;
                case "08":
                    Nome_Data = "Ago/"+ano;
                    break;
                case "09":
                    Nome_Data = "Set/"+ano;
                    break;
                case "10":
                    Nome_Data = "Out/"+ano;
                    break;
                case "11":
                    Nome_Data = "Nov/"+ano;
                    break;
                case "12":
                    Nome_Data = "Dez/"+ano;
                    break;
            }
            return Nome_Data;
        }
        public  void LimparGrafico(){
            GraficoDados1.clear();

        }

}
