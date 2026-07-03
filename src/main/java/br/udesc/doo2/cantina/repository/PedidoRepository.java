package br.udesc.doo2.cantina.repository;

import br.udesc.doo2.cantina.enums.StatusPedido;
import br.udesc.doo2.cantina.enums.TipoConsumo;
import br.udesc.doo2.cantina.model.Pedido;
import java.util.List;

public interface PedidoRepository {
    void salvar(Pedido pedido);
    void atualizarStatus(int idPedido, StatusPedido novoStatus);
    Pedido buscarPorId(int id);
    List<Pedido> buscarTodos();
    List<Pedido> buscarPorCliente(String nomeCliente);
    List<Pedido> buscarPorTipoConsumo(TipoConsumo tipoConsumo);
}
