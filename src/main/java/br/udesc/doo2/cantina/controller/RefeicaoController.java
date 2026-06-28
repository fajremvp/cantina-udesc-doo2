package br.udesc.doo2.cantina.controller;

import br.udesc.doo2.cantina.exception.RefeicaoException;
import br.udesc.doo2.cantina.model.OpcaoCarne;
import br.udesc.doo2.cantina.model.Refeicao;
import br.udesc.doo2.cantina.repository.OpcaoCarneRepository;
import br.udesc.doo2.cantina.repository.RefeicaoRepository;
import br.udesc.doo2.cantina.view.administrador.CadastrarRefeicaoView;
import java.time.LocalDate;
import java.util.Set;

public class RefeicaoController {
    private CadastrarRefeicaoView view;
    private RefeicaoRepository repositoryRefeicao;
    private OpcaoCarneRepository repositoryCarne;
    private Refeicao model;

    public RefeicaoController(CadastrarRefeicaoView view, RefeicaoRepository repositoryRefeicao, OpcaoCarneRepository repositoryCarne) {
        this.view = view;
        this.repositoryRefeicao = repositoryRefeicao;
        this.repositoryCarne = repositoryCarne;
        
        initTela();
    }
    
    public void initTela() {
        view.adicionarAcaoBtnCadastrar(e -> cadastrarRefeicao());
        
        Set<OpcaoCarne> carnes = repositoryCarne.buscarTodas();
        
        view.initCbCarne1(carnes);
        view.initCbCarne2(carnes);
        
        view.apresentarTela();
    }
    
    public void cadastrarRefeicao() {
        try {
            LocalDate data = view.getData();
            String descricao = view.getDescricao();
            float preco = view.getPreco();
            int capacidade = view.getCapacidade();
            
            model = new Refeicao(data, descricao, preco, capacidade);
            OpcaoCarne carne1 = repositoryCarne.buscarPorNome(view.getCarne1Selecionada());
            OpcaoCarne carne2 = repositoryCarne.buscarPorNome(view.getCarne2Selecionada());
            model.salvarCarne(carne1);
            model.salvarCarne(carne2);
            repositoryRefeicao.salvar(model);
            
            view.apresentarMensagem("Refeição cadastrada com sucesso");
            view.limparTela();
        } catch (RefeicaoException ex) {
            view.apresentarMensagem(ex.getMessage());
        }
    }
    
}
