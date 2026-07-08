package br.udesc.doo2.cantina.model;

import java.util.Objects;
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.matricula);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cliente other = (Cliente) obj;
        return Objects.equals(this.matricula, other.matricula);
    }
    
}