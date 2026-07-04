package br.udesc.doo2.cantina.dao;

import br.udesc.doo2.cantina.infra.Conexao;
import br.udesc.doo2.cantina.model.OpcaoCarne;
import br.udesc.doo2.cantina.repository.OpcaoCarneRepository;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

public class OpcaoCarneDAO implements OpcaoCarneRepository {
    
    @Override
    public void salvar(OpcaoCarne opcaoCarne) {

        EntityManager em = Conexao.getEntityManager();

        try {

            em.getTransaction().begin();

            em.persist(opcaoCarne);

            em.getTransaction().commit();

        } catch (Exception e) {

            em.getTransaction().rollback();
            throw e;

        } finally {

            em.close();
        }
    }

    @Override
    public void atualizar(OpcaoCarne opcaoCarne) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public OpcaoCarne buscarPorNome(String nome) {

        EntityManager em = Conexao.getEntityManager();

        try {

            return em.createQuery(
                    "FROM OpcaoCarne c WHERE c.nome = :nome",
                    OpcaoCarne.class
            )
            .setParameter("nome", nome)
            .getSingleResult();

        } finally {

            em.close();
        }
    }

    @Override
    public Set<OpcaoCarne> buscarTodas() {

        EntityManager em = Conexao.getEntityManager();

        try {

            return new HashSet<>(
                em.createQuery(
                    "FROM OpcaoCarne",
                    OpcaoCarne.class
                ).getResultList()
            );

        } finally {

            em.close();
        }
    }
    
}
