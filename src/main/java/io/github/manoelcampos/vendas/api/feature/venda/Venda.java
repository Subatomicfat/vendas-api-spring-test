package io.github.manoelcampos.vendas.api.feature.venda;

import io.github.manoelcampos.vendas.api.feature.cliente.Cliente;
import io.github.manoelcampos.vendas.api.feature.cliente.Endereco;
import io.github.manoelcampos.vendas.api.shared.model.AbstractBaseModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.requireNonNullElse;

/**
 * @author Manoel Campos
 */
@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Venda extends AbstractBaseModel {
    public enum Status{ REGISTRADA, AGUARDANDO_PAGAMENTO, PAGA, PREPARANDO_ENVIO, ENVIADA, ENTREGUE }

    @NotNull @ManyToOne
    private Cliente cliente;

    @Column(nullable = false)
    private LocalDateTime dataHora = LocalDateTime.now();

    @Enumerated(EnumType.STRING) @NotNull
    private Status status;

    @Embedded
    private Endereco enderecoEntregua;

    @OneToMany(mappedBy = "venda")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<ItemVenda> itens = new ArrayList<>();

    public Venda(final long id) {
        this.setId(id);
    }

    public Venda(final long id, final List<ItemVenda> itens) {
        this(id);
        setItens(itens);
    }

    public Venda(final Cliente cliente) {
        setCliente(cliente);
    }

    public void setItens(final List<ItemVenda> itens) {
        this.itens = requireNonNullElse(itens, new LinkedList<>());
        this.itens.forEach(item -> item.setVenda(this));
    }

    @Override
    public String toString() {
        return "Venda{id: %d cliente: %d}".formatted(getId(), cliente == null ? cliente : cliente.getId());
    }
}
