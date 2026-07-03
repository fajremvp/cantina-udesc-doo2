package br.udesc.doo2.cantina.model;

import br.udesc.doo2.cantina.enums.StatusPedido;
import br.udesc.doo2.cantina.enums.TipoConsumo;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pedido")
public class Pedido implements Serializable, Comparable<Pedido> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "data_hora_criacao", nullable = false)
    private LocalDateTime dataHoraCriacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_consumo", nullable = false)
    private TipoConsumo tipoConsumo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPedido status;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "refeicao_id", nullable = false)
    private Refeicao refeicao;

    @ManyToOne
    @JoinColumn(name = "opcao_carne_id", nullable = false)
    private OpcaoCarne opcaoCarne;

    protected Pedido() {
    }

    public Pedido(LocalDateTime dataHoraCriacao, TipoConsumo tipoConsumo,
            StatusPedido status, Cliente cliente, Refeicao refeicao,
            OpcaoCarne opcaoCarne) {
        this.dataHoraCriacao = dataHoraCriacao;
        this.tipoConsumo = tipoConsumo;
        this.status = status;
        this.cliente = cliente;
        this.refeicao = refeicao;
        this.opcaoCarne = opcaoCarne;
    }

    public Pedido(int id, LocalDateTime dataHoraCriacao, TipoConsumo tipoConsumo,
            StatusPedido status, Cliente cliente, Refeicao refeicao,
            OpcaoCarne opcaoCarne) {
        this(dataHoraCriacao, tipoConsumo, status, cliente, refeicao, opcaoCarne);
        this.id = id;
    }

    @Override
    public String toString() {
        return "Pedido\n"
                + "Id: " + id
                + " Data/Hora: " + dataHoraCriacao
                + " Tipo de Consumo: " + tipoConsumo
                + " Status: " + status
                + " Cliente: " + cliente
                + " Refeição: " + refeicao
                + " Opção de Carne: " + opcaoCarne;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public LocalDateTime getDataHoraCriacao() {
        return dataHoraCriacao;
    }
    public void setDataHoraCriacao(LocalDateTime dataHoraCriacao) {
        this.dataHoraCriacao = dataHoraCriacao;
    }
    public TipoConsumo getTipoConsumo() {
        return tipoConsumo;
    }
    public void setTipoConsumo(TipoConsumo tipoConsumo) {
        this.tipoConsumo = tipoConsumo;
    }
    public StatusPedido getStatus() {
        return status;
    }
    public void setStatus(StatusPedido status) {
        this.status = status;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public Refeicao getRefeicao() {
        return refeicao;
    }
    public void setRefeicao(Refeicao refeicao) {
        this.refeicao = refeicao;
    }
    public OpcaoCarne getOpcaoCarne() {
        return opcaoCarne;
    }
    public void setOpcaoCarne(OpcaoCarne opcaoCarne) {
        this.opcaoCarne = opcaoCarne;
    }

    @Override
    public int compareTo(Pedido outro) {
        return this.dataHoraCriacao.compareTo(outro.dataHoraCriacao);
    }
}
