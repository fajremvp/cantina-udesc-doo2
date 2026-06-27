package br.udesc.doo2.cantina.infra;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Conexao {

	private static EntityManagerFactory emf;

	// Construtor privado para impedir instanciamento (Padrão Singleton)
	private Conexao() {
	}

	public static EntityManager getEntityManager() {
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory("cantinaPU");
		}
		return emf.createEntityManager();
	}

	public static void fecharConexao() {
		if (emf != null && emf.isOpen()) {
			emf.close();
		}
	}
}
