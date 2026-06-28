package br.udesc.doo2.cantina.model;

public class OpcaoCarne {
    private int id;
    private String nome;
    private int quantidadeDisponivel;
    
    private static int geradorId=0;

    public OpcaoCarne(String nome, int quantidadeDisponivel) {
        this.id = ++geradorId;
        this.nome = nome;
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getQuantidadeDisponivel() { return quantidadeDisponivel; }
    public void setQuantidadeDisponivel(int quantidadeDisponivel) { this.quantidadeDisponivel = quantidadeDisponivel; }
}