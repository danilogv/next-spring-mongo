package br.com.springboot.mongodb.dto;

public class UsuarioRequisicaoDTO {

    private String email;
    private String senha;

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
