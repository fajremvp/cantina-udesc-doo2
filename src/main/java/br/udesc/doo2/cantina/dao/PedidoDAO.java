package br.udesc.doo2.cantina.dao;

import br.udesc.doo2.cantina.enums.StatusPedido;
import br.udesc.doo2.cantina.enums.TipoConsumo;
import br.udesc.doo2.cantina.infra.Conexao;
import br.udesc.doo2.cantina.model.Pedido;
import br.udesc.doo2.cantina.repository.PedidoRepository;
import java.util.List;
import javax.persistence.EntityManager;

public class PedidoDAO implements PedidoRepository {

    @Override
    public void salvar(Pedido pedido) {
        EntityManager em = Conexao.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(pedido);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void atualizarStatus(int idPedido, StatusPedido novoStatus) {
        EntityManager em = Conexao.getEntityManager();

        try {
            em.getTransaction().begin();

            Pedido pedido = em.find(Pedido.class, idPedido);
            if (pedido == null) {
                throw new IllegalArgumentException("Pedido não encontrado.");
            }

            pedido.setStatus(novoStatus);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Pedido buscarPorId(int id) {
        EntityManager em = Conexao.getEntityManager();

        try {
            return em.find(Pedido.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Pedido> buscarTodos() {
        EntityManager em = Conexao.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT p FROM Pedido p ORDER BY p.dataHoraCriacao",
                    Pedido.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Pedido> buscarPorCliente(String nomeCliente) {
        EntityManager em = Conexao.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT p FROM Pedido p "
                    + "WHERE LOWER(p.cliente.nome) LIKE LOWER(:nomeCliente) "
                    + "ORDER BY p.dataHoraCriacao",
                    Pedido.class
            )
            .setParameter("nomeCliente", "%" + nomeCliente + "%")
            .getResultList();
        } finally {
            em.close();
        }
    }


    @Override
    public List<Pedido> buscarPorTipoConsumo(TipoConsumo tipoConsumo) {
         EntityManager em = Conexao.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT p FROM Pedido p "
                    + "WHERE p.tipoConsumo = :tipoConsumo "
                    + "ORDER BY p.dataHoraCriacao",
                    Pedido.class
            )
            .setParameter("tipoConsumo", tipoConsumo)
            .getResultList();
        } finally {
            em.close();
        }
    }
}
