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

/**
 * Entidade que representa um pedido realizado por um cliente.
 * Reune a refeicao, a carne escolhida, o tipo de consumo, o status
 * atual e o momento em que o pedido foi criado.
 */
@Entity
@Table(name = "pedido")
public class Pedido implements Serializable, Comparable<Pedido> {

    @Id //É uma chave primaria, gerada pelo banco 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "data_hora_criacao", nullable = false) //slava da/hora do pedido, impedindo nuumeração vazia
    private LocalDateTime dataHoraCriacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_consumo", nullable = false)
    private TipoConsumo tipoConsumo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPedido status;

    //Relacionam o pedido com as outras informações necessárias das outras classes
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false) // é o relacionamento, um cliente pode possuir vários pedidos
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "refeicao_id", nullable = false) // é o relacionamento, varios pedidos podem ter a mesma refeição
    private Refeicao refeicao;

    @ManyToOne
    @JoinColumn(name = "opcao_carne_id", nullable = false) // relacionamento, varios pedidos podem ter a mesma opção de carne
    private OpcaoCarne opcaoCarne;

    /**
     * Construtor vazio exigido pelo JPA/Hibernate para criar a entidade.
     */
    protected Pedido() {
    }

    /**
     * cria um pedido novo. O identificador sera gerado pelo banco.
     */
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

    /**
     * Define a ordenacao natural dos pedidos pela data e hora de criacao.
     * E utilizado na consulta administrativa com Collections.sort().
     */
    @Override
    public int compareTo(Pedido outro) {
        return this.dataHoraCriacao.compareTo(outro.dataHoraCriacao);
    }
}
