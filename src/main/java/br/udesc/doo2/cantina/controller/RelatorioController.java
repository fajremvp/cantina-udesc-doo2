package br.udesc.doo2.cantina.controller;

import br.udesc.doo2.cantina.enums.TipoConsumo;
import br.udesc.doo2.cantina.model.Cliente;
import br.udesc.doo2.cantina.model.OpcaoCarne;
import br.udesc.doo2.cantina.model.Pedido;
import br.udesc.doo2.cantina.repository.PedidoRepository;
import br.udesc.doo2.cantina.view.administrador.RelatoriosView;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RelatorioController {
    
    private RelatoriosView view;
    private PedidoRepository pedidoRepository;
    
    public RelatorioController(RelatoriosView view, PedidoRepository pedidoRepository) {
        this.view = view;
        this.pedidoRepository = pedidoRepository;
        
        this.setAcoes();
    }
    
    private void setAcoes() {
        view.setAcaoBotaoGerarRelatorio(e -> this.gerarRelatorio());
    }
    
    public void gerarRelatorio() {
        try {
            LocalDate dataInicial = view.getDataInicial();
            LocalDate dataFinal = view.getDataFinal();
            
            List<Pedido> pedidos = pedidoRepository.buscarPorData(dataInicial.atStartOfDay(), dataFinal.atTime(LocalTime.MAX));
            
            int tipo = view.getTipoRelatorio();
            
            switch(tipo) {
                case 0 -> this.gerarRelatorioClientes(pedidos);
                case 1 -> this.gerarRelatorioTipoConsumo(pedidos);
                case 2 -> this.gerarRelatorioPorCarne(pedidos);
            }
            
            view.apresentarMensagem("Relatório gerado com sucesso");
        } catch(DateTimeParseException  e) {
            view.apresentarMensagem("Não foi possível completar a ação - formato de data inválido");
        } catch(Exception e) {
            view.apresentarMensagem("Não foi possível completar a ação - tente novamente mais tarde");
        }
    }
    
    private void gerarRelatorioClientes(List<Pedido> pedidos) {
        Set<Cliente> clientes = new HashSet<>();

        for(Pedido pedido : pedidos) {
            clientes.add(pedido.getCliente());
        }

        Map<String, Integer> dados = new HashMap<>();
        dados.put("Clientes Únicos", clientes.size());

        view.exibirRelatorio("Descrição", dados);
    }
    
    private void gerarRelatorioTipoConsumo(List<Pedido> pedidos) {
        Map<TipoConsumo, Integer> mapa = new HashMap<>();

        for (Pedido pedido : pedidos) {
            TipoConsumo tipo = pedido.getTipoConsumo();
            mapa.put(tipo, mapa.getOrDefault(tipo, 0) + 1);
        }
        
        view.exibirRelatorio("Tipo de Consumo", mapa);
    }
    
    private void gerarRelatorioPorCarne(List<Pedido> pedidos) {
        Map<OpcaoCarne, Integer> mapa = new HashMap<>();

        for (Pedido pedido : pedidos) {
            OpcaoCarne carne = pedido.getOpcaoCarne();
            mapa.put(carne, mapa.getOrDefault(carne, 0) + 1);
        }
        
        view.exibirRelatorio("Opção de Carne", mapa);
    }
    
}
