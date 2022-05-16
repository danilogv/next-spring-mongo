package br.com.springboot.mongodb.controle;

import br.com.springboot.mongodb.servico.EmpresaServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/empresa")
public class EmpresaControle {

    @Autowired
    private EmpresaServico servico;

}
