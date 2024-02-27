package com.example.demo.Models.Services;

import com.example.demo.Controllers.ControllerDashboard;
import com.example.demo.Models.Classes.Config;
import com.example.demo.Models.Classes.Consumo;
import com.example.demo.Models.Classes.Vendas;
import com.example.demo.Models.Dao.ConfigDao;
import com.example.demo.Models.Dao.ConsumoDao;
import com.example.demo.Models.Dao.VendaDao;
import com.example.demo.Models.Interfaces.VendaInterface;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.gillius.jfxutils.chart.JFXChartUtil;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.util.StringConverter;


public class VendaService implements VendaInterface {
    VendaDao vendaDao = new VendaDao();
    Config config = new Config();
    ConfigDao configDao = new ConfigDao();
    private ControllerDashboard controllerDashboard;
  ;
    XYChart.Series<String,Number> series ;
    Boolean iniciado = false;
    private ObservableList<XYChart.Data<String,Number>> GraficoDados;
private ScheduledExecutorService executorService;
public  void inicial(ControllerDashboard controllerDashboard){
    this.controllerDashboard = controllerDashboard;
    GraficoDados = FXCollections.observableArrayList();
    series = new XYChart.Series<>(GraficoDados);
    controllerDashboard.yAxis.setAutoRanging(false);
    controllerDashboard.yAxis.setLowerBound(0);
    controllerDashboard.yAxis.setUpperBound(50);
    controllerDashboard.yAxis.setTickUnit(5);
    controllerDashboard.grafico_venda.getData().add(series);

}

    public double TotalVendadeConsumo(){
        configDao.ListaConfig(config);
        LocalTime tpf = LocalTime.parse(config.getPernoite_fim());
        double total = 0.00;
        LocalDateTime localDateTime = LocalDateTime.now();
        int Hora = localDateTime.getHour();
        if(Hora>=0 & tpf.getHour()>Hora){
            LocalDate Fim = LocalDate.now();
            LocalDate Inicio = Fim.minusDays(1);
            total = total + vendaDao.TotaldeConsumo(Inicio,Fim);
        }else{

            LocalDate Inicio = LocalDate.now();
            LocalDate Fim = Inicio.plusDays(1);
            total = total + vendaDao.TotaldeConsumo(Inicio,Fim);
        }
        return total;
    }
    public double TotalVendadeConsumo(LocalDate Data){
        double total = 0.00;
            LocalDate Inicio = Data;
            LocalDate Fim = Inicio.plusDays(1);
            total = total + vendaDao.TotaldeConsumo(Inicio,Fim);

        return total;
    }
    public int TotalVendadeEntrada(){
        configDao.ListaConfig(config);
        LocalTime tpf = LocalTime.parse(config.getPernoite_fim());
        int total = 0;
        LocalDateTime localDateTime = LocalDateTime.now();
        int Hora = localDateTime.getHour();
        if(Hora>=0 & tpf.getHour()>Hora){
            LocalDate Fim = LocalDate.now();
            LocalDate Inicio = Fim.minusDays(1);
            total = total + vendaDao.TotaldeEntrada(Inicio,Fim);
        }else{

            LocalDate Inicio = LocalDate.now();
            LocalDate Fim = Inicio.plusDays(1);
            total = total + vendaDao.TotaldeEntrada(Inicio,Fim);
        }
        return total;
    }
    public int TotalVendadeSaida(){
        configDao.ListaConfig(config);
        LocalTime tpf = LocalTime.parse(config.getPernoite_fim());
        int total = 0;
        LocalDateTime localDateTime = LocalDateTime.now();
        int Hora = localDateTime.getHour();
        if(Hora>=0 & tpf.getHour()>Hora){
            LocalDate Fim = LocalDate.now();
            LocalDate Inicio = Fim.minusDays(1);
            total = total + vendaDao.TotaldeSainda(Inicio,Fim);
        }else{

            LocalDate Inicio = LocalDate.now();
            LocalDate Fim = Inicio.plusDays(1);
            total = total + vendaDao.TotaldeSainda(Inicio,Fim);
        }
        return total;
    }
    public int TotalVendadeSaidaPorPesquisar(LocalDate Date){
        int total = 0;
             LocalDate Inicio = Date;
            LocalDate Fim = Inicio.plusDays(1);
            total = total + vendaDao.TotaldeSainda(Inicio,Fim);

        return total;
    }
    public int TotalVendadeEntradaPorPesquisar(LocalDate Date){
        int total = 0;
        LocalDate Inicio = Date;
        LocalDate Fim = Inicio.plusDays(1);
        total = total + vendaDao.TotaldeEntrada(Inicio,Fim);

        return total;
    }

    public double TotalVendadePesquisar(LocalDate date){

        double total = 0.00;
            LocalDate Inicio = date;
            LocalDate Fim = Inicio.plusDays(1);
            total = total + vendaDao.VendaDeHoje(Inicio,Fim);

        return total;
    }
    public double TotalVendadeHoje(){

        configDao.ListaConfig(config);
        LocalTime tpf = LocalTime.parse(config.getPernoite_fim());
        double total = 0.00;
        LocalDateTime localDateTime = LocalDateTime.now();
        int Hora = localDateTime.getHour();
        if(Hora>=0 & tpf.getHour()>Hora){
            LocalDate Fim = LocalDate.now();
            LocalDate Inicio = Fim.minusDays(1);
            total = total + vendaDao.VendaDeHoje(Inicio,Fim);
        }else{

            LocalDate Inicio = LocalDate.now();
            LocalDate Fim = Inicio.plusDays(1);
            total = total + vendaDao.VendaDeHoje(Inicio,Fim);
        }
        return total;
    }
    public ObservableList<Vendas> ListaVendadeHoje(){
        configDao.ListaConfig(config);
        LocalTime tpf = LocalTime.parse(config.getPernoite_fim());
        ObservableList<Vendas> lista = FXCollections.observableArrayList();
        LocalDateTime localDateTime = LocalDateTime.now();
        int Hora = localDateTime.getHour();
        if(Hora>=0 & tpf.getHour()>Hora){
            LocalDate Fim = LocalDate.now();
            LocalDate Inicio = Fim.minusDays(1);
            lista = vendaDao.ListaDevendadehoje(Inicio,Fim);
        }else{

            LocalDate Inicio = LocalDate.now();
            LocalDate Fim = Inicio.plusDays(1);
            lista = vendaDao.ListaDevendadehoje(Inicio,Fim);
        }
        return lista;
    }

    public ObservableList<Vendas> ListaVendaporPesquisar(LocalDate data){
        ObservableList<Vendas> lista = FXCollections.observableArrayList();

            LocalDate Fim = data.plusDays(1);
            LocalDate Inicio = data;
            lista = vendaDao.ListaDevendaPorquarto(Inicio,Fim);

        return lista;
    }

    public ObservableList<Consumo> ListaConsumodeHoje(){
        configDao.ListaConfig(config);
        LocalTime tpf = LocalTime.parse(config.getPernoite_fim());
        ObservableList<Consumo> lista = FXCollections.observableArrayList();
        LocalDateTime localDateTime = LocalDateTime.now();
        int Hora = localDateTime.getHour();
        if(Hora>=0 & tpf.getHour()>Hora){
            LocalDate Fim = LocalDate.now();
            LocalDate Inicio = Fim.minusDays(1);
            lista = vendaDao.ListaDeConsumodehoje(Inicio,Fim);
        }else{

            LocalDate Inicio = LocalDate.now();
            LocalDate Fim = Inicio.plusDays(1);
            lista = vendaDao.ListaDeConsumodehoje(Inicio,Fim);
        }
        return lista;
    }
   @Override
    public void editar_Vendas(Vendas vendas, ComboBox Status_venda){
        if(Status_venda.getValue().equals("Realizando")){
            vendaDao.EditarVParaRealizando(vendas);
        }else{
            vendaDao.EditarVParaFinalizado(vendas);
        }
    }
    @Override
    public void excluir(Vendas vendas){
                  vendaDao.excluir(vendas);

    }
@Override
    public ObservableList<Vendas> ListaVendadeHojePorQuarto(){
        configDao.ListaConfig(config);
        LocalTime tpf = LocalTime.parse(config.getPernoite_fim());
        ObservableList<Vendas> lista = FXCollections.observableArrayList();
        LocalDateTime localDateTime = LocalDateTime.now();
        int Hora = localDateTime.getHour();
        if(Hora>=0 & tpf.getHour()>Hora){
            LocalDate Fim = LocalDate.now();
            LocalDate Inicio = Fim.minusDays(1);
            lista = vendaDao.ListaDevendaPorquarto(Inicio,Fim);
        }else{

            LocalDate Inicio = LocalDate.now();
            LocalDate Fim = Inicio.plusDays(1);
            lista = vendaDao.ListaDevendaPorquarto(Inicio,Fim);
        }
        return lista;
    }
public void StartExecutorService(String Filtrar){
System.out.println(Filtrar);
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(()->{
        Buscar(Filtrar);
        }, 0,5, TimeUnit.SECONDS);

}
public void Buscar(String Filtrar){
    Task<double[]> valoresData = Criartarefa(Filtrar);
    valoresData.setOnSucceeded(event -> {
            MostrarGraficoVendaPorQuarto(valoresData.getValue(),Filtrar);
    });
executorService.execute(valoresData);
}
    public void stopExecutorService() {
        executorService.shutdownNow();
    }
public void Filtrar(String Fitrar){
        StartExecutorService(Fitrar);


}
private Task<double[]> Criartarefa(String Filtrar){
        return new Task<>(){
            @Override
            protected double[] call() throws Exception{

                Thread.sleep(2000);
                String r = Filtrar;
                String Por_Data_Per = Filtrar ;
                double[] valores = new  double[0];
                int i = 0;

                if(r == "Por datas personalizadas"){
                    System.out.println("entrou");
                int days = (int) ChronoUnit.DAYS.between(controllerDashboard.Data_Personalizada_Inicio_Grafico_Venda_Por_Quarto.getValue(),controllerDashboard.Data_Personalizada_Fim_Grafico_Venda_Por_Quarto.getValue());
                System.out.println(days);
                if(days%30 != 0 & days>30){
                    int resultado = days/30;
                    int arraysvalores = resultado + 1;
                    valores = new double[arraysvalores];
                } else if (days%30 == 0 & days>30) {
                    int resultado = days/30;
                    valores = new double[resultado];
                }else {
                valores = new double[days+1];
                }
                    if (days > 60 || days >30){
                    YearMonth data_inicial = YearMonth.of(controllerDashboard.Data_Personalizada_Inicio_Grafico_Venda_Por_Quarto.getValue().getYear(),controllerDashboard.Data_Personalizada_Inicio_Grafico_Venda_Por_Quarto.getValue().getMonth());
                        YearMonth data_fim = YearMonth.of(controllerDashboard.Data_Personalizada_Fim_Grafico_Venda_Por_Quarto.getValue().getYear(),controllerDashboard.Data_Personalizada_Fim_Grafico_Venda_Por_Quarto.getValue().getMonth());
                 for (YearMonth proximo = data_inicial; proximo.isBefore(data_fim) || proximo.equals(data_inicial); proximo = proximo.plusMonths(1)){
                     if(proximo.equals(data_inicial)){
                        String dataFormatado = DataValor();
                        String dia = dataFormatado.substring(0,2);
                        String mes = dataFormatado.substring(3,5);
                        String ano = dataFormatado.substring(6,10);
                      YearMonth mesp= proximo.plusMonths(1);
                      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
                      String FORMATADO_Mes = mesp.format(formatter);
                      String MesP_F = FORMATADO_Mes.substring(0,2);
                      String AnoP_F = FORMATADO_Mes.substring(3,7);
                      valores[i]= vendaDao.Por_data_personalizadas_Inicial(ano,mes,dia,MesP_F,AnoP_F);
                      i++;
                     }else if (proximo.equals(data_fim)){
                         String dataFormatado = DataValorF();
                         String dia = dataFormatado.substring(0,2);
                         String mes = dataFormatado.substring(3,5);
                         String ano = dataFormatado.substring(6,10);
                         valores[i]= vendaDao.Por_data_personalizadas_Fim(ano,mes,dia);

                     }else {
                         DateTimeFormatter formatterP = DateTimeFormatter.ofPattern("MM/yyyy");
                         String dataFormatado = proximo.format(formatterP) ;
                         String mes = dataFormatado.substring(0,2);
                         String ano = dataFormatado.substring(3,7);
                         YearMonth mesp= proximo.plusMonths(1);
                         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
                         String FORMATADO_Mes = mesp.format(formatter);
                         String MesP_F = FORMATADO_Mes.substring(0,2);
                         String AnoP_F = FORMATADO_Mes.substring(3,7);
                         valores[i]= vendaDao.Por_data_personalizadas_alt(ano,mes,MesP_F,AnoP_F);
                         i++;

                     }
                 }
                }
                    else {
                        LocalDate data_inicial = controllerDashboard.Data_Personalizada_Inicio_Grafico_Venda_Por_Quarto.getValue();
                        LocalDate data_fim = controllerDashboard.Data_Personalizada_Fim_Grafico_Venda_Por_Quarto.getValue();
                        for (LocalDate Proximo = data_inicial; Proximo.isBefore(data_fim)||Proximo.equals(data_fim);Proximo = Proximo.plusDays(1)){

                            valores[i] = TotalVendadePesquisar(Proximo);
                            i++;
                        }
                    }
                }
                else{
                    int Filtrar_Valor = Integer.parseInt(r);
                     valores = new double[Filtrar_Valor];
                    System.out.println(valores.length);


                    LocalDate Data_atual = LocalDate.now();
                    LocalDate Data_Passada = Data_atual.minusDays(Filtrar_Valor);
                    for (LocalDate Proximo = Data_Passada; Proximo.isBefore(Data_atual);Proximo = Proximo.plusDays(1)){
                        valores[i] = TotalVendadePesquisar(Proximo);
                        i++;
                    }
                }
                return valores;
            }

        };
}
public void MostrarGraficoVendaPorQuarto(double[] Valores,String Fitrar) {
    System.out.println("entrou mostrar");
    int i = 0;
    GraficoDados.clear();
    series.getData().clear();
    controllerDashboard.yAxis.setTickLength(0);
    controllerDashboard.yAxis.setTickLength(Valores.length);
    controllerDashboard.xAxis.setTickLength(0);
    controllerDashboard.xAxis.setTickLength(Valores.length);
    String[] Datas = new String[0];
     Datas = new String[Valores.length];
    System.out.println(Datas.length);
    if (Fitrar.equals("Por datas personalizadas")) {
        System.out.println("entrou por personalizadas");

        int days = (int) ChronoUnit.DAYS.between(controllerDashboard.Data_Personalizada_Inicio_Grafico_Venda_Por_Quarto.getValue(),controllerDashboard.Data_Personalizada_Fim_Grafico_Venda_Por_Quarto.getValue());
        if(days>60 || days>30){
            YearMonth yearMonthInicial = YearMonth.of(controllerDashboard.Data_Personalizada_Inicio_Grafico_Venda_Por_Quarto.getValue().getYear(),controllerDashboard.Data_Personalizada_Fim_Grafico_Venda_Por_Quarto.getValue().getMonth());
            yearMonthInicial = yearMonthInicial.minusMonths(1);
            YearMonth yearMonthFim = YearMonth.of(controllerDashboard.Data_Personalizada_Fim_Grafico_Venda_Por_Quarto.getValue().getYear(),controllerDashboard.Data_Personalizada_Fim_Grafico_Venda_Por_Quarto.getValue().getMonth());
            for (YearMonth Proximo = yearMonthInicial; Proximo.isBefore(yearMonthFim) || Proximo.equals(yearMonthFim); Proximo = Proximo.plusMonths(1)) {
                System.out.println("entrou por personalizadas FOR");
                DateTimeFormatter formatterP = DateTimeFormatter.ofPattern("MM/yyyy");
                String dataFormatado = Proximo.format(formatterP) ;
                String mes = dataFormatado.substring(0,2);
                String ano = dataFormatado.substring(3,7);
                Datas[i] = DataMesAno(mes,ano);
                i++;

            }
        }else {
            LocalDate Data_Inicial = controllerDashboard.Data_Personalizada_Inicio_Grafico_Venda_Por_Quarto.getValue();

            LocalDate Data_Fim = controllerDashboard.Data_Personalizada_Fim_Grafico_Venda_Por_Quarto.getValue();

            for (LocalDate Proximo = Data_Inicial; Proximo.isBefore(Data_Fim)||  Proximo.equals(Data_Fim); Proximo = Proximo.plusDays(1)) {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                Datas[i] = dateTimeFormatter.format(Proximo);
                i++;

            }

        }
    } else {

        int Filtrar_Valor = Integer.parseInt(Fitrar);
        LocalDate Data_atual = LocalDate.now();
        LocalDate Data_Passada = Data_atual.minusDays(Filtrar_Valor);
        for (LocalDate Proximo = Data_Passada; Proximo.isBefore(Data_atual); Proximo = Proximo.plusDays(1)) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            Datas[i] = dateTimeFormatter.format(Proximo);
            i++;

        }

    }


    for (int in = 0; in < Datas.length; in++) {
        System.out.println(Datas[in] + ", " + Valores[in]);

        GraficoDados.add(new XYChart.Data<>(Datas[in], Valores[in]));
    }
    // Animação das barras
    for (XYChart.Data<String, Number> data : GraficoDados) {

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
                    controllerDashboard.txt_Valor_grafico.setText("Data da venda: "+data.getXValue()+"\n"+"Valor total: " + String.format("%.2f",data.getYValue()));
                }
            });
            node.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    controllerDashboard.txt_Valor_grafico.setText("");
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
GraficoDados.clear();
    }
}