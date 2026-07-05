package br.udesc.doo2.cantina.repository;

import br.udesc.doo2.cantina.enums.StatusPedido;
import br.udesc.doo2.cantina.enums.TipoConsumo;
import br.udesc.doo2.cantina.model.Pedido;
import java.util.List;


public interface PedidoRepository {
    void salvar(Pedido pedido);//recebe um obj pedido e salva
    void atualizarStatus(int idPedido, StatusPedido novoStatus); //atualiza satatus realizado/concluido
    boolean existePorClienteERefeicao(int idCliente, int idRefeicao); //vai verificar se cliente já fez um pedido
    List<Pedido> buscarTodos();//busca todos os pedidos 
    List<Pedido> buscarPorCliente(String nomeCliente); //filtro de buscar pelo nome do  cliente na ConsultarPedidosView
    List<Pedido> buscarPorTipoConsumo(TipoConsumo tipoConsumo);//filtro de buscar pelo consumo do  cliente na ConsultarPedidosView
}
