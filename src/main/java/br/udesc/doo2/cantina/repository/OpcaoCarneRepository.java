package br.udesc.doo2.cantina.repository;

import br.udesc.doo2.cantina.model.OpcaoCarne;
import java.util.Set;

public interface OpcaoCarneRepository {
    void salvar(OpcaoCarne opcaoCarne);
    void atualizar(OpcaoCarne opcaoCarne);
    OpcaoCarne buscarPorNome(String nome);
    Set<OpcaoCarne> buscarTodas();
}