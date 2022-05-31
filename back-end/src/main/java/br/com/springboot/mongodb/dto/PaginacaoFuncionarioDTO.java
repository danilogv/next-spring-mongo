package br.com.springboot.mongodb.dto;

import br.com.springboot.mongodb.dominio.Funcionario;
import java.util.List;

public class PaginacaoFuncionarioDTO extends PaginacaoDTO {

    private List<Funcionario> funcionarios;

    public List<Funcionario> getFuncionarios() {
        return this.funcionarios;
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }
}
