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

public class PedidoController {

    private PedidoView pedidoView;
    private ConsultarPedidosView consultarPedidosView;
    private PedidoRepository pedidoRepository;
    private Cliente cliente;
    private Refeicao refeicao;

    public PedidoController(PedidoView view, PedidoRepository repository,
            Cliente cliente, Refeicao refeicao) {
        this.pedidoView = view;
        this.pedidoRepository = repository;
        this.cliente = cliente;
        this.refeicao = refeicao;
        iniciarTelaPedido();
    }

    public PedidoController(ConsultarPedidosView view,
            PedidoRepository repository) {
        this.consultarPedidosView = view;
        this.pedidoRepository = repository;
        iniciarTelaConsulta();
    }

    private void iniciarTelaPedido() {
        pedidoView.iniciarOpcoesCarne(refeicao.getCarnes());
        pedidoView.adicionarAcaoBtnFinalizar(e -> finalizarPedido());
        pedidoView.apresentarTela();
    }

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

            OpcaoCarne opcaoCarne = buscarCarneSelecionada(nomeCarne);
            Pedido pedido = new Pedido(
                    LocalDateTime.now(),
                    tipoConsumo,
                    StatusPedido.REALIZADO,
                    cliente,
                    refeicao,
                    opcaoCarne
            );

            pedidoRepository.salvar(pedido);
            pedidoView.apresentarMensagem(montarConfirmacao(pedido));
            pedidoView.limparSelecoes();
        } catch (PedidoException e) {
            pedidoView.apresentarMensagem(e.getMessage());
        }
    }

    private OpcaoCarne buscarCarneSelecionada(String nomeCarne)
            throws PedidoException {
        for (OpcaoCarne carne : refeicao.getCarnes()) {
            if (carne.getNome().equals(nomeCarne)) {
                return carne;
            }
        }
        throw new PedidoException("opção de carne inválida");
    }

    private String montarConfirmacao(Pedido pedido) {
        return "Pedido realizado, opção de carne "
                + pedido.getOpcaoCarne().getNome()
                + ", opção de consumo "
                + pedido.getTipoConsumo();
    }

    private void iniciarTelaConsulta() {
        consultarPedidosView.adicionarAcaoBtnBuscar(e -> buscarPedidos());

        List<Pedido> pedidos = pedidoRepository.buscarTodos();
        Collections.sort(pedidos);
        consultarPedidosView.apresentarPedidos(pedidos);
        consultarPedidosView.apresentarTela();
    }

    private void buscarPedidos() {
        try {
            String nomeCliente = consultarPedidosView.getNomeCliente();
            TipoConsumo tipoConsumo =
                    consultarPedidosView.getTipoConsumoSelecionado();

            if (nomeCliente.isBlank() && tipoConsumo == null) {
                throw new PedidoException(
                        "preencha algum dos filtros para realizar a busca");
            }

            List<Pedido> pedidos;
            if (!nomeCliente.isBlank()) {
                pedidos = pedidoRepository.buscarPorCliente(nomeCliente);
                if (tipoConsumo != null) {
                    pedidos.removeIf(p ->
                            p.getTipoConsumo() != tipoConsumo);
                }
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
