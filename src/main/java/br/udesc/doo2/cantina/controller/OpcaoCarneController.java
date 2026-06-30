package br.udesc.doo2.cantina.controller;

import br.udesc.doo2.cantina.exception.OpcaoCarneException;
import br.udesc.doo2.cantina.model.OpcaoCarne;
import br.udesc.doo2.cantina.repository.OpcaoCarneRepository;
import br.udesc.doo2.cantina.view.administrador.CadastrarCarnesView;

public class OpcaoCarneController {
    private CadastrarCarnesView view;
    private OpcaoCarneRepository repositoryCarne;
    private OpcaoCarne model;

    public OpcaoCarneController(CadastrarCarnesView view, OpcaoCarneRepository repositoryCarne) {
        this.view = view;
        this.repositoryCarne = repositoryCarne;
        
        initTela();
    }
    
    public void initTela() {
        view.adicionarAcaoBtnCadastrar(e -> CadastrarCarne());
        
        view.initTblCarnes(repositoryCarne.buscarTodas());
        
        view.apresentarTela();
    }
    
    public void CadastrarCarne() {
        try {
            String nome = view.getNome();
            int quantidade = view.getQuantidade();
            
            model = new OpcaoCarne(nome , quantidade);
            repositoryCarne.salvar(model);
            view.initTblCarnes(repositoryCarne.buscarTodas());
            
            view.apresentarMensagem("Carne cadastrada com sucesso");
            view.limparTela();
            
        } catch (OpcaoCarneException ex) {
            view.apresentarMensagem(ex.getMessage());
        }
    }
    
}
