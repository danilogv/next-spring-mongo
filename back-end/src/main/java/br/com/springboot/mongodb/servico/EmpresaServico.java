package br.com.springboot.mongodb.servico;

import br.com.springboot.mongodb.repositorio.EmpresaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpresaServico {

    @Autowired
    private EmpresaRepositorio repositorio;

}
