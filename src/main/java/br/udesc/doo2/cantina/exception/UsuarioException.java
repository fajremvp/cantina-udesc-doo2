package br.udesc.doo2.cantina.exception;

/**
 * Exceção de negócio relacionada a operações com Usuario (Cliente ou
 * Administrador): falha de autenticação, matrícula duplicada, dados
 * inválidos, ou erro de persistência encapsulado.
 */

public class UsuarioException extends Exception {

    public UsuarioException(String mensagem) {
        super(mensagem);
    }

    public UsuarioException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
