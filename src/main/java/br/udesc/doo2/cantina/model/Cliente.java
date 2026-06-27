package br.udesc.doo2.cantina.model;

public class Cliente extends Usuario {
    private String matricula;

    public Cliente(int id, String nome, char[] senha, String matricula) {
        super(id, nome, senha);
        this.matricula = matricula;
    }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
}