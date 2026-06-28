package br.udesc.doo2.cantina.dao;

import br.udesc.doo2.cantina.model.Refeicao;
import br.udesc.doo2.cantina.repository.RefeicaoRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RefeicaoDAO implements RefeicaoRepository {

    public static List<Refeicao> refeicoes = new ArrayList<>();
    
    @Override
    public void salvar(Refeicao refeicao) {
        refeicoes.add(refeicao);
    }

    @Override
    public void atualizar(Refeicao refeicao) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Refeicao buscarPorId(int id) {
        for (Refeicao r : refeicoes) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }

    @Override
    public List<Refeicao> buscarTodas() {
        return refeicoes;
    }

    @Override
    public Refeicao buscarPorData(LocalDate data) {
        for (Refeicao r : refeicoes) {
            if (r.getData() == data) {
                return r;
            }
        }
        return null;
    }
    
}
