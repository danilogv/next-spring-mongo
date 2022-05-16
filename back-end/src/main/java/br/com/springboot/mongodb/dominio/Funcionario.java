package br.com.springboot.mongodb.dominio;

import org.springframework.format.annotation.DateTimeFormat;
import java.math.BigDecimal;
import java.time.LocalDate;

public final class Funcionario {

    private String nome;

    private String cpf;

    private BigDecimal salario;

    private Integer idade;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataDesligamento;

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

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto)
            return true;
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        return false;
    }

}

