package br.udesc.doo2.cantina.repository;

import br.udesc.doo2.cantina.model.OpcaoCarne;

public interface OpcaoCarneRepository {
    void salvar(OpcaoCarne opcaoCarne);
    void atualizar(OpcaoCarne opcaoCarne);
    OpcaoCarne buscarPorId(int id);
}