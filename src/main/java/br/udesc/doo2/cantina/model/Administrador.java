package br.udesc.doo2.cantina.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Não possui matrícula e não se autocadastra pela aplicação -
 * é inserido diretamente no banco via database/seed.sql.
 */

@Entity
@DiscriminatorValue("ADMINISTRADOR")
public class Administrador extends Usuario {

    protected Administrador() {
        super();
    }

    public Administrador(int id, String nome, String senha) {
        super(id, nome, senha);
    }
}