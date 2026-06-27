package br.udesc.doo2.cantina.model;

import java.time.LocalDate;

public class Comentario {
    private int id;
    private LocalDate data;
    private String descricao;
    private int nota;
    private Cliente cliente;

    public Comentario(int id, LocalDate data, String descricao, int nota, Cliente cliente) {
        this.id = id;
        this.data = data;
        this.descricao = descricao;
        this.nota = nota;
        this.cliente = cliente;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public int getNota() { return nota; }
    public void setNota(int nota) { this.nota = nota; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
}