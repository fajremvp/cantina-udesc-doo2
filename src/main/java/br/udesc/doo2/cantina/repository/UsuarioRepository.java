package br.udesc.doo2.cantina.repository;

import br.udesc.doo2.cantina.exception.UsuarioException;
import br.udesc.doo2.cantina.model.Administrador;
import br.udesc.doo2.cantina.model.Cliente;
import br.udesc.doo2.cantina.model.Usuario;

/**
 * IMPORTANTE: a senha é recebida como char[] - texto puro, capturado da
 * JPasswordField na View. A conversão para hash (String) acontece DENTRO
 * do DAO (via SenhaUtils), nunca na interface ou na View.
 */

public interface UsuarioRepository {
    void salvar(Usuario usuario) throws UsuarioException;
    void atualizar(Usuario usuario) throws UsuarioException;
    Usuario buscarPorId(int id) throws UsuarioException;
    Cliente buscarPorMatricula(String matricula) throws UsuarioException;
    Cliente autenticarCliente(String matricula, char[] senha) throws UsuarioException;
    Administrador autenticarAdministrador(char[] senha) throws UsuarioException;
}