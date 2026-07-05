package br.udesc.doo2.cantina.model;

import br.udesc.doo2.cantina.model.Pedido;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Faz a ponte entre a lista de Pedido e a JTable da tela ConsultarPedidoView
 * Cada Pedido corresponde a uma linha e cada atributo visivel a uma coluna.
 */
public class PedidoTableModel extends AbstractTableModel {

    private final List<Pedido> pedidos;
    
    //Nome das colunas
    private final String[] colunas = {
        "ID", "Cliente", "Tipo de Consumo",
        "Opção de Carne", "Status", "Data"
    };
    //define formato da data
    private final DateTimeFormatter formatoData =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    //recebe a lista
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

    /** Retorna o valor que deve ser exibido em cada celula da tabela. */
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

    /** Permite recuperar o objeto completo a partir da linha selecionada. */
    public Pedido getPedido(int linha) {
        return pedidos.get(linha);
    }
}
