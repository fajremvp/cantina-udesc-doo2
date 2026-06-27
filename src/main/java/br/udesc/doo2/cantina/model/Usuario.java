package br.udesc.doo2.cantina.model;

public abstract class Usuario {
    private int id;
    private String nome;
    private char[] senha;

    public Usuario(int id, String nome, char[] senha) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public char[] getSenha() { return senha; }
    public void setSenha(char[] senha) { this.senha = senha; }
}