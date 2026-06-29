package br.udesc.doo2.cantina.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "refeicao")
public class Refeicao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private LocalDate data;
    private String descricao;
    private float preco;
    private int capacidadeMaxima;
    
    @ManyToMany
    @JoinTable(
        name = "refeicao_carne",
        joinColumns = @JoinColumn(name = "refeicao_id"),
        inverseJoinColumns = @JoinColumn(name = "carne_id")
    )
    private Set<OpcaoCarne> carnes = new HashSet<>();

    public Refeicao() {
    }
    
    public Refeicao(LocalDate data, String descricao, float preco, int capacidadeMaxima) {
        this.data = data;
        this.descricao = descricao;
        this.preco = preco;
        this.capacidadeMaxima = capacidadeMaxima;
    }
    
    public void salvarCarne(OpcaoCarne carne) {
        carnes.add(carne);
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public float getPreco() { return preco; }
    public void setPreco(float preco) { this.preco = preco; }
    public int getCapacidadeMaxima() { return capacidadeMaxima; }
    public void setCapacidadeMaxima(int capacidadeMaxima) { this.capacidadeMaxima = capacidadeMaxima; }
    public Set<OpcaoCarne> getCarnes() { return carnes; }
}