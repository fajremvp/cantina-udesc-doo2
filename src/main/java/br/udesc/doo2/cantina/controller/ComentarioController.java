package br.udesc.doo2.cantina.controller;

import br.udesc.doo2.cantina.dao.ComentarioDAO;
import br.udesc.doo2.cantina.exception.ComentarioException;
import br.udesc.doo2.cantina.model.Cliente;
import br.udesc.doo2.cantina.model.Comentario;
import br.udesc.doo2.cantina.repository.ComentarioRepository;
import br.udesc.doo2.cantina.view.administrador.ConsultarComentariosView;
import br.udesc.doo2.cantina.view.cliente.ComentarioView;
import java.time.LocalDate;
import java.util.List;

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
        
        this.imprimeComentarios();
    }
    
    private void setComportamentos() {
        view.getBtnEnviar().addActionListener(e -> enviarComentario());
    }
    
    private void enviarComentario() {
        try {
            String comentario = this.getComentario();
            int nota = this.getNota();
            
            model = new Comentario(LocalDate.now(), comentario, nota, cliente);
            
            comentarioRepository.salvar(model);
        } catch(ComentarioException ex) {
            view.apresentarMensagem("Entrada inválida");
        }
    }
    
    private void imprimeComentarios() {
        List<Comentario> comentarios = comentarioRepository.buscarTodos();
        
        
    }
    
    private String getComentario() {
        return view.getTxtComentario().getText();
    }
    
    private int getNota() throws ComentarioException {
        return Integer.parseInt(view.getJcbNota().getSelectedItem().toString());
    }
    
}
