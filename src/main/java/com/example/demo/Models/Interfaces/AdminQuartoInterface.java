package com.example.demo.Models.Interfaces;

import com.example.demo.Models.Classes.Quarto;
import com.example.demo.Models.Dao.QuartoDao;

public interface AdminQuartoInterface {
    public boolean VerificarCampos(String Num, String Estado);
    public boolean VerificarNumero(String Num, QuartoDao quartoDao);
    void Excluir_e_Editar(boolean Excluir, boolean Editar, Quarto quarto, QuartoDao quartoDao);
    public void Excluir_Tudo(QuartoDao quartoDao);
    public int cod_quarto(QuartoDao quartoDao);
}
