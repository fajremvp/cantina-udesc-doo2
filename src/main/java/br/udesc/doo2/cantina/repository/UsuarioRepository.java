package br.udesc.doo2.cantina.repository;

import br.udesc.doo2.cantina.model.Administrador;
import br.udesc.doo2.cantina.model.Cliente;
import br.udesc.doo2.cantina.model.Usuario;

public interface UsuarioRepository {
    void salvar(Usuario usuario);
    Usuario buscarPorId(int id);
    Cliente autenticarCliente(String matricula, char[] senha);
    Administrador autenticarAdministrador(char[] senha);
}