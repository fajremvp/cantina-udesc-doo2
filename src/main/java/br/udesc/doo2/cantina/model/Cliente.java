package br.udesc.doo2.cantina.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Diferencia-se do Administrador por possuir
 * matrícula e poder se autocadastrar.
 */

@Entity
@DiscriminatorValue("CLIENTE")
public class Cliente extends Usuario {

    private String matricula;

    protected Cliente() {
        super();
    }

    public Cliente(int id, String nome, String senha, String matricula) {
        super(id, nome, senha);
        this.matricula = matricula;
    }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
}