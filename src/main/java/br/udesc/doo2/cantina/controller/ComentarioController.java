package br.udesc.doo2.cantina.controller;

import br.udesc.doo2.cantina.exception.ComentarioException;
import br.udesc.doo2.cantina.model.Cliente;
import br.udesc.doo2.cantina.model.Comentario;
import br.udesc.doo2.cantina.repository.ComentarioRepository;
import br.udesc.doo2.cantina.view.administrador.ConsultarComentariosView;
import br.udesc.doo2.cantina.view.cliente.ComentarioView;
import java.time.LocalDate;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ComentarioController {
    
    private ComentarioView view;
    private ConsultarComentariosView consultaView;
    private ComentarioRepository comentarioRepository;
    private Comentario model;
    private Cliente cliente;
    
    public ComentarioController(ComentarioView view, ComentarioRepository repository, Cliente cliente) {
        this.view = view;
        this.comentarioRepository = repository;
        this.cliente = cliente;
        
        this.setComportamentos();
    }
    
    public ComentarioController(ConsultarComentariosView view, ComentarioRepository repository) {
        this.consultaView = view;
        this.comentarioRepository = repository;
        
        List<Comentario> comentarios = comentarioRepository.buscarTodos();
                
        view.imprimeComentarios(comentarios);
    }
    
    private void setComportamentos() {
        view.setAcaoBotaoEnviarComentario(e -> enviarComentario());
    }
    
    private void enviarComentario() {
        try {
            String comentario = view.getTxtComentario();
            int nota = view.getNota();
            
            model = new Comentario(LocalDate.now(), comentario, nota, cliente);
            
            comentarioRepository.salvar(model);
            
            view.apresentarMensagem("Comentário enviado com sucesso!");
            
            view.limparCampos();
        } catch(ComentarioException ex) {
            view.apresentarMensagem(ex.getMessage());
        } catch (NumberFormatException ex) {
            view.apresentarMensagem("Nota inválida");
        }
    }
    
}
