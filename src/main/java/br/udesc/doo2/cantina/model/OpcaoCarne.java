package br.udesc.doo2.cantina.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "opcao_carne")
public class OpcaoCarne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String nome;
    private int quantidadeDisponivel;
    
    @ManyToMany(mappedBy = "carnes")
    private Set<Refeicao> refeicoes = new HashSet<>();

    public OpcaoCarne() {
    }

    public OpcaoCarne(String nome, int quantidadeDisponivel) {
        this.nome = nome;
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getQuantidadeDisponivel() { return quantidadeDisponivel; }
    public void setQuantidadeDisponivel(int quantidadeDisponivel) { this.quantidadeDisponivel = quantidadeDisponivel; }
    
    public Set<Refeicao> getRefeicoes() {
        return refeicoes;
    }

    public void setRefeicoes(Set<Refeicao> refeicoes) {
        this.refeicoes = refeicoes;
    }

    @Override
    public String toString() {
        return nome;
    }
    
}