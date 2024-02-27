package com.example.demo.Models.Services;

import com.example.demo.Models.Database.Conexao;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

public class ExecutarRelatorio {

    public  void ExecutarRelatorioLista_Vendas(Date Inicio, Date Fim){
        try {
            File file = new File("Relatorios/");
            String Atp = file.getAbsolutePath();
            File fileI = new File("Imagens/");
            String AtpI = fileI.getAbsolutePath();
            JasperDesign design = JRXmlLoader.load(Atp+"/RelatoriodeVendas.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            HashMap map = new HashMap();
            map.put("Data_inicio",Inicio);
            map.put("Data_Fim",Fim);
            map.put("imageDir",AtpI);
            JasperPrint Print= JasperFillManager.fillReport(jasperReport,map,Conexao.conectar());
            JasperViewer.viewReport(Print,false);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

}
