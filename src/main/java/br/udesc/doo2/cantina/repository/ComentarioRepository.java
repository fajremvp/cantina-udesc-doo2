package br.udesc.doo2.cantina.repository;

import br.udesc.doo2.cantina.model.Comentario;
import java.util.List;

public interface ComentarioRepository {
    void salvar(Comentario comentario);
    List<Comentario> buscarTodos();
}