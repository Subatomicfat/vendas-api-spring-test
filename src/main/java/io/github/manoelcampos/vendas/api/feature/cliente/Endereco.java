package io.github.manoelcampos.vendas.api.feature.cliente;

import lombok.Data;

/**
 * @author Manoel Campos
 */
@Data
public class Endereco {
    private String logradouro;
    private String numero;
    private String cep;
    private String cidade;
    private String uf;
}
