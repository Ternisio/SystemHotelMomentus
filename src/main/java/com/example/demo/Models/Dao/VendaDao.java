package com.example.demo.Models.Dao;

import com.example.demo.Models.Classes.*;
import com.example.demo.Models.Database.Conexao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class VendaDao {

    public ObservableList<Vendas> CodigodoVenda()  {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT idVendas FROM  vendas order by idVendas asc;";

        ObservableList<Vendas> lista = FXCollections.observableArrayList();
        try {
            ps = Conexao.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                Vendas vendas= new Vendas();
                vendas.setCod_venda(rs.getString("idVendas"));
                lista.add(vendas);
            }
            ps.close();
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }


        return lista;

    }
    public Double VendaporFuncionario(LocalDate Data_inicio, LocalDate Data_Fim, String Nome_Fun)  {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT sum(Total) FROM  vendas inner join funcionarios on vendas.id_fun=funcionarios.idFuncionarios where (Data_início between  '"+Data_inicio+" 07:00' and  '"+Data_Fim+" 7:00') and funcionarios.Nome_Fun = '"+Nome_Fun+"' ";
        Double Total = 0.00;
        try {
            ps = Conexao.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                Total = Total + rs.getDouble("sum(Total)");
            }
            ps.close();
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return Total;



    }
    public Double VendaDeHoje(LocalDate Data_inicio, LocalDate Data_Fim)  {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT sum(Total) FROM  vendas where Data_início between  '"+Data_inicio+" 07:00' and  '"+Data_Fim+" 7:00'";
        Double Total = 0.00;
        try {
            ps = Conexao.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                Total = Total + rs.getDouble("sum(Total)");
            }
            ps.close();
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return Total;



    }

    public Double Por_data_personalizadas_Inicial_fun(String Ano_Inicial, String Mes_Inicial,String Dia_Inicial, String mesP,String anoP,String Nome_fun)  {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT  cast(sum(Total) as decimal(12,2)) as Total_valor \n" +
                "FROM vendas inner join funcionarios on vendas.id_fun=funcionarios.idFuncionarios\n" +
                " WHERE (DATE_FORMAT(Data_início, '%Y-%m-%d %H:%i:%s') >= DATE_FORMAT(DATE_SUB(CONCAT('"+Ano_Inicial+"', '-', '"+Mes_Inicial+"', '-','"+Dia_Inicial+"'), INTERVAL 7 HOUR), '%Y-%m-%d %H:%i:%s')\n" +
                "  AND DATE_FORMAT(Data_início, '%Y-%m-%d %H:%i:%s') < DATE_FORMAT(DATE_ADD(CONCAT('"+anoP+"', '-', '"+mesP+"', '-','01'), INTERVAL 7 HOUR),'%Y-%m-%d %H:%i:%s')) AND funcionarios.Nome_Fun = '"+Nome_fun+"' " +
                "\t\t ";
        Double Total = 0.00;
        try {
            ps = Conexao.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                Total = Total + rs.getDouble("Total_valor");
            }
            ps.close();
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return Total;



    }
    public Double Por_data_personalizadas_Inicial(String Ano_Inicial, String Mes_Inicial,String Dia_Inicial, String mesP,String anoP)  {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT  cast(sum(Total) as decimal(12,2)) as Total_valor \n" +
                "FROM vendas\n" +
                " WHERE DATE_FORMAT(Data_início, '%Y-%m-%d %H:%i:%s') >= DATE_FORMAT(DATE_SUB(CONCAT('"+Ano_Inicial+"', '-', '"+Mes_Inicial+"', '-','"+Dia_Inicial+"'), INTERVAL 7 HOUR), '%Y-%m-%d %H:%i:%s')\n" +
                "  AND DATE_FORMAT(Data_início, '%Y-%m-%d %H:%i:%s') < DATE_FORMAT(DATE_ADD(CONCAT('"+anoP+"', '-', '"+mesP+"', '-','01'), INTERVAL 7 HOUR),'%Y-%m-%d %H:%i:%s');\n" +
                "\t\t ";
        Double Total = 0.00;
        try {
            ps = Conexao.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                Total = Total + rs.getDouble("Total_valor");
            }
            ps.close();
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return Total;



    }
      public Double Por_data_personalizadas_alt(String Ano_Inicial, String Mes_Inicial, String mesP,String anoP)  {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT  cast(sum(Total) as decimal(12,2)) as Total_valor \n" +
                "FROM vendas\n" +
                " WHERE DATE_FORMAT(Data_início, '%Y-%m-%d %H:%i:%s') >= DATE_FORMAT(DATE_SUB(CONCAT('"+Ano_Inicial+"', '-', '"+Mes_Inicial+"', '-','01'), INTERVAL 7 HOUR), '%Y-%m-%d %H:%i:%s')\n" +
                "  AND DATE_FORMAT(Data_início, '%Y-%m-%d %H:%i:%s') < DATE_FORMAT(DATE_ADD(CONCAT('"+anoP+"', '-', '"+mesP+"', '-','01'), INTERVAL 7 HOUR),'%Y-%m-%d %H:%i:%s');\n" +
                "\t\t ";
        Double Total = 0.00;
        try {
            ps = Conexao.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                Total = Total + rs.getDouble("Total_valor");
            }
            ps.close();
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return Total;



    }
    public Double Por_data_personalizadas_alt_fun(String Ano_Inicial, String Mes_Inicial, String mesP,String anoP,String Nome)  {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT  cast(sum(Total) as decimal(12,2)) as Total_valor \n" +
                "FROM vendas inner join funcionarios on vendas.id_fun=funcionarios.idFuncionarios\n" +
                " WHERE DATE_FORMAT(Data_início, '%Y-%m-%d %H:%i:%s') >= DATE_FORMAT(DATE_SUB(CONCAT('"+Ano_Inicial+"', '-', '"+Mes_Inicial+"', '-','01'), INTERVAL 7 HOUR), '%Y-%m-%d %H:%i:%s')\n" +
                "  AND DATE_FORMAT(Data_início, '%Y-%m-%d %H:%i:%s') < DATE_FORMAT(DATE_ADD(CONCAT('"+anoP+"', '-', '"+mesP+"', '-','01'), INTERVAL 7 HOUR),'%Y-%m-%d %H:%i:%s')AND funcionarios.Nome_Fun = '"+Nome+"'\n" +
                "\t\t ";
        Double Total = 0.00;
        try {
            ps = Conexao.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                Total = Total + rs.getDouble("Total_valor");
            }
            ps.close();
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return Total;



    }

    public Double Por_data_personalizadas_Fim(String Ano_Inicial, String Mes_Inicial,String Dia_Inicial)  {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT  cast(sum(Total) as decimal(12,2)) as Total_valor \n" +
                "FROM vendas\n" +
                " WHERE DATE_FORMAT(Data_início, '%Y-%m-%d %H:%i:%s') >= DATE_FORMAT(DATE_SUB(CONCAT('"+Ano_Inicial+"', '-', '"+Mes_Inicial+"', '-','01'), INTERVAL 7 HOUR), '%Y-%m-%d %H:%i:%s')\n" +
                "  AND DATE_FORMAT(Data_início, '%Y-%m-%d %H:%i:%s') < DATE_FORMAT(DATE_ADD(CONCAT('"+Ano_Inicial+"', '-', '"+Mes_Inicial+"', '-','"+Dia_Inicial+"'), INTERVAL 7 HOUR),'%Y-%m-%d %H:%i:%s');\n" +
                "\t\t ";
        Double Total = 0.00;
        try {
            ps = Conexao.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                Total = Total + rs.getDouble("Total_valor");
            }
            ps.close();
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return Total;



    }
    public Double Por_data_personalizadas_Fim_fun(String Ano_Inicial, String Mes_Inicial,String Dia_Inicial,String Nome)  {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT  cast(sum(Total) as decimal(12,2)) as Total_valor \n" +
                "FROM vendas inner join funcionarios on vendas.id_fun=funcionarios.idFuncionarios\n" +
                " WHERE DATE_FORMAT(Data_início, '%Y-%m-%d %H:%i:%s') >= DATE_FORMAT(DATE_SUB(CONCAT('"+Ano_Inicial+"', '-', '"+Mes_Inicial+"', '-','01'), INTERVAL 7 HOUR), '%Y-%m-%d %H:%i:%s')\n" +
                "  AND DATE_FORMAT(Data_início, '%Y-%m-%d %H:%i:%s') < DATE_FORMAT(DATE_ADD(CONCAT('"+Ano_Inicial+"', '-', '"+Mes_Inicial+"', '-','"+Dia_Inicial+"'), INTERVAL 7 HOUR),'%Y-%m-%d %H:%i:%s' AND funcionarios.Nome_Fun = '"+Nome+"'\n" +
                "\t\t ";
        Double Total = 0.00;
        try {
            ps = Conexao.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                Total = Total + rs.getDouble("Total_valor");
            }
            ps.close();
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return Total;



    }

    public int TotaldeEntrada(LocalDate Data_inicio, LocalDate Data_Fim)  {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select count(vendas.Status) as Numero_entrada from vendas where vendas.Status=\"Realizando\"\n" +
                " and vendas.Data_início between  '"+Data_inicio+" 07:00' and  '"+Data_Fim+" 7:00'";
        int Total = 0;
        try {
            ps = Conexao.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                Total = Total + rs.getInt("Numero_entrada");
            }
            ps.close();
            rs.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return Total;



    }
    public int TotaldeSainda(LocalDate Data_inicio, LocalDate Data_Fim)  {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select count(vendas.Status) as Numero_saida from vendas where vendas.Status=\"Finalizado\"\n" +
                " and vendas.Data_início between  '"+Data_inicio+" 07:00' and  '"+Data_Fim+" 7:00'";
        int Total = 0;
        try {
            ps = Conexao.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                Total = Total + rs.getInt("Numero_saida");
            }
            ps.close();
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return Total;



    }
    public Double TotaldeConsumo(LocalDate Data_inicio, LocalDate Data_Fim)  {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select sum(produtos.Valor_prod * consumo.Quantidade) AS total from consumo\n" +
                " inner join vendas on consumo.id_venda = vendas.idVendas\n" +
                " inner join produtos on consumo.id_produto = produtos.idProdutos\n" +
                " where vendas.Data_início between  '"+Data_inicio+" 07:00' and  '"+Data_Fim+" 7:00'";
        Double Total = 0.00;
        try {
            ps = Conexao.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                Total = Total + rs.getDouble("total");
            }
            ps.close();
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return Total;



    }
    public ObservableList ListaDeConsumodehoje(LocalDate Data_inicio, LocalDate Data_Fim)  {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT  quartos.num_quartos, sum(produtos.Valor_prod*consumo.Quantidade) as Total from  consumo \n" +
                "inner join vendas on consumo.id_venda = vendas.idVendas\n" +
                "inner join produtos on consumo.id_produto = produtos.idProdutos\n" +
                "inner join quartos on vendas.id_quartos = quartos.idquartos where vendas.Data_início between '"+Data_inicio+" 07:00'  and '"+Data_Fim+"' group by consumo.id_venda; ";
        ObservableList<Consumo> lista = FXCollections.observableArrayList();


        try {
            ps = Conexao.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                Consumo consumo = new Consumo(rs.getString("quartos.num_quartos"),String.format("%.2f",rs.getDouble("Total")));
                lista.add(consumo);

            }
            ps.close();
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }



        return lista;
    }
    public ObservableList ListaDevendadehoje(LocalDate Data_inicio, LocalDate Data_Fim)  {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT  quartos.num_quartos, vendas.Data_início, vendas.Data_fim, vendas.Status \n" +
                "from vendas inner join quartos on vendas.id_quartos = quartos.idquartos  \n" +
                "where Data_início between  '"+Data_inicio+" 07:00' and  '"+Data_Fim+" 7:00' ";
        ObservableList<Vendas> lista = FXCollections.observableArrayList();


        try {
            ps = Conexao.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                Timestamp timestampDI = rs.getTimestamp("vendas.Data_início");
                Timestamp timestampDF = rs.getTimestamp("vendas.Data_fim");
                if (timestampDF == null){
                    Vendas vendas = new Vendas(rs.getString("vendas.Status"), rs.getString("quartos.num_quartos"),timestampDI.toLocalDateTime(),null);
                    lista.add(vendas);
                }else {
                    Vendas vendas = new Vendas(rs.getString("vendas.Status"), rs.getString("quartos.num_quartos"), timestampDI.toLocalDateTime(), timestampDF.toLocalDateTime());
                    lista.add(vendas);
                }

            }
            ps.close();
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }



   return lista;
    }

    public ObservableList ListaDevendaPorquarto(LocalDate Data_inicio, LocalDate Data_Fim)  {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT vendas.idVendas,funcionarios.Nome_Fun, quartos.num_quartos, vendas.Data_início, vendas.Data_fim, vendas.Status, vendas.Total \n" +
                "from vendas inner join quartos on vendas.id_quartos = quartos.idquartos  \n" +
                "inner join funcionarios on vendas.id_fun = funcionarios.idFuncionarios where Data_início between  '"+Data_inicio+" 07:00' and  '"+Data_Fim+" 7:00' ";
        ObservableList<Vendas> lista = FXCollections.observableArrayList();


        try {
            ps = Conexao.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                Timestamp timestampDI = rs.getTimestamp("vendas.Data_início");
                Timestamp timestampDF = rs.getTimestamp("vendas.Data_fim");
                if (timestampDF == null){
                    Vendas vendas = new Vendas(rs.getString("vendas.idVendas"),rs.getString("vendas.Status"), rs.getString("funcionarios.Nome_Fun"),rs.getString("quartos.num_quartos"),timestampDI.toLocalDateTime(),null,rs.getDouble("vendas.Total"));
                    lista.add(vendas);
                }else {
                    Vendas vendas = new Vendas(rs.getString("vendas.idVendas"),rs.getString("vendas.Status"), rs.getString("funcionarios.Nome_Fun"),rs.getString("quartos.num_quartos"),timestampDI.toLocalDateTime(), timestampDF.toLocalDateTime(), rs.getDouble("vendas.Total"));
                    lista.add(vendas);
                }

            }
            ps.close();
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }



        return lista;
    }


    public void BuscarRealizando(Quarto quarto, Vendas vendas,Funcionario funcionario)  {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT vendas.idVendas, funcionarios.Nome_Fun, quartos.num_quartos, vendas.Data_início \n" +
                "from vendas inner join quartos on vendas.id_quartos = quartos.idquartos  \n" +
                "inner join funcionarios on vendas.id_fun = funcionarios.idFuncionarios WHERE vendas.id_quartos = "+quarto.getCod_Quarto()+" and vendas.Status=\"Realizando\"; ";


        try {
            ps = Conexao.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                vendas.setCod_venda(rs.getString("vendas.idVendas"));
                funcionario.setNome_Fun(rs.getString("funcionarios.Nome_Fun"));
                Timestamp timestamp = rs.getTimestamp("vendas.Data_início");
                vendas.setData_hora_Entrada(timestamp.toLocalDateTime());

            }
            ps.close();
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }




    }
    public void cadastrar(Funcionario funcionario, Vendas vendas, Quarto quarto) {
        String sql = "INSERT INTO vendas(idVendas,id_fun,id_quartos,Data_início,Status)" +
                "  VALUES(?,?,?,?,?)";
        PreparedStatement ps = null;


        try {
            ps = Conexao.conectar().prepareStatement(sql);
            ps.setString(1, vendas.getCod_venda());
            ps.setString(2, funcionario.getId_Fun());
            ps.setInt(3, quarto.getCod_Quarto());
            ps.setObject(4, LocalDateTime.now());
            ps.setString(5, "Realizando");
            ps.execute();
            ps.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Aviso:");
            alert.setContentText("Foi cadastrado com sucesso");
            alert.showAndWait();

        }
        catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }
    public void Editarq(String Num, String status) {
        String sql = "UPDATE quartos SET Status_quartos = ? where num_quartos = ?";
        PreparedStatement ps = null;


        try {
            ps = Conexao.conectar().prepareStatement(sql);
            ps.setString(1, status);
            ps.setString(2, Num);
            ps.execute();
            ps.close();

        } catch (SQLException e) {
            // TODO: handle exception
        }
    }
    public void finalizar(Vendas vendas){

        String sql = "UPDATE vendas SET Data_fim = ?,Total = ?, Status = ? where idVendas = ?";
        PreparedStatement ps = null;

        try {
            ps = Conexao.conectar().prepareStatement(sql);
            ps.setObject(1,LocalDateTime.now());
            ps.setDouble(2, vendas.getTotal());
            ps.setString(3,vendas.getStatus());
            ps.setString(4,vendas.getCod_venda());

            ps.execute();
            ps.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Aviso:");
            alert.setContentText("Foi finalizado com sucesso");
            alert.showAndWait();

        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    public void Cancelado(Vendas vendas){

        String sql = "UPDATE vendas SET Data_fim = ?, Status = ? where idVendas = ?";
        PreparedStatement ps = null;
        String cancelado = "Cancelado";
        try {
            ps = Conexao.conectar().prepareStatement(sql);
            ps.setObject(1,LocalDateTime.now());
            ps.setString(2,cancelado);
            ps.setString(3,vendas.getCod_venda());

            ps.execute();
            ps.close();

        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    public void EditarVParaFinalizado(Vendas vendas){

        String sql = "UPDATE vendas SET Data_fim = ?,  id_quartos = ?, Total = ? , Status = ? where idVendas = ?";
        PreparedStatement ps = null;

        try {
            ps = Conexao.conectar().prepareStatement(sql);
            ps.setObject(1,vendas.getData_hora_Saida());
            ps.setInt(2,vendas.getCod_Quarto());
            ps.setDouble(3,vendas.getTotal());
            ps.setString(4,vendas.getStatus());
            ps.setString(5,vendas.getCod_venda());

            ps.execute();
            ps.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Aviso:");
            alert.setContentText("Foi alterado com sucesso");
            alert.showAndWait();

        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    public void EditarVParaRealizando(Vendas vendas){

        String sql = "UPDATE vendas SET Data_fim = ?,Total = ? , Status = ? , id_quartos = ?,Status = ?  where idVendas = ?";
        PreparedStatement ps = null;

        try {
            ps = Conexao.conectar().prepareStatement(sql);
            ps.setObject(1,null);
            ps.setDouble(2,vendas.getTotal());
            ps.setString(3,vendas.getStatus());
            ps.setInt(4,vendas.getCod_Quarto());
            ps.setString(5,vendas.getStatus());
            ps.setString(6,vendas.getCod_venda());

            ps.execute();
            ps.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Aviso:");
            alert.setContentText("Foi alterado com sucesso");
            alert.showAndWait();

        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void excluir(Vendas vendas){

        String sql = "DELETE FROM vendas where idVendas = ?";
        PreparedStatement ps = null;

        try {
            ps = Conexao.conectar().prepareStatement(sql);
            ps.setString(1,vendas.getCod_venda());

            ps.execute();
            ps.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Aviso:");
            alert.setContentText("Foi excluido com sucesso");
            alert.showAndWait();

        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

}
