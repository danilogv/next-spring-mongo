package br.com.springboot.mongodb.dto;

import br.com.springboot.mongodb.dominio.Empresa;
import java.util.List;

public class PaginacaoEmpresaDTO extends PaginacaoDTO {

    private List<Empresa> empresas;

    public List<Empresa> getEmpresas() {
        return this.empresas;
    }

    public void setEmpresas(List<Empresa> empresas) {
        this.empresas = empresas;
    }

}
