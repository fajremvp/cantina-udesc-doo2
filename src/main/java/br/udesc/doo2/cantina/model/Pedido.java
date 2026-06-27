package br.udesc.doo2.cantina.model;

import br.udesc.doo2.cantina.enums.StatusPedido;
import br.udesc.doo2.cantina.enums.TipoConsumo;
import java.time.LocalDateTime;

public class Pedido {
    private int id;
    private LocalDateTime dataHoraCriacao;
    private TipoConsumo tipoConsumo;
    private StatusPedido status;
    private Cliente cliente;
    private Refeicao refeicao;
    private OpcaoCarne opcaoCarne;

    public Pedido(int id, LocalDateTime dataHoraCriacao, TipoConsumo tipoConsumo, StatusPedido status, Cliente cliente, Refeicao refeicao, OpcaoCarne opcaoCarne) {
        this.id = id;
        this.dataHoraCriacao = dataHoraCriacao;
        this.tipoConsumo = tipoConsumo;
        this.status = status;
        this.cliente = cliente;
        this.refeicao = refeicao;
        this.opcaoCarne = opcaoCarne;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public LocalDateTime getDataHoraCriacao() { return dataHoraCriacao; }
    public void setDataHoraCriacao(LocalDateTime dataHoraCriacao) { this.dataHoraCriacao = dataHoraCriacao; }
    public TipoConsumo getTipoConsumo() { return tipoConsumo; }
    public void setTipoConsumo(TipoConsumo tipoConsumo) { this.tipoConsumo = tipoConsumo; }
    public StatusPedido getStatus() { return status; }
    public void setStatus(StatusPedido status) { this.status = status; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Refeicao getRefeicao() { return refeicao; }
    public void setRefeicao(Refeicao refeicao) { this.refeicao = refeicao; }
    public OpcaoCarne getOpcaoCarne() { return opcaoCarne; }
    public void setOpcaoCarne(OpcaoCarne opcaoCarne) { this.opcaoCarne = opcaoCarne; }
}