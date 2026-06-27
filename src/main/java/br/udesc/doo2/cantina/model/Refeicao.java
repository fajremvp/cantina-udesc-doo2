package br.udesc.doo2.cantina.model;

import java.time.LocalDate;
import java.util.List;

public class Refeicao {
    private int id;
    private LocalDate data;
    private String descricao;
    private float preco;
    private int capacidadeMaxima;
    private List<OpcaoCarne> carnes;

    public Refeicao(int id, LocalDate data, String descricao, float preco, int capacidadeMaxima, List<OpcaoCarne> carnes) {
        this.id = id;
        this.data = data;
        this.descricao = descricao;
        this.preco = preco;
        this.capacidadeMaxima = capacidadeMaxima;
        this.carnes = carnes;
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
    public List<OpcaoCarne> getCarnes() { return carnes; }
    public void setCarnes(List<OpcaoCarne> carnes) { this.carnes = carnes; }
}