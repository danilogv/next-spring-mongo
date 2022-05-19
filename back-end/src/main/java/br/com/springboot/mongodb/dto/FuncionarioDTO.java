package br.com.springboot.mongodb.dto;

import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class FuncionarioDTO implements Serializable {

    private String nome;

    private String cpf;

    private BigDecimal salario;

    private Integer idade;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataDesligamento;

    @Transient
    private String empresaId;

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public BigDecimal getSalario() {
        return this.salario;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public Integer getIdade() {
        return this.idade;
    }

    public void setDataDesligamento(LocalDate dataDesligamento) {
        this.dataDesligamento = dataDesligamento;
    }

    public LocalDate getDataDesligamento() {
        return this.dataDesligamento;
    }

    public void setEmpresaId(String empresaId) {
        this.empresaId = empresaId;
    }

    public String getEmpresaId() {
        return this.empresaId;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto)
            return true;
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        FuncionarioDTO funcionario = (FuncionarioDTO) objeto;
        if (this.cpf.equals(funcionario.getCpf()))
            return true;
        return false;
    }

}
