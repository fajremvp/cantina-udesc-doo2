package br.udesc.doo2.cantina.controller;

import br.udesc.doo2.cantina.enums.StatusPedido;
import br.udesc.doo2.cantina.enums.TipoConsumo;
import br.udesc.doo2.cantina.exception.PedidoException;
import br.udesc.doo2.cantina.model.Cliente;
import br.udesc.doo2.cantina.model.OpcaoCarne;
import br.udesc.doo2.cantina.model.Pedido;
import br.udesc.doo2.cantina.model.Refeicao;
import br.udesc.doo2.cantina.repository.PedidoRepository;
import br.udesc.doo2.cantina.view.administrador.ConsultarPedidosView;
import br.udesc.doo2.cantina.view.cliente.PedidoView;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Coordena os dois fluxos do modulo de Pedido:
 * realizacao pelo cliente e consulta/retirada pelo administrador.
 * As Views apenas fornecem dados e exibem resultados; as decisoes ficam aqui.
 */
public class PedidoController {

    private PedidoView pedidoView;
    private ConsultarPedidosView consultarPedidosView;
    private PedidoRepository pedidoRepository;
    private Cliente cliente;
    private Refeicao refeicao;

    /**
     * controla o fluxo do cliente com sua refeicao e repositorio.
     */
    public PedidoController(PedidoView view, PedidoRepository repository,
            Cliente cliente, Refeicao refeicao) {
        this.pedidoView = view;
        this.pedidoRepository = repository;
        this.cliente = cliente;
        this.refeicao = refeicao;
        iniciarTelaPedido();
    }

    /** controla o fluxo de consulta do adiministrador. */
    public PedidoController(ConsultarPedidosView view,
            PedidoRepository repository) {
        this.consultarPedidosView = view;
        this.pedidoRepository = repository;
        iniciarTelaConsulta();
    }

    /** Carrega as carnes da refeicao e registra a acao de finalizar. */
    private void iniciarTelaPedido() {
        pedidoView.iniciarOpcoesCarne(refeicao.getCarnes());
        pedidoView.adicionarAcaoBtnFinalizar(e -> finalizarPedido());
        pedidoView.apresentarTela();
    }

    /**
     * Valida as escolhas, cria o Pedido, salva no banco e exibe a confirmacao.
     */
    private void finalizarPedido() {
        try {
            String nomeCarne = pedidoView.getOpcaoCarneSelecionada();
            if (nomeCarne == null) {
                throw new PedidoException(
                        "Pedido não pode ser finalizado, preencha a opção de carne");
            }

            TipoConsumo tipoConsumo = pedidoView.getTipoConsumoSelecionado();
            if (tipoConsumo == null) {
                throw new PedidoException(
                        "Pedido não pode ser finalizado, preencha a opção de consumo");
            }

            if (pedidoRepository.existePorClienteERefeicao( cliente.getId(), refeicao.getId())) { // verifica se o cliente já realizou pedido para o dia
                throw new PedidoException(
                        "Cliente já realizou um pedido para esta refeição");
            }

            OpcaoCarne opcaoCarne = buscarCarneSelecionada(nomeCarne); //Procura, dentro das carnes da refeição atual, o objeto correspondente ao nome selecionado na tela.
            
            // cria o pedido em memória 
            Pedido pedido = new Pedido(
                    LocalDateTime.now(),
                    tipoConsumo,
                    StatusPedido.REALIZADO,
                    cliente,
                    refeicao,
                    opcaoCarne
            );
            // salva no banco
            pedidoRepository.salvar(pedido);
            
            pedidoView.apresentarMensagem(montarConfirmacao(pedido));
            pedidoView.limparSelecoes();
        } catch (PedidoException e) {
            pedidoView.apresentarMensagem(e.getMessage());
        }
    }

    /** Confirma que a carne selecionada pertence a refeicao atual. */
    private OpcaoCarne buscarCarneSelecionada(String nomeCarne)
            throws PedidoException {
        for (OpcaoCarne carne : refeicao.getCarnes()) {
            if (carne.getNome().equals(nomeCarne)) {
                return carne;
            }
        }
        throw new PedidoException("opção de carne inválida");
    }

    /** Monta a mensagem apresentada depois do salvamento com sucesso. */
    private String montarConfirmacao(Pedido pedido) {
        return "Pedido realizado, opção de carne "
                + pedido.getOpcaoCarne().getNome()
                + ", opção de consumo "
                + pedido.getTipoConsumo();
    }

    /**
     * Registra as acoes do adm e apresenta a listagem inicial.
     */
    private void iniciarTelaConsulta() {
        consultarPedidosView.adicionarAcaoBtnBuscar(e -> buscarPedidos());
        consultarPedidosView.adicionarAcaoBtnConfirmarRetirada(e -> confirmarRetirada());
        List<Pedido> pedidos = pedidoRepository.buscarTodos();
        Collections.sort(pedidos);
        consultarPedidosView.apresentarPedidos(pedidos); //Coloca os pedidos na tabela por meio do PedidoTableModel
        consultarPedidosView.apresentarTela();
    }


    private void confirmarRetirada() {
        try {
            Pedido pedido = consultarPedidosView.getPedidoSelecionado();
            if (pedido == null) {
                throw new PedidoException(
                        "selecione um pedido para confirmar a retirada");// Lança uma exceção caso seja clicado o botão confirmar retirada sem selecionar pedido nenhum
            }
            pedidoRepository.atualizarStatus(
                    pedido.getId(), StatusPedido.CONCLUIDO);

            List<Pedido> pedidos = pedidoRepository.buscarTodos();
            Collections.sort(pedidos);
            consultarPedidosView.apresentarPedidos(pedidos);
            consultarPedidosView.apresentarMensagem(
                    "Retirada confirmada, pedido concluido");
        } catch (PedidoException e) {
            consultarPedidosView.apresentarMensagem(e.getMessage());
        }
    }
    /**
     * Aplica os filtros opcionais e ordena o resultado pelo Comparable.
     */
    private void buscarPedidos() {
        try {
            String nomeCliente = consultarPedidosView.getNomeCliente();
            TipoConsumo tipoConsumo =
                    consultarPedidosView.getTipoConsumoSelecionado();

            if (nomeCliente.isBlank() && tipoConsumo == null) {
                throw new PedidoException(
                        "preencha algum dos filtros para realizar a busca");//Lança um exceção caso nenhum campo dos filtros tenha sido preenchido e tentou buscar
            }

            List<Pedido> pedidos;
            if (!nomeCliente.isBlank()) {
                pedidos = pedidoRepository.buscarPorCliente(nomeCliente);
            } else {
                pedidos = pedidoRepository.buscarPorTipoConsumo(tipoConsumo);
            }

            Collections.sort(pedidos);
            consultarPedidosView.apresentarPedidos(pedidos);
        } catch (PedidoException e) {
            consultarPedidosView.apresentarMensagem(e.getMessage());
        }
    }
}
