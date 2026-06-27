package br.udesc.doo2.cantina.repository;

import br.udesc.doo2.cantina.model.Refeicao;
import java.time.LocalDate;
import java.util.List;

public interface RefeicaoRepository {
    void salvar(Refeicao refeicao);
    void atualizar(Refeicao refeicao);
    Refeicao buscarPorId(int id);
    List<Refeicao> buscarTodas();
    List<Refeicao> buscarPorData(LocalDate data);
}