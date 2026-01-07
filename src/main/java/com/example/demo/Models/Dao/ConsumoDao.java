package com.example.demo.Models.Dao;

import com.example.demo.Models.Api.GlobalApi;
import com.example.demo.Models.Classes.Config;
import com.example.demo.Models.Classes.Consumo;
import com.example.demo.Models.Classes.Produto;
import com.example.demo.Models.Classes.Vendas;
import com.example.demo.Models.Database.Conexao;
import com.example.demo.Models.Graphqls.Consumo.Mutation;
import com.example.demo.Models.Graphqls.Consumo.Query;
import com.example.demo.Util.MensagemAlert;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ConsumoDao {
    Query ConsumoQuery = new Query();
    GlobalApi api = new GlobalApi();
    Mutation mutationG = new Mutation();
    MensagemAlert alert = new MensagemAlert();
    public void cadastrar( Vendas vendas, String Cod_produto, int qtd) {
    String Mutation = mutationG.Consumo_add.formatted(vendas.getCod_venda(),Cod_produto,qtd, LocalDate.now());
        String compactQuery = Mutation.replace("\n", " ").replace("\r", " ");
        String json = "{ \"query\": \"" + compactQuery.replace("\"", "\\\"") + "\" }";
        HttpClient client = HttpClient.newHttpClient();

        try {

            HttpResponse<String> response = api.Api(client,json);

        } catch (Exception e) {
            // TODO: handle exception
            alert.MensagemError(""+e.getMessage());
            e.printStackTrace();
        }
    }
    public ObservableList<Consumo> ListaConsumo(Vendas vendas){

        String query = ConsumoQuery.ListaPorCliente.formatted(vendas.getCod_venda());
                ObservableList<Consumo> lista = FXCollections.observableArrayList();
        HttpClient client = HttpClient.newHttpClient();
        String compactQuery = query.replace("\n", " ").replace("\r", " ");

        String json = "{ \"query\": \"" + compactQuery.replace("\"", "\\\"") + "\" }";


        try {
            HttpResponse<String> response = api.Api(client,json);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode data = mapper.readTree(response.body()).get("data").get("TodosConsumosPorIdVenda");

            for(JsonNode res : data){
                Consumo consumo =  new Consumo(res.get("Id_Consumo").asInt(), res.get("IdProduto").get("idProduto").asText(),
                        res.get("IdProduto").get("nome").asText(), String.format("%.2f", res.get("IdProduto").get("valor").asDouble()).replace(".",","),
                        res.get("Quantidade").asInt());
                lista.add(consumo);
            }
            }
                    catch (Exception e){

            alert.MensagemError(""+e.getMessage());
            e.printStackTrace();
        }
        return lista;

    }
    public int Estoques( String Id_produto){
        String query = ConsumoQuery.QuantidadeProduto.formatted(Id_produto);
        String compactQuery = query.replace("\n", " ").replace("\r", " ");
        String json = "{ \"query\": \"" + compactQuery.replace("\"", "\\\"") + "\" }";
        HttpClient client = HttpClient.newHttpClient();
        int qtd_Estoques = 0;
        try {

            HttpResponse<String> response = api.Api(client,json);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode data = mapper.readTree(response.body()).get("data").get("QuantidadeProduto");

             qtd_Estoques = data.asInt();

        }catch (Exception e){
            String mensagem = "" + e.getMessage();
            alert.MensagemError(mensagem);
            e.printStackTrace();

        }
        return qtd_Estoques;
    }
    public ObservableList<Produto> ListaConsumoDados(LocalDate Data_Inicio, LocalDate Data_fim, int Limite){
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT p.Nome_prod as nome, sum(c.Quantidade * p.Valor_prod) as total FROM consumo c INNER JOIN produtos p on p.idProdutos = c.id_produto  WHERE c.data_Consumo_Produto BETWEEN '"+Data_Inicio+"' AND '"+Data_fim+"' GROUP BY nome ORDER BY total desc LIMIT "+Limite+";";
        ObservableList<Produto> lista = FXCollections.observableArrayList();
        try {
            ps = Conexao.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
              Produto produto = new Produto(rs.getString("nome"), rs.getFloat("total"));
              lista.add(produto);
            }
            rs.close();
            ps.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return lista;

    }
    public void editarProduto(String Cod_Produto, int Restantes){
    String Mutation = mutationG.AtualizarEstoques.formatted(Cod_Produto,Restantes);
        HttpClient client = HttpClient.newHttpClient();
        String compactQuery = Mutation.replace("\n", " ").replace("\r", " ");
  String json = "{ \"query\": \"" + compactQuery.replace("\"", "\\\"") + "\" }";

        try {
            HttpResponse<String> response = api.Api(client,json);

    }
        catch (Exception e) {
        // TODO: handle exception
            alert.MensagemError(""+e);
    }
}
    public void editarConsumo(String Cod_Produto, int qtd, Vendas vendas){

        String Mutation = mutationG.Consumo_atual.formatted(vendas.getCod_venda(),Cod_Produto,qtd);
        String compactQuery = Mutation.replace("\n", " ").replace("\r", " ");
        String json = "{ \"query\": \"" + compactQuery.replace("\"", "\\\"") + "\" }";
        HttpClient client = HttpClient.newHttpClient();

        try {
            HttpResponse<String> response = api.Api(client,json);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode data = mapper.readTree(response.body()).get("data").get("AtualizarConsumo");
            
            data : alert.MensagemSucess("Foi salvo com sucesso");

        } catch (Exception e) {
            // TODO: handle exception
            alert.MensagemError(""+e);

        }

    }
    public void Excluir(Vendas vendas, String id_produto) {

        String Mutation = mutationG.Consumo_Excluir.formatted(vendas.getCod_venda(),id_produto);
        String compactQuery = Mutation.replace("\n", " ").replace("\r", " ");
        String json = "{ \"query\": \"" + compactQuery.replace("\"", "\\\"") + "\" }";
        HttpClient client = HttpClient.newHttpClient();

        try {
            HttpResponse<String> response = api.Api(client,json);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode data = mapper.readTree(response.body()).get("data").get("AtualizarConsumo");
            
            data : alert.MensagemSucess("Foi excluido com sucesso");

        } catch (Exception e) {
            // TODO: handle exception
            alert.MensagemError(""+e);

        }

    }    

    public void ExcluirVenda(Vendas vendas) {
        String sql = "DELETE FROM consumo WHERE  id_venda = ?";
        PreparedStatement ps = null;

        try {
            ps = Conexao.conectar().prepareStatement(sql);
            ps.setString(1, vendas.getCod_venda());
            ps.execute();
            ps.close();

        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
