package br.com.springboot.mongodb.dto;

import java.io.Serializable;

public class PaginacaoDTO implements Serializable {

    private Integer numeroPaginas;
    private Integer paginaAnterior;
    private Integer paginaAtual;
    private Integer paginaPosterior;
    private Integer qtdMaximaPaginas;

    public Integer getNumeroPaginas() {
        return this.numeroPaginas;
    }

    public void setNumeroPaginas(Integer numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public Integer getPaginaAnterior() {
        return this.paginaAnterior;
    }

    public void setPaginaAnterior(Integer paginaAnterior) {
        this.paginaAnterior = paginaAnterior;
    }

    public Integer getPaginaAtual() {
        return this.paginaAtual;
    }

    public void setPaginaAtual(Integer paginaAtual) {
        this.paginaAtual = paginaAtual;
    }

    public Integer getPaginaPosterior() {
        return this.paginaPosterior;
    }

    public void setPaginaPosterior(Integer paginaPosterior) {
        this.paginaPosterior = paginaPosterior;
    }

    public Integer getQtdMaximaPaginas() {
        return this.qtdMaximaPaginas;
    }

    public void setQtdMaximaPaginas(Integer qtdMaximaPaginas) {
        this.qtdMaximaPaginas = qtdMaximaPaginas;
    }
}
