package br.com.springboot.mongodb.excecao;

import java.io.Serializable;

public class Erro implements Serializable {

    private Integer status;
    private String mensagem;

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMensagem() {
        return this.mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

}
