package br.udesc.doo2.cantina.view.administrador;

import br.udesc.doo2.cantina.model.Pedido;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class PedidoTableModel extends AbstractTableModel {

    private final List<Pedido> pedidos;
    private final String[] colunas = {
        "ID", "Cliente", "Tipo de Consumo",
        "Opção de Carne", "Status", "Data"
    };
    private final DateTimeFormatter formatoData =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public PedidoTableModel(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public int getRowCount() {
        return pedidos.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int coluna) {
        return colunas[coluna];
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        Pedido pedido = pedidos.get(linha);

        switch (coluna) {
            case 0:
                return pedido.getId();
            case 1:
                return pedido.getCliente().getNome();
            case 2:
                return pedido.getTipoConsumo();
            case 3:
                return pedido.getOpcaoCarne().getNome();
            case 4:
                return pedido.getStatus();
            case 5:
                return pedido.getDataHoraCriacao().format(formatoData);
            default:
                return null;
        }
    }

    public Pedido getPedido(int linha) {
        return pedidos.get(linha);
    }
}
