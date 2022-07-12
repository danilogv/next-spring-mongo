package br.com.springboot.mongodb.padrao_projeto;

import br.com.springboot.mongodb.servico.EmpresaServico;
import br.com.springboot.mongodb.servico.FuncionarioServico;
import br.com.springboot.mongodb.servico.UsuarioServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FacadeServico {

    @Autowired
    public EmpresaServico empresa;

    @Autowired
    public FuncionarioServico funcionario;

    @Autowired
    public UsuarioServico usuario;

}
