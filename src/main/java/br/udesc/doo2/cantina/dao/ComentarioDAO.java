package br.udesc.doo2.cantina.dao;

import br.udesc.doo2.cantina.infra.Conexao;
import br.udesc.doo2.cantina.model.Comentario;
import br.udesc.doo2.cantina.repository.ComentarioRepository;
import java.util.List;
import javax.persistence.EntityManager;

public class ComentarioDAO implements ComentarioRepository {

    @Override
    public void salvar(Comentario comentario) {
        EntityManager em = Conexao.getEntityManager();
        
        try {
            em.getTransaction().begin();
            em.persist(comentario);
            em.getTransaction().commit();
        } catch(RuntimeException e) {
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
        }
    }

    @Override
    public List<Comentario> buscarTodos() {
        EntityManager em = Conexao.getEntityManager();
        
        try {
            return em.createQuery(
                "SELECT c FROM Comentario c ORDER BY c.data DESC, c.id", Comentario.class
            ).getResultList();
        } finally {
            em.close();
        }
    }
    
}
