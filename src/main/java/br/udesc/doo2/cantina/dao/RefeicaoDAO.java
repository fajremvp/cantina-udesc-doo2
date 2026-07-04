package br.udesc.doo2.cantina.dao;

import br.udesc.doo2.cantina.infra.Conexao;
import br.udesc.doo2.cantina.model.Refeicao;
import br.udesc.doo2.cantina.repository.RefeicaoRepository;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.*;

public class RefeicaoDAO implements RefeicaoRepository {
    
    @Override
    public void salvar(Refeicao refeicao) {

        EntityManager em = Conexao.getEntityManager();

        try {

            em.getTransaction().begin();

            em.persist(refeicao);

            em.getTransaction().commit();

        } catch (Exception e) {

            em.getTransaction().rollback();
            throw e;

        } finally {

            em.close();
        }
    }

    @Override
    public void atualizar(Refeicao refeicao) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Refeicao buscarPorId(int id) {

        EntityManager em = Conexao.getEntityManager();

        try {
            return em.find(Refeicao.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Refeicao> buscarTodas() {

        EntityManager em = Conexao.getEntityManager();

        try {

            return em.createQuery(
                    "FROM Refeicao",
                    Refeicao.class
            ).getResultList();

        } finally {

            em.close();
        }
    }

    @Override
    public Refeicao buscarPorData(LocalDate data) {

        EntityManager em = Conexao.getEntityManager();

        try {

            return em.createQuery(
                    "FROM Refeicao r WHERE r.data = :data",
                    Refeicao.class
            )
            .setParameter("data", data)
            .getSingleResult();

        } finally {

            em.close();
        }
    }
    
}
