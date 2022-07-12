package br.com.springboot.mongodb.padrao_projeto;

import br.com.springboot.mongodb.repositorio.EmpresaRepositorio;
import br.com.springboot.mongodb.repositorio.FuncionarioRepositorio;
import br.com.springboot.mongodb.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FacadeRepositorio {

    @Autowired
    public EmpresaRepositorio empresa;

    @Autowired
    public FuncionarioRepositorio funcionario;

    @Autowired
    public UsuarioRepositorio usuario;

}
