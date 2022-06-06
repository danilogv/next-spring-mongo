package br.com.springboot.mongodb.dto;

import java.io.Serializable;

public class PaginacaoDTO implements Serializable {

    private Integer numeroPaginas;
    private Integer paginaAtual;

    public Integer getNumeroPaginas() {
        return this.numeroPaginas;
    }

    public void setNumeroPaginas(Integer numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public Integer getPaginaAtual() {
        return this.paginaAtual;
    }

    public void setPaginaAtual(Integer paginaAtual) {
        this.paginaAtual = paginaAtual;
    }

}
