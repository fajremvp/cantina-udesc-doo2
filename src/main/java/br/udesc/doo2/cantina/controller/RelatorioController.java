package br.udesc.doo2.cantina.controller;

import br.udesc.doo2.cantina.enums.TipoConsumo;
import br.udesc.doo2.cantina.model.Cliente;
import br.udesc.doo2.cantina.model.OpcaoCarne;
import br.udesc.doo2.cantina.model.Pedido;
import br.udesc.doo2.cantina.repository.PedidoRepository;
import br.udesc.doo2.cantina.view.administrador.RelatoriosView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.table.DefaultTableModel;

public class RelatorioController {
    
    private RelatoriosView view;
    private PedidoRepository pedidoRepository;
    
    private DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public RelatorioController(RelatoriosView view, PedidoRepository pedidoRepository) {
        this.view = view;
        this.pedidoRepository = pedidoRepository;
        
        this.setAcoes();
    }
    
    private void setAcoes() {
        view.getBtnGerar().addActionListener(e -> this.gerarRelatorio());
    }
    
    public void gerarRelatorio() {
        try {
            LocalDateTime dataInicial = LocalDateTime.parse(this.getDataInicial().trim(), dtFormat);
            LocalDateTime dataFinal = LocalDateTime.parse(this.getDataFinal().trim(), dtFormat);
            
            int tipo = view.getJcbTipo().getSelectedIndex();
            
            switch(tipo) {
                case 0:
                    this.gerarRelatorioClientes(dataInicial, dataFinal);
                    break;
                case 1:
//                    this.gerarRelatorioTipoConsumo(dataInicial, dataFinal);
                    break;
                case 2:
//                    this.gerarRelatorioPorCarne(dataInicial, dataFinal);
                    break;
            }
            
            view.apresentarMensagem("Relatório gerado com sucesso");
        } catch(RuntimeException e) {
            view.apresentarMensagem("Não foi possível completar a ação");
            System.out.println(e.getMessage());
        }
    }
    
    private void gerarRelatorioClientes(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        List<Pedido> pedidos = pedidoRepository.buscarPorData(dataInicial, dataFinal);

        Set<Cliente> clientes = new HashSet<>();

        for(Pedido pedido : pedidos) {
            clientes.add(pedido.getCliente());
        }

        Map<String, Integer> dados = new HashMap<>();
        dados.put("Clientes Únicos", clientes.size());

        this.exibirRelatorio("Descrição", dados);
    }
/*    
    private void gerarRelatorioTipoConsumo(LocalDate dataInicial, LocalDate dataFinal) {
        List<Pedido> pedidos = pedidoRepository.buscarPorData(dataInicial, dataFinal);

        Map<TipoConsumo, Integer> mapa = new HashMap<>();

        for (Pedido pedido : pedidos) {
            TipoConsumo tipo = pedido.getTipoConsumo();
            mapa.put(tipo, mapa.getOrDefault(tipo, 0) + 1);
        }
        
        this.exibirRelatorio("Tipo de Consumo", mapa);
    }
    
    private void gerarRelatorioPorCarne(LocalDate dataInicial, LocalDate dataFinal) {
        List<Pedido> pedidos = pedidoRepository.buscarPorData(dataInicial, dataFinal);

        Map<OpcaoCarne, Integer> mapa = new HashMap<>();

        for (Pedido pedido : pedidos) {
            OpcaoCarne carne = pedido.getOpcaoCarne();
            mapa.put(carne, mapa.getOrDefault(carne, 0) + 1);
        }
        
        this.exibirRelatorio("Opção de Carne", mapa);
    }
  */  
    private void exibirRelatorio(String tituloColuna, Map<?, Integer> dados) {
        DefaultTableModel model = (DefaultTableModel) view.getTblDados().getModel();

        model.setRowCount(0);

        model.setColumnCount(0);

        model.addColumn(tituloColuna);
        model.addColumn("Quantidade");

        for (Map.Entry<?, Integer> entry : dados.entrySet()) {

            model.addRow(new Object[]{
                entry.getKey(),
                entry.getValue()
            });

        }
    }
    
    private String getDataInicial() {
        return view.getTxtDataInicial().getText();
    }
    
    private String getDataFinal() {
        return view.getTxtDataFinal().getText();
    }
    
    private List<Pedido> buscarPedidos() {
        return pedidoRepository.buscarTodos();
    }
    
}
