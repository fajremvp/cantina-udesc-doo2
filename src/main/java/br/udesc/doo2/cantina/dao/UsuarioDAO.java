package br.udesc.doo2.cantina.dao;

import br.udesc.doo2.cantina.exception.UsuarioException;
import br.udesc.doo2.cantina.infra.Conexao;
import br.udesc.doo2.cantina.model.Administrador;
import br.udesc.doo2.cantina.model.Cliente;
import br.udesc.doo2.cantina.model.Usuario;
import br.udesc.doo2.cantina.repository.UsuarioRepository;
import br.udesc.doo2.cantina.util.SenhaUtils;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * Executa as consultas JPQL via EntityManager (obtido de Conexao) e
 * é responsável por converter a senha em texto puro (char[]) para o
 * hash SHA-256 (String) antes de persistir ou comparar com o banco.
 */

public class UsuarioDAO implements UsuarioRepository {

    @Override
    public void salvar(Usuario usuario) throws UsuarioException {
        EntityManager em = Conexao.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new UsuarioException("Erro ao salvar usuário: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public void atualizar(Usuario usuario) throws UsuarioException {
        EntityManager em = Conexao.getEntityManager();
        try {
            em.getTransaction().begin();
            // merge() reanexa/atualiza uma entidade que já existe no banco
            // (diferente de persist(), que é só para entidades novas)
            em.merge(usuario);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new UsuarioException("Erro ao atualizar usuário: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public Usuario buscarPorId(int id) throws UsuarioException {
        EntityManager em = Conexao.getEntityManager();
        try {
            // find() busca a entidade pela chave primária diretamente,
            // sem necessidade de JPQL aqui
            return em.find(Usuario.class, id);
        } catch (RuntimeException e) {
            throw new UsuarioException("Erro ao buscar usuário por id: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public Cliente buscarPorMatricula(String matricula) throws UsuarioException {
        EntityManager em = Conexao.getEntityManager();
        try {
            // JPQL: busca um Cliente (subtipo de Usuario) filtrando pela
            // coluna "matricula", específica da subclasse Cliente.
            TypedQuery<Cliente> query = em.createQuery(
                    "SELECT c FROM Cliente c WHERE c.matricula = :matricula",
                    Cliente.class
            );
            query.setParameter("matricula", matricula);
            return query.getSingleResult();
        } catch (NoResultException e) {
            // Nenhum cliente encontrado com essa matrícula - retorno null é
            // um resultado válido aqui (ex: checagem de duplicidade no
            // cadastro), não é um erro.
            return null;
        } catch (RuntimeException e) {
            throw new UsuarioException("Erro ao buscar usuário por matrícula: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public Cliente autenticarCliente(String matricula, char[] senha) throws UsuarioException {
        EntityManager em = Conexao.getEntityManager();
        try {
            // Convertemos a senha em texto puro para hash ANTES de montar a
            // query - o banco nunca vê a senha em texto plano, só o hash.
            String hashSenha = SenhaUtils.gerarHash(senha);

            // JPQL: busca o Cliente cuja matrícula E hash de senha batem
            // exatamente com o que foi digitado. Sem resultado = credenciais inválidas.
            TypedQuery<Cliente> query = em.createQuery(
                    "SELECT c FROM Cliente c WHERE c.matricula = :matricula AND c.senha = :senha",
                    Cliente.class
            );
            query.setParameter("matricula", matricula);
            query.setParameter("senha", hashSenha);

            return query.getSingleResult();

        } catch (NoResultException e) {
            throw new UsuarioException("Matrícula ou senha inválidos.", e);
        } finally {
            em.close();
        }
    }

    @Override
    public Administrador autenticarAdministrador(char[] senha) throws UsuarioException {
        EntityManager em = Conexao.getEntityManager();
        try {
            String hashSenha = SenhaUtils.gerarHash(senha);

            // JPQL: busca o Administrador cujo hash de senha bate com o
            // digitado. Não há identificador (matrícula) para Admin - a
            // senha sozinha precisa ser suficiente, assumindo 1 único
            // Administrador pré-cadastrado via seed.sql.
            TypedQuery<Administrador> query = em.createQuery(
                    "SELECT a FROM Administrador a WHERE a.senha = :senha",
                    Administrador.class
            );
            query.setParameter("senha", hashSenha);

            return query.getSingleResult();

        } catch (NoResultException e) {
            throw new UsuarioException("Senha de administrador inválida.", e);
        } finally {
            em.close();
        }
    }
}