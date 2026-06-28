package br.udesc.doo2.cantina.dao;

import br.udesc.doo2.cantina.model.OpcaoCarne;
import br.udesc.doo2.cantina.repository.OpcaoCarneRepository;
import java.util.HashSet;
import java.util.Set;

public class OpcaoCarneDAO implements OpcaoCarneRepository {

    public static Set<OpcaoCarne> carnes = new HashSet<>();
    
    @Override
    public void salvar(OpcaoCarne opcaoCarne) {
        carnes.add(opcaoCarne);
    }

    @Override
    public void atualizar(OpcaoCarne opcaoCarne) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public OpcaoCarne buscarPorNome(String nome) {
        for (OpcaoCarne c : carnes) {
            if (c.getNome().equals(nome)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public Set<OpcaoCarne> buscarTodas() {
        return carnes;
    }
    
}
