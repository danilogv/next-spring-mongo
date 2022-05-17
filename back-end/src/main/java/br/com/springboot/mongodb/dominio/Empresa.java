package br.com.springboot.mongodb.dominio;

import br.com.springboot.mongodb.dto.FuncionarioDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "empresa")
public class Empresa implements Serializable {

    @Id
    private String id;

    private String nome;

    private String cnpj;

    private List<FuncionarioDTO> funcionarios = new ArrayList<>();

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCnpj() {
        return this.cnpj;
    }

    public void setFuncionarios(List<FuncionarioDTO> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public List<FuncionarioDTO> getFuncionarios() {
        return this.funcionarios;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto)
            return true;
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        Empresa empresa = (Empresa) objeto;
        if (this.id.equals(empresa.getId()) || this.cnpj.equals(empresa.getCnpj()))
            return true;
        return false;
    }

}
