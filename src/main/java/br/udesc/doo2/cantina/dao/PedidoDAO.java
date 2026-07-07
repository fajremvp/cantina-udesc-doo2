package br.udesc.doo2.cantina.dao;

import br.udesc.doo2.cantina.enums.StatusPedido;
import br.udesc.doo2.cantina.enums.TipoConsumo;
import br.udesc.doo2.cantina.infra.Conexao;
import br.udesc.doo2.cantina.model.Pedido;
import br.udesc.doo2.cantina.repository.PedidoRepository;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManager;


public class PedidoDAO implements PedidoRepository {

    @Override
    public void salvar(Pedido pedido) {
        EntityManager em = Conexao.getEntityManager(); //abre conexão
        // salva no banco
        try {
            em.getTransaction().begin();
            em.persist(pedido);
            em.getTransaction().commit();
        } catch (RuntimeException e) { // se não der certo lança exceção e desfaz a transação do salvamento das informações
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally { //finaliza e fecha conexão
            em.close();
        }
    }

    /** localiza o pedido e altera seu status de forma transacional. satatus: Realizado/concluido*/
    @Override
    public void atualizarStatus(int idPedido, StatusPedido novoStatus) {
        EntityManager em = Conexao.getEntityManager();

        try {
            em.getTransaction().begin();

            Pedido pedido = em.find(Pedido.class, idPedido); //busca pelo id do pedido
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

    /** Verifica se o cliente ja realizou um pedido para esta refeicao. */
    @Override
    public boolean existePorClienteERefeicao(int idCliente, int idRefeicao) {
        EntityManager em = Conexao.getEntityManager();

        try {
            Long quantidade = em.createQuery(
                    "SELECT COUNT(p) FROM Pedido p "
                    + "WHERE p.cliente.id = :idCliente "
                    + "AND p.refeicao.id = :idRefeicao",
                    Long.class
            )
            .setParameter("idCliente", idCliente)
            .setParameter("idRefeicao", idRefeicao)
            .getSingleResult();

            return quantidade > 0;
        } finally {
            em.close();
        }
    }

    /** Retorna todos os pedidos ordenados pela data de criacao. */
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

    /**
     * Busca pelo nome completo ou parcial do cliente, ignorando maiusculas.
     */
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


    /** Filtra os pedidos pelo tipo LOCAL ou LEVAR. */
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
    
    @Override
    public List<Pedido> buscarPorData(LocalDate dataInicial, LocalDate dataFinal) {
        EntityManager em = Conexao.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT p FROM Pedido p "
                  + "WHERE p.dataHoraCriacao BETWEEN :dataInicial AND :dataFinal "
                  + "ORDER BY p.dataHoraCriacao",
                    Pedido.class)
                    .setParameter("dataInicial", dataInicial)
                    .setParameter("dataFinal", dataFinal)
                    .getResultList();
        } finally {
            em.close();
        }
    }
    
}
